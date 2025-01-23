package repository;

public class weatherInterval {
    private int startHour;
    private int endHour;
    private double temp;
    private int precip;
    private String description;

    public weatherInterval(int startHour, int endHour, double temp, int precip, String description) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.temp = temp;
        this.precip = precip;
        this.description = description;
    }

    public int getStartH() {
        return startHour;
    }

    public int getEndH() {
        return endHour;
    }

    public double getTemp() {
        return temp;
    }

    public int getPrecip() {
        return precip;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Start: " + startHour + ", End: " + endHour + ", Temp: " + temp + ", Precip: " + precip + ", Desc: " + description;
    }
}
