package dk.dmi.lib.gauges;

import dk.dmi.lib.location.Location;

import java.time.Month;

public class Station {
    protected String type;
    private int statid;
    private int leeindex;
    private double measured;
    private long qualityLabel;
    private int endHour;
    private int gridPoint;
    private GaugeConstants constants;
    private Location location;

    public static class Builder {
        protected String type;
        private int statid;
        private int leeindex;
        private double measured;
        private long qualityLabel;
        private int endHour;
        private int gridPoint;
        private GaugeConstants constants;
        private Location location;

        public Builder type(String value) {
            type = value;
            return this;
        }
        public Builder statid(int value) {
            statid = value;
            return this;
        }
        public Builder leeindex(Integer value) {
            if (value == null) {
                throw new IllegalArgumentException("No lee index found for station " + statid + ". Station ignored.");
            }
            leeindex = value;
            return this;
        }
        public Builder measured(double value) {
            measured = value;
            return this;
        }
        public Builder qualityLabel(long value) {
            qualityLabel = value;
            return this;
        }
        public Builder endHour(int value) {
            endHour = value;
            return this;
        }
        public Builder gridPoint(int value) {
            gridPoint = value;
            return this;
        }
        public Builder constants(String value) throws IllegalArgumentException {
            if (value == null) {
                throw new IllegalArgumentException("Type code for station " + statid + " is not found. Station ignored.");
            }

            switch (value) {
                case "rimco":
                    constants = GaugeConstantsImpl.RIMCO;
                    break;
                case "PSVK":
                    constants = GaugeConstantsImpl.RIMCO;
                    break;
                case "geonor":
                    constants = GaugeConstantsImpl.GEONOR;
                    break;
                case "pluvio":
                    constants = GaugeConstantsImpl.PLUVIO;
                    break;
                default:
                    throw new IllegalArgumentException(value + " is unknown type code for station " + statid + ". Station ignored.");
            }

            return this;
        }
        public Builder location(Location value) {
            location = value;
            return this;
        }
        public Station build() {
            return new Station(this);
        }
    }

    public Station(Builder builder) {

    }

    public Station(int id, double measured, int leeindex, long qualityLabel, int endHour, GaugeConstants constants) {
        //super(id, 0, 0);
        this.statid = id;
        this.leeindex = leeindex;
        this.measured = measured;
        this.qualityLabel = qualityLabel;
        this.endHour = endHour;
        this.constants = constants;
    }

    public int getStatid() {
        return statid;
    }

    public double getValue() {
        return measured;
    }

    public int getLeeindex() {
        return leeindex;
    }

    public void setLeeIndex(int leeIndex) {
        this.leeindex = leeIndex;
    }

    public void setGridPoint(int gridPoint) {
        this.gridPoint = gridPoint;
    }

    public int getGridPoint() {
        return gridPoint;
    }

    @Override
    public String toString() {
        return "ID: " + statid + " -- leeindex: " + leeindex + " -- gridpoint: " + gridPoint;
    }

    public GaugeConstants getConstants() {
        return constants;
    }

    public double getWettingLossRain(Month month) {
        return constants.getWlr().get(month);
    }

    public double getWettingLossSnow(Month month) {
        return constants.getWls().get(month);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
