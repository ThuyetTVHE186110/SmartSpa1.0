package model;

public class PopularService {
    private int id;
    private String name;
    private int bookingCount;
    private int trend;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(int bookingCount) {
        this.bookingCount = bookingCount;
    }

    public int getTrend() {
        return trend;
    }

    public void setTrend(int trend) {
        this.trend = trend;
    }

    // Helper methods for JSP
    public boolean isPositiveTrend() {
        return trend >= 0;
    }

    public int getAbsoluteTrend() {
        return trend >= 0 ? trend : -trend;
    }

    public String getTrendDirection() {
        return trend >= 0 ? "up" : "down";
    }
} 