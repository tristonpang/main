package seedu.address.model.person;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Represents a time in the address book.
 */
public class Time {

    /*
     * Time should be in 24 hour format, HHMM.
     */
    public static final String TIME_VALIDATION_REGEX = "^\\d{4}";

    public static final String MESSAGE_TIME_INVALID_FORMAT_CONSTRAINTS = "Time should be entered in 24 hr clock format."
            + " e.g. 1330 represents 1:30 pm. ";
    public static final String MESSAGE_TIME_INVALID_DOES_NOT_EXIST = "This time does not exist: ";

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
    public boolean comesStrictlyBefore(Time otherTiming) {
        int currentTime = Integer.parseInt(time.trim());
        int otherTime = Integer.parseInt(otherTiming.time.trim());
        return (currentTime < otherTime);
    }

    /**
     * @return whether this time is a valid time.
     */
    public boolean isValidTime() {
        return isCorrectFormat() && doesExist();
    }

    private boolean isCorrectFormat() {
        return time.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Checks if a given time in the correct format exists.
     * @return whether the given time exists.
     */
    private boolean doesExist() {
        if (!isCorrectFormat()) {
            return false;
        }
        boolean result = true;
        int hour = Integer.parseInt(time.substring(0, 2));
        int minute = Integer.parseInt(time.substring(2));
        if (hour < 0 || minute < 0) {
            result = false;
        } else if (hour > 23 || minute > 59) {
            result = false;
        }
        return result;
    }

    public String getFailureReason() {
        if (isValidTime()) {
            return "Time is valid.";
        }
        String reason;
        if (!isCorrectFormat()) {
            reason = MESSAGE_TIME_INVALID_FORMAT_CONSTRAINTS;
        } else {
            reason = MESSAGE_TIME_INVALID_DOES_NOT_EXIST + time;
        }
        return reason;
    }

    public static Time getCurrentTime() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Singapore"));
        String stringZonedDateTime = zonedDateTime.toString();
        // Splitting output from API into a date part and a time part.
        String[] dateAndTimeParts = stringZonedDateTime.split("T");

        // Reformatting the oder of the time.
        String currentTime = dateAndTimeParts[1];
        String[] currentTimeParts = currentTime.split(":");
        String hour = currentTimeParts[0];
        String minute = currentTimeParts[1];
        currentTime = hour + minute;

        return new Time(currentTime);
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
