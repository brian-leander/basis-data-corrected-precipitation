package dk.dmi.lib.correctedprecipitation;

/**
 * Class used for calculating the fraction of snow 
 * See IR11-10 page 9
 */
public class SnowfractionAlpha {

    /**
     * Type of precipitation is classified using average temperature
     *
     * @return the fraction of snow
     */
    public static double getAlpha(double temp) {
        double alfa;
        if (temp > 2.0) {
            alfa = 0;
        } else if (temp <= 0) {
            alfa = 1;
        } else {
            alfa = -0.5 * temp + 1;
        }
        return alfa;
    }
}
