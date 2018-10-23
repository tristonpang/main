package seedu.address.model.person;

/**
 * Represents a time in the address book.
 */
public class Time {

    /** String representation of the time **/
    private String time;

    /*
     * Time should be in 24 hour format, HHMM.
     */
    public static final String TIME_VALIDATION_REGEX = "^\\d{4}";

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
        int otherTime = Integer.parseInt(otherTiming.time.trim());
        return (currentTime <= otherTime);
    }

    /**
     * Checks loosely if current time is after other time.
     * Loosely means if the 2 times are equal, the method will still
     * return true.
     *
     * @param otherTiming
     * @return oolean stating if current time comes after otherTiming
     */
    public boolean comesAfter(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime = Integer.parseInt(otherTiming.time.trim());
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
        int otherTime = Integer.parseInt(otherTiming.time.trim());
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
        int otherTime = Integer.parseInt(otherTiming.time.trim());
        return (currentTime > otherTime);
    }

    /**
     *
     * @return whether this time is a valid time.
     */
    public boolean isValid() {
        return isCorrectFormat() && doesExist();
    }

    private boolean isCorrectFormat() {
        return this.time.matches(TIME_VALIDATION_REGEX);
    }

    private boolean doesExist() {
        boolean result = true;
        int hour = Integer.parseInt(this.time.substring(0,2));
        int minute = Integer.parseInt(this.time.substring(2));
        if (hour < 0 || minute < 0) {
            result = false;
        } else if (hour > 23 || minute > 59) {
            result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object obj) {
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
