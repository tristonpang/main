package seedu.address.model;

/**
 * Represents a time in the address book.
 */
public class Time {

    /** String representation of the time **/
    private String time;

    public Time(String time) {
        this.time = time;
    }

    /**
     * Checks loosely if current time is before other time.
     * Loosely means if the 2 times are equal, the method will still
     * return true.
     *
     * @param otherTiming
     * @return boolean stating if current time comes before otherTiming
     */
    public boolean comesBefore(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime =  Integer.parseInt(otherTiming.time.trim());
        return (currentTime <= otherTime);
    }

    /**
     * Checks loosely if current time is after other time.
     * Loosely means if the 2 times are equal, the method will still
     * return true.
     *
     * @param otherTiming
     * @return
     */
    public boolean comesAfter(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime =  Integer.parseInt(otherTiming.time.trim());
        return (currentTime >= otherTime);
    }

    /**
     * Checks strictly if current time is before other time.
     * Strictly means current time must be strictly less than other time
     * for the method to return true.
     *
     * @param otherTiming
     * @return boolean stating if current time comes before otherTiming
     */
    public boolean comesBeforeStrictly(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime =  Integer.parseInt(otherTiming.time.trim());
        return (currentTime < otherTime);
    }

    /**
     * Checks loosely if current time is after other time.
     * Strictly means current time must be strictly greater than other time
     * for the method to return true.
     *
     * @param otherTiming
     * @return
     */
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
