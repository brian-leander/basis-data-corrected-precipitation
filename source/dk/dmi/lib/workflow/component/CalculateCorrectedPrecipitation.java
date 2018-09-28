package dk.dmi.lib.workflow.component;

import dk.dmi.lib.correctedprecipitation.*;
import dk.dmi.lib.gauges.Station;
import dk.dmi.lib.location.GridPointValue;
import dk.dmi.lib.workflow.common.BaseComponent;
import dk.dmi.lib.workflow.common.WorkflowAnnotations;

import java.time.Month;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dk.dmi.lib.workflow.common.WorkflowAnnotations.Component;
import static dk.dmi.lib.workflow.common.WorkflowAnnotations.ExecuteMethod;

@Component(
        name = "Calculate Corrected Precipitation",
        category = "Basis Data",
        description = "Calculates corrected station precipitation values for a given date",
        version = 1)
public class CalculateCorrectedPrecipitation extends BaseComponent {

    @ExecuteMethod(
            argumentDisplayTypes = {WorkflowAnnotations.ARGUMENT_DISPLAY_TYPE_TEXT, WorkflowAnnotations.ARGUMENT_DISPLAY_TYPE_TEXT, WorkflowAnnotations.ARGUMENT_DISPLAY_TYPE_TEXT},
            argumentDescriptions = {"List of stations", "Map of GridPointValues", "The date to calculate corrected precipitation"},
            returnDescription = "Returns list of corrected station values for the given date")
    public List<String> CalculateCorrectedPrecipitation(List<Station> stations, Map<Integer, GridPointValue> gridPointValueMap, ZonedDateTime date) {
        LOGGER.debug("CalculateCorrectedPrecipitation start ---> ");

        List<Station> statsToUpdate = new ArrayList<Station>();
        List<String> output = new ArrayList<String>(350);
        output.add("dato;statid;easting;northing;gridnr;maalertype;laeIndex;T;Tvalid;V10;wind15;wind15Lae;wind15LaeRainValid;wind15LaeSnowValid;alfa;I;Ivalid;z0;kr;ks;Pm;Pc;label;status");

        long label;

        for (Station s : stations) {

            int statNo = s.getStatid();
            LOGGER.debug("Processing date: "+date + " --- Station:" + statNo);
            double Pm = s.getValue();//cdb.getPrecipVal(statNo, gcDate);
            label = 0;
            LOGGER.debug("Daily value: Pm = " + Pm);

//            Integer laeIndex = leeStats.get(statNo);
            Integer leeIndex = s.getLeeindex();
            if(leeIndex == null){
                LOGGER.debug("statNo = {0} missing leeindex" + statNo);
                continue;
            }

            LOGGER.debug("statNo = " + statNo);
//            Station stat = sdb.getStatPos(statNo, gcDate);
//            if (stat == null) {
//                LOGGER.debug(Level.SEVERE, "statNo = {0} missing possition", statNo);
//            } else {
                int eastings = s.getLocation().getEastings();
                int northings = s.getLocation().getNorthings();

//                GridClassifier gc = new GridClassifier(gps);
//                fieldId = gc.getGridPoint(s);
//                if (fieldId == 0) {
//                    LOGGER.debug("No gridpoint for station " + statNo);
//
//                }

//                LOGGER.debug("Station: "+statNo+" , tilh\u00f8rende gridfelt: "+fieldId);

//                GetGridvalues gg = new GetGridvalues(ndb);
//                gg.getGrid(s.getGridPoint(), gcDate); // Hent de tilhørende gridværdier for 101 (temp) og 301 (wind10)
                double temp = gridPointValueMap.get(s.getGridPoint()).getTemp();
                double wind10 = gridPointValueMap.get(s.getGridPoint()).getWind();

                LOGGER.debug("laeIndex = " + leeIndex);
                LOGGER.debug("temp = " + temp);
                LOGGER.debug("wind10 = " + wind10);

                double alfa = SnowfractionAlpha.getAlpha(temp);      // Beregn snefraktion ud fra temp

                LOGGER.debug("Gridv\u00e6rdier: temp = "+temp+", wind10 = "+wind10);
                LOGGER.debug("alfa = "+ alfa);

                // vindhastighed målt i 10 meter justeres ned til 1.5 meter
                double z0 = 0.25;
                double wind15 = Wind10toWind15.getWind15(wind10, z0);
                LOGGER.debug("Ruhedsparameter z0 = " + z0);
                LOGGER.debug("Vind nedjusteret til 1.5m, wind15: " + wind15);

                // vindhastigheden korrigeres for læindexet
                double wind15Lae = CorrWindLae.getVLae(leeIndex, wind15);
                // Flag for at laeIndex evt. er > 30
                double vLaeFlag = CorrWindLae.getVLaeFlag(leeIndex);

                LOGGER.debug("Vind korrigeret med l\u00e6index, wind15Lae = " +wind15Lae+ " --- vLaeFlag = " +vLaeFlag);

                if (wind15Lae < 0) {
                    wind15Lae = 0;
                }

                ValidWindTempIKs vwtIK = new ValidWindTempIKs();
                vwtIK.getWindValid(alfa, wind15Lae, label); // Evt. justering af wind15Lae i overensstemmelse med modellens gyldighedsområde
                double wind15LaeRainValid = vwtIK.getWindValidRain();
                double wind15LaeSnowValid = vwtIK.getWindValidSnow();
                double tempValid = vwtIK.getTempValid(alfa, temp);
                if (temp != tempValid) {
                    label += 100;
                }
                label = vwtIK.getLabel();

                Month month = date.getMonth(); //.get() .get(GregorianCalendar.MONTH) + 1;
//                int month = gcDate.get(GregorianCalendar.MONTH) + 1;
                double I = RainIntensityI.getI(month);

                double IValid = vwtIK.getIValid(alfa, I);
                if (I != IValid) {
                    label += 1000;
                }
//                LOGGER.debug("Validerede v\u00e6rdier: wind15Rain = {0}, wind15Snow ={1}, temp = {2}, I = {3}", new Object[]{wind15LaeRainValid, wind15LaeSnowValid, tempValid, IValid});


                double ks = CorrFactorSnow.getKs(wind15LaeSnowValid, tempValid,
                        s.getConstants().getBeta0(),
                        s.getConstants().getBeta1(),
                        s.getConstants().getBeta2(),
                        s.getConstants().getBeta3(),
                        I);
                /*double ks = CorrFactorSnow.getKs(wind15LaeSnowValid, tempValid, s.getBeta0(), s.getBeta1(), s.getBeta2(), s.getBeta3(), I);*/

//            double kr = CorrFactorRain.getKr(wind15LaeRainValid, s.getGamma0(), s.getGamma1(), s.getGamma2(), s.getGamma3(), I, s.getC());
                double kr = CorrFactorRain.getKr(wind15LaeRainValid,
                        s.getConstants().getGamma0(),
                        s.getConstants().getGamma1(),
                        s.getConstants().getGamma2(),
                        s.getConstants().getGamma3(),
                        I,
                        s.getConstants().getC());

                if (kr < 1) {
                    kr = 1;
                    LOGGER.debug("Kr < 1");
                }

                if (ks < 1) {
                    ks = 1;
                }

                LOGGER.debug("ksValid = "+ks+" -- kr = "+kr);

                //Get wetting loss
                double wr = s.getWettingLossRain(month);
                double ws = s.getWettingLossSnow(month);

//                LOGGER.debug("Wettingtab: wr = {0}, ws = {1}", new Object[]{wr, ws});

                double Pc;
                if (Pm == 0) {
                    Pc = 0;
                } else {
                    if (alfa == 0) {
                        Pc = kr * Pm + wr;
                    } else if (alfa == 1) {
                        Pc = ks * (Pm + ws);
                    } else {

                        LOGGER.debug("(1 - alfa) =" + (1 - alfa));
                        LOGGER.debug("kr * Pm + wr =" + kr * Pm + wr);
                        LOGGER.debug("alfa = " + alfa);
                        LOGGER.debug("ksValid = " + ks);
                        LOGGER.debug("Pm + ws = " + (Pm + ws));

                        Pc = (1 - alfa) * (kr * Pm + wr) + alfa * ks * (Pm + ws);
                    }
                }

                LOGGER.debug("Nedb\u00f8r Pm = "+Pm+" -- korrigeret nedb\u00f8r Pc = "+Pc);
                LOGGER.debug("---");

                // System.out.println(" " + statNo + " " + Pm + " " + Pc);
                //s.setCorrected(Pc);

                int status = 1;

                if (leeIndex <= 30) {

                    if (!statsToUpdate.contains(s)) {
                        statsToUpdate.add(s);
                    }
                    status = 0;
                }

                String formatString = "%s;%d;%d;%d;%d;%6s;%2d;%2.1f;%2.1f;%2.1f;%2.1f;%2.1f;%2.1f;%2.1f;%2.2f;%2.2f;%2.2f;%2.2f;%2.4f;%2.4f;%2.1f;%2.1f;%d;%d";
                output.add(String.format(formatString, DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date), s.getStatid(), eastings, northings, s.getGridPoint(), s.getConstants().getType(), leeIndex, temp, tempValid, wind10, wind15, wind15Lae, wind15LaeRainValid, wind15LaeSnowValid, alfa, I, IValid, z0, kr, ks, Pm, Pc, label, status));
            }
//            String formatString = "%s;%d;%d;%d;%d;%6s;%2d;%2.1f;%2.1f;%2.1f;%2.1f;%2.1f;%2.1f;%2.1f;%2.2f;%2.2f;%2.2f;%2.2f;%2.4f;%2.4f;%2.1f;%2.1f";
            //           output.add(String.format(formatString, sdf.format(gcDate.getTime()), s.getId(), eastings, northings, fieldId, s.getType(), laeIndex, temp, tempValid, wind10, wind15, wind15Lae, wind15LaeRainValid, wind15LaeSnowValid, alfa, I, IValid, z0, kr, ks, Pm, Pc, label));

        
        LOGGER.debug("<--- CalculateCorrectedPrecipitation end ");
        return output;
    }
}