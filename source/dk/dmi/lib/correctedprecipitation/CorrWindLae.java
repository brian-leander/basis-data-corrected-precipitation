package dk.dmi.lib.correctedprecipitation;

/**
 * Class used for calculating wind speed at 1.5m corrected for lee effect See
 * IR11-10 page 8
 *
 */
public class CorrWindLae {

    private static final double c = 0.024;

    /**
     * Correction for lee effect
     *
     * @param leeIndex average of elevation anglesaround the precipitation gauge
     * @param wind15 wind spead at 1.5m height
     * @return returns lee corrected wind speed at 1.5m height
     */
    public static double getVLae(int leeIndex, double wind15) {
        double lambda = 1 - c * leeIndex;
        double vLae = lambda * wind15;

        return vLae;
    }

    /**
     * Returns if the lee index is too heigh
     *
     * @param leeIndex average of elevation anglesaround the precipitation gauge
     * @return 1 if leeIndex is above 30, otherwise 0
     */
    public static int getVLaeFlag(int leeIndex) {
        int vLaeFlag = 0;
        if (leeIndex > 30) {
            vLaeFlag = 1;
        }
        return vLaeFlag;
    }
}
