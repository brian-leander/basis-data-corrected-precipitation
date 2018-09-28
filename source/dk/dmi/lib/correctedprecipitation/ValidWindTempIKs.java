package dk.dmi.lib.correctedprecipitation;

/**
 *
 * @author hmj
 */
public class ValidWindTempIKs {

    private double windValidSnow, windValidRain, tempValid, IValid, ksValid;
    private long lab;

    public void getWindValid(double alfa, double wind, long label) {
        windValidSnow = wind;
        windValidRain = wind;
        lab = label;
        if (alfa == 0) {   // Regn
            if (wind > 15.0) {
                windValidRain = 15.0;
                lab += 30;
            }
        } else if (alfa == 1) {           // Sne
            if (wind > 7.0) {
                windValidSnow = 7.0;
                lab += 20;
            }
        } else {           // Regn og sne
            if (wind > 7.0) {
                windValidSnow = 7.0;
                lab += 20;
            }
            if (wind > 15.0) {
                windValidRain = 15.0;
                // bruges ikke pt.
            }
        }
    }

    public double getWindValidSnow() {
        return windValidSnow;
    }

    public double getWindValidRain() {
        return windValidRain;
    }

    public long getLabel() {
        return lab;
    }

    public double getTempValid(double alfa, double temp) {
        tempValid = temp;
        if (temp < -12.0) {
            tempValid = -12.0;
        }

        return tempValid;
    }

    public double getIValid(double alfa, double I) {
        IValid = I;
        if (I > 15.0) {
            IValid = 15.0;
        }
        return IValid;
    }
}