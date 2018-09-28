package dk.dmi.lib.gauges;

import java.time.Month;
import java.util.Map;

public interface GaugeConstants {
    double getBeta0();
    double getBeta1();
    double getBeta2();
    double getBeta3();
    double getGamma0();
    double getGamma1();
    double getGamma2();
    double getGamma3();
    double getC();
    Map<Month, Double> getWlr();
    Map<Month, Double> getWls();
    String getType();
}
