package seedu.address.model;

public class Time {

    private String time;

    public Time(String time) {
        this.time = time;
    }

    public boolean comesBefore(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime =  Integer.parseInt(otherTiming.time.trim());
        return (currentTime <= otherTime);
    }

    public boolean comesAfter(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime =  Integer.parseInt(otherTiming.time.trim());
        return (currentTime >= otherTime);
    }

    public boolean comesBeforeStrictly(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime =  Integer.parseInt(otherTiming.time.trim());
        return (currentTime < otherTime);
    }

    public boolean comesAfterStrictly(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime =  Integer.parseInt(otherTiming.time.trim());
        return (currentTime > otherTime);
    }

    @Override
    public boolean equals(Object obj ) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Time) {
            Time otherTime = (Time) obj;
            return time.equals(otherTime.time);
        } else {
            return false;
        }
    }
}
