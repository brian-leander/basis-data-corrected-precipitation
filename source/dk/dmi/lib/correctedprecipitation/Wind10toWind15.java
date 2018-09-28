package dk.dmi.lib.correctedprecipitation;

/**
 * Class that calculates wind speed at 1.5m height from wind speed mesured at
 * 10m height
 *
 * See IR11-10 page 7. In the equation a constant d is used however it is set to
 * 0
 */
public class Wind10toWind15 {

    /**
     * Calculates wind speed at 1.5m height from wind speed mesured at 10m
     * height
     *
     * @param wind10 wind speed at 10m height
     * @param z0 roughness parameter
     * @return wind speed at 1.5m height
     */
    public static double getWind15(double wind10, double z0) {
        double wind15 = wind10 * (Math.log10(1.5) - Math.log10(z0)) / (Math.log10(10) - Math.log10(z0));
        return wind15;
    }

    private Wind10toWind15() {
    }
}
