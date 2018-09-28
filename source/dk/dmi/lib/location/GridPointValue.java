package dk.dmi.lib.location;

public class GridPointValue {
    private double temp, wind;

    public double getTemp() {
        return temp;
    }

    public double getWind() {
        return wind;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public String toString() {
        return "temp: " + temp + " -- wind: " + wind;
    }
}
