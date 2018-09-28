package dk.dmi.lib.correctedprecipitation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used for calculating the correction factor for snow 
 * See IR11-10 page 6
 *
 */
public class CorrFactorSnow {

    private static final Logger LOG = Logger.getLogger(CorrFactorSnow.class.getName());

    /**
     * Method that calculates correction factor for snow (Ks)
     *
     * @param wind15 - Wind speed at 1.5m height
     * @param temp - Temperature at 2m
     * @param beta0 - Constant in the correction model
     * @param beta1 - Constant in the correction model
     * @param beta2 - Constant in the correction model
     * @param beta3 - Constant in the correction model
     * @param I - Rain intensity mm/hour
     * @return The correction factor for snow
     */
    public static double getKs(double wind15, double temp, double beta0, double beta1, double beta2, double beta3, double I) {

        double power = beta0 + beta1 * wind15 + beta2 * temp + beta3 * wind15 * temp;

        LOG.log(Level.FINE, "Fra CorrFactorSnow: getKs: power (snow) = {0}", power);

        double ks = Math.exp(power);
        return ks;
    }

    private CorrFactorSnow() {
    }
}
