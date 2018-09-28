package dk.dmi.lib.workflow.component;

import dk.dmi.lib.db.NovanaDB;
import dk.dmi.lib.db.StatDB;
import dk.dmi.lib.gauges.GaugeConstants;
import dk.dmi.lib.gauges.GaugeConstantsImpl;
import dk.dmi.lib.gauges.Station;
import dk.dmi.lib.location.GridClassifier;
import dk.dmi.lib.location.GridPoint;
import dk.dmi.lib.location.Location;
import dk.dmi.lib.persistence.database.climadb.publicc.controller.BasisDailyDkController;
import dk.dmi.lib.persistence.database.climadb.publicc.entity.BasisDailyDk;
import dk.dmi.lib.persistence.database.statdb.publicc.controller.LeeIndexController;
import dk.dmi.lib.persistence.database.statdb.publicc.controller.PositionController;
import dk.dmi.lib.persistence.database.statdb.publicc.entity.Leeindex;
import dk.dmi.lib.persistence.database.statdb.publicc.entity.Position;
import dk.dmi.lib.persistence.database.statdb.publicc.entity.PositionUtmWgs84Z32;
import dk.dmi.lib.persistence.database.statdbparameter.publicc.controller.StatObsCodeController;
import dk.dmi.lib.persistence.database.statdbparameter.publicc.entity.StatObsCode;
import dk.dmi.lib.workflow.common.BaseComponent;
import dk.dmi.lib.workflow.common.WorkflowAnnotations;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WorkflowAnnotations.Component(
        name = "Get Precipitation Stations",
        category = "Basis Data",
        description = "Calculates corrected station precipitation values for a given date",
        version = 1)
public class GetActivePrecipitationStations extends BaseComponent {

    @WorkflowAnnotations.ExecuteMethod(
            argumentDisplayTypes = {WorkflowAnnotations.ARGUMENT_DISPLAY_TYPE_TEXT},
            argumentDescriptions = {""},
            returnDescription = "")
    public List<Station> getActivePrecipitationStations(ZonedDateTime date) {
        Date theDate = new Date(date.toInstant().toEpochMilli());

        List<StatObsCode> statObsCodeList = StatObsCodeController.getInstance().getStatObsCodeForPrecipitationStations(theDate);
        Map<Integer, String> stationCode = statObsCodeList.stream().collect(Collectors.toMap(statObsCode -> statObsCode.getId().getStatid(), statObsCode -> statObsCode.getId().getObsCode()));

        List<Leeindex> leeindexList = LeeIndexController.getInstance().getLeeIndexForPrecipitationStations(theDate);
        Map<Integer, Integer> leeStats = leeindexList.stream().
                collect(Collectors.toMap(
                        leeindex -> leeindex.getId().getStatid(),
                        leeindex -> leeindex.getIndex()));

        List<PositionUtmWgs84Z32> positions = PositionController.getInstance().getPositionWgs84Z32(date);
        Map<Integer, Location> locations = positions.stream().
                collect(Collectors.toMap(
                        pos -> pos.getStatid(),
                        pos -> new Location(pos.getStatid(), (int) Math.round(pos.getEasting()), (int) Math.round(pos.getNorthing()))));

        NovanaDB ndb = new NovanaDB("wwwdb.dmi.dk:3306", "novadba", "hmjkir", "novana2DB");
        ndb.open();
        List<GridPoint> gps = ndb.ReadGridpoints();

        ndb.close();

        List<BasisDailyDk> basisDailyDks = BasisDailyDkController.getInstance().getBasisDailyDkByDateAndElementNumber(theDate, 601);
        List<Station> stations = new ArrayList<>();
        for (BasisDailyDk basisDailyDk : basisDailyDks) {

            String code = stationCode.get(new Integer(basisDailyDk.getStatid()));
            if (code == null) {
                LOGGER.warn("No type code for station " + basisDailyDk.getStatid() + ". Station ignored.");
                continue;
            }

            final Location location = locations.get(basisDailyDk.getStatid());
            int fieldId = new GridClassifier(gps).getGridPoint(location, basisDailyDk.getStatid());

            try {
                Station station = new Station.Builder().
                        statid(basisDailyDk.getStatid()).
                        measured(basisDailyDk.getValue()).
                        leeindex(leeStats.get(basisDailyDk.getStatid())).
                        qualityLabel(basisDailyDk.getLabel()).
                        endHour(basisDailyDk.getEndHour()).
                        constants(code).
                        location(location).
                        gridPoint(fieldId).
                        build();

                stations.add(station);
            } catch (IllegalArgumentException e) {
                LOGGER.warn(e.getMessage());
            }
        }

        return stations;
    }
}
