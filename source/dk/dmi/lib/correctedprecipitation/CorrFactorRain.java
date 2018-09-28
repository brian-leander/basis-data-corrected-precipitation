package dk.dmi.lib.correctedprecipitation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used for calculating the correction factor for rain 
 * See IR11-10 page 6
 *
 */
public class CorrFactorRain {

    private static final Logger LOG = Logger.getLogger(CorrFactorRain.class.getName());

    /**
     * Calculates the correction factor for rain (Kr)
     *
     * @param wind15 wind speed at 1.5m height
     * @param gamma0 Constant in the correction model
     * @param gamma1 Constant in the correction model
     * @param gamma2 Constant in the correction model
     * @param gamma3 Constant in the correction model
     * @param I precipitation intensity in mm/hour
     * @param c Constant in the correction model
     * @return correction factor for rain
     */
    public static double getKr(double wind15, double gamma0, double gamma1, double gamma2, double gamma3, double I, double c) {

        double power = gamma0 + gamma1 * wind15 + gamma2 * Math.log(I) + gamma3 * wind15 * Math.log(I) + c;

        LOG.log(Level.FINE, "{0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}", new Object[]{gamma0, gamma1, gamma2, gamma3, wind15, I, Math.log(I), c});
        LOG.log(Level.FINE, "gamma1*wind15 = {0}", gamma1 * wind15);
        LOG.log(Level.FINE, "gamma2*Math.log(I) = {0}", gamma2 * Math.log(I));
        LOG.log(Level.FINE, "gamma3*wind15*Math.log(I) = {0}", gamma3 * wind15 * Math.log(I));
        LOG.log(Level.FINE, "Fra CorrFactorRain:getKr: power (rain) = {0}", power);

        double kr = Math.exp(power);
        return kr;
    }

    private CorrFactorRain() {
    }
}
