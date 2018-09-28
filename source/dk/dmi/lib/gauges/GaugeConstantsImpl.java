package dk.dmi.lib.gauges;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public enum GaugeConstantsImpl implements GaugeConstants {
    PLUVIO() {
        @Override
        public double getBeta0() {
            return Constants.PLUVIO_BETA0;
        }

        @Override
        public double getBeta1() {
            return Constants.PLUVIO_BETA1;
        }

        @Override
        public double getBeta2() {
            return Constants.PLUVIO_BETA2;
        }

        @Override
        public double getBeta3() {
            return Constants.PLUVIO_BETA3;
        }

        @Override
        public double getGamma0() {
            return Constants.PLUVIO_GAMMA0;
        }

        @Override
        public double getGamma1() {
            return Constants.PLUVIO_GAMMA1;
        }

        @Override
        public double getGamma2() {
            return Constants.PLUVIO_GAMMA2;
        }

        @Override
        public double getGamma3() {
            return Constants.PLUVIO_GAMMA3;
        }

        @Override
        public double getC() {
            return Constants.PLUVIO_C;
        }

        @Override
        public Map<Month, Double> getWlr() {
            return Constants.PLUVIO_WLR;
        }

        @Override
        public Map<Month, Double> getWls() {
            return Constants.PLUVIO_WLS;
        }

        @Override
        public String getType() {
            return this.name();
        }
    },
    RIMCO() {
        @Override
        public double getBeta0() {
            return Constants.RIMCO_BETA0;
        }

        @Override
        public double getBeta1() {
            return Constants.RIMCO_BETA1;
        }

        @Override
        public double getBeta2() {
            return Constants.RIMCO_BETA2;
        }

        @Override
        public double getBeta3() {
            return Constants.RIMCO_BETA3;
        }

        @Override
        public double getGamma0() {
            return Constants.RIMCO_GAMMA0;
        }

        @Override
        public double getGamma1() {
            return Constants.RIMCO_GAMMA1;
        }

        @Override
        public double getGamma2() {
            return Constants.RIMCO_GAMMA2;
        }

        @Override
        public double getGamma3() {
            return Constants.RIMCO_GAMMA3;
        }

        @Override
        public double getC() {
            return Constants.RIMCO_C;
        }

        @Override
        public Map<Month, Double> getWlr() {
            return Constants.RIMCO_WLR;
        }

        @Override
        public Map<Month, Double> getWls() {
            return Constants.RIMCO_WLS;
        }

        @Override
        public String getType() {
            return this.name();
        }
    },
    GEONOR() {
        @Override
        public double getBeta0() {
            return Constants.GEONOR_BETA0;
        }

        @Override
        public double getBeta1() {
            return Constants.GEONOR_BETA1;
        }

        @Override
        public double getBeta2() {
            return Constants.GEONOR_BETA2;
        }

        @Override
        public double getBeta3() {
            return Constants.GEONOR_BETA3;
        }

        @Override
        public double getGamma0() {
            return Constants.GEONOR_GAMMA0;
        }

        @Override
        public double getGamma1() {
            return Constants.GEONOR_GAMMA1;
        }

        @Override
        public double getGamma2() {
            return Constants.GEONOR_GAMMA2;
        }

        @Override
        public double getGamma3() {
            return Constants.GEONOR_GAMMA3;
        }

        @Override
        public double getC() {
            return Constants.GEONOR_C;
        }

        @Override
        public Map<Month, Double> getWlr() {
            return Constants.GEONOR_WLR;
        }

        @Override
        public Map<Month, Double> getWls() {
            return Constants.GEONOR_WLS;
        }

        @Override
        public String getType() {
            return this.name();
        }
    };

    private static class Constants {

        public static final double PLUVIO_BETA0 = 0.04587;
        public static final double PLUVIO_BETA1 = 0.23677;
        public static final double PLUVIO_BETA2 = 0.017979;
        public static final double PLUVIO_BETA3 = -0.015407;

        public static final double PLUVIO_GAMMA0 = 0.007697;
        public static final double PLUVIO_GAMMA1 = 0.034331;
        public static final double PLUVIO_GAMMA2 = -0.00101;
        public static final double PLUVIO_GAMMA3 = -0.012177;

        public static final double PLUVIO_C = 0.0;

        static final Map<Month, Double> PLUVIO_WLR ;
        static final Map<Month, Double> PLUVIO_WLS ;
        static {
            PLUVIO_WLR = new HashMap<>(12);
            PLUVIO_WLR.put(Month.JANUARY, 0d);
            PLUVIO_WLR.put(Month.FEBRUARY, 0d);
            PLUVIO_WLR.put(Month.MARCH, 0d);
            PLUVIO_WLR.put(Month.APRIL, 0d);
            PLUVIO_WLR.put(Month.MAY, 0d);
            PLUVIO_WLR.put(Month.JUNE, 0d);
            PLUVIO_WLR.put(Month.JULY, 0d);
            PLUVIO_WLR.put(Month.AUGUST, 0d);
            PLUVIO_WLR.put(Month.SEPTEMBER, 0d);
            PLUVIO_WLR.put(Month.OCTOBER, 0d);
            PLUVIO_WLR.put(Month.NOVEMBER, 0d);
            PLUVIO_WLR.put(Month.DECEMBER, 0d);

            PLUVIO_WLS = new HashMap<>(12);
            PLUVIO_WLS.put(Month.JANUARY, 0d);
            PLUVIO_WLS.put(Month.FEBRUARY, 0d);
            PLUVIO_WLS.put(Month.MARCH, 0d);
            PLUVIO_WLS.put(Month.APRIL, 0d);
            PLUVIO_WLS.put(Month.MAY, 0d);
            PLUVIO_WLS.put(Month.JUNE, 0d);
            PLUVIO_WLS.put(Month.JULY, 0d);
            PLUVIO_WLS.put(Month.AUGUST, 0d);
            PLUVIO_WLS.put(Month.SEPTEMBER, 0d);
            PLUVIO_WLS.put(Month.OCTOBER, 0d);
            PLUVIO_WLS.put(Month.NOVEMBER, 0d);
            PLUVIO_WLS.put(Month.DECEMBER, 0d);
        }


        public static final double RIMCO_BETA0 = 0.04587;
        public static final double RIMCO_BETA1 = 0.23677;
        public static final double RIMCO_BETA2 = 0.017979;
        public static final double RIMCO_BETA3 = -0.015407;

        public static final double RIMCO_GAMMA0 = 0.007697;
        public static final double RIMCO_GAMMA1 = 0.034331;
        public static final double RIMCO_GAMMA2 = -0.00101;
        public static final double RIMCO_GAMMA3 = -0.012177;

        public static final double RIMCO_C = 0.0;

        static final Map<Month, Double> RIMCO_WLR ;
        static final Map<Month, Double> RIMCO_WLS ;
        static {
            RIMCO_WLR = new HashMap<>(12);
            RIMCO_WLR.put(Month.JANUARY, 0.05);
            RIMCO_WLR.put(Month.FEBRUARY, 0.06);
            RIMCO_WLR.put(Month.MARCH, 0.07);
            RIMCO_WLR.put(Month.APRIL, 0.10);
            RIMCO_WLR.put(Month.MAY, 0.12);
            RIMCO_WLR.put(Month.JUNE, 0.13);
            RIMCO_WLR.put(Month.JULY, 0.13);
            RIMCO_WLR.put(Month.AUGUST, 0.12);
            RIMCO_WLR.put(Month.SEPTEMBER, 0.11);
            RIMCO_WLR.put(Month.OCTOBER, 0.08);
            RIMCO_WLR.put(Month.NOVEMBER, 0.06);
            RIMCO_WLR.put(Month.DECEMBER, 0.05);

            RIMCO_WLS = new HashMap<>(12);
            RIMCO_WLS.put(Month.JANUARY, 0.05);
            RIMCO_WLS.put(Month.FEBRUARY, 0.06);
            RIMCO_WLS.put(Month.MARCH, 0.07);
            RIMCO_WLS.put(Month.APRIL, 0.10);
            RIMCO_WLS.put(Month.MAY, 0.12);
            RIMCO_WLS.put(Month.JUNE, 0.13);
            RIMCO_WLS.put(Month.JULY, 0.13);
            RIMCO_WLS.put(Month.AUGUST, 0.12);
            RIMCO_WLS.put(Month.SEPTEMBER, 0.11);
            RIMCO_WLS.put(Month.OCTOBER, 0.08);
            RIMCO_WLS.put(Month.NOVEMBER, 0.06);
            RIMCO_WLS.put(Month.DECEMBER, 0.05);
        }

        public static final double GEONOR_BETA0 = -0.12159;
        public static final double GEONOR_BETA1 = 0.18546;
        public static final double GEONOR_BETA2 = 0.006918;
        public static final double GEONOR_BETA3 = -0.005254;

        public static final double GEONOR_GAMMA0 = 0.007697;
        public static final double GEONOR_GAMMA1 = 0.034331;
        public static final double GEONOR_GAMMA2 = -0.00101;
        public static final double GEONOR_GAMMA3 = -0.012177;

        public static final double GEONOR_C = -0.05;

        static final Map<Month, Double> GEONOR_WLR ;
        static final Map<Month, Double> GEONOR_WLS ;
        static {
            GEONOR_WLR = new HashMap<>(12);
            GEONOR_WLR.put(Month.JANUARY, 0d);
            GEONOR_WLR.put(Month.FEBRUARY, 0d);
            GEONOR_WLR.put(Month.MARCH, 0d);
            GEONOR_WLR.put(Month.APRIL, 0d);
            GEONOR_WLR.put(Month.MAY, 0d);
            GEONOR_WLR.put(Month.JUNE, 0d);
            GEONOR_WLR.put(Month.JULY, 0d);
            GEONOR_WLR.put(Month.AUGUST, 0d);
            GEONOR_WLR.put(Month.SEPTEMBER, 0d);
            GEONOR_WLR.put(Month.OCTOBER, 0d);
            GEONOR_WLR.put(Month.NOVEMBER, 0d);
            GEONOR_WLR.put(Month.DECEMBER, 0d);

            GEONOR_WLS = new HashMap<>(12);
            GEONOR_WLS.put(Month.JANUARY, 0d);
            GEONOR_WLS.put(Month.FEBRUARY, 0d);
            GEONOR_WLS.put(Month.MARCH, 0d);
            GEONOR_WLS.put(Month.APRIL, 0d);
            GEONOR_WLS.put(Month.MAY, 0d);
            GEONOR_WLS.put(Month.JUNE, 0d);
            GEONOR_WLS.put(Month.JULY, 0d);
            GEONOR_WLS.put(Month.AUGUST, 0d);
            GEONOR_WLS.put(Month.SEPTEMBER, 0d);
            GEONOR_WLS.put(Month.OCTOBER, 0d);
            GEONOR_WLS.put(Month.NOVEMBER, 0d);
            GEONOR_WLS.put(Month.DECEMBER, 0d);
        }
    }
}
