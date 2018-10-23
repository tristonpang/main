package seedu.address.model.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a date in the address book.
 */
public class Date {

    /*
     * Dates should be in DD.MM.YYYY format. Date and month can be 1 or 2 digits long
     */
    public static final String DATE_VALIDATION_REGEX = "^\\d{1,2}\\.\\d{1,2}\\.\\d{4}";

    public static final String MESSAGE_DATE_INVALID_FORMAT_CONSTRAINTS = "Dates should be entered in DD.MM.YYYY format."
            + " Date and month can have 1 or 2 digits, but the year must be 4 digits.";
    public static final String MESSAGE_DATE_INVALID_DOES_NOT_EXIST = "This date does not exist: ";

    private final ArrayList<Integer> monthsWithThirtyOneDays = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));
    private final ArrayList<Integer> monthsWithThirtyDays = new ArrayList<>(Arrays.asList(4, 6, 9, 11));

    /** String representation of the date */
    private String date;


    public Date(String date) {
        this.date = date;
    }

    /**
     * Checks if the date is a valid date.
     * @return whether this date is a valid date.
     */
    public boolean isValid() {
        return isCorrectFormat() && doesExist();
    }

    private boolean isCorrectFormat() {
        return this.date.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Checks if a given date in the correct format exists.
     * @return whether given date exists.
     */
    private boolean doesExist() {
        if (!isCorrectFormat()) {
            return false;
        }
        boolean result = true;
        List<String> valueList = Arrays.asList(this.date.split("\\."));
        int date = Integer.parseInt(valueList.get(0));
        int month = Integer.parseInt(valueList.get(1));
        int year = Integer.parseInt(valueList.get(2));
        if (date < 1) {
            result = false;
        } else if (monthsWithThirtyDays.contains(month) && (date > 30)) {
            result = false;
        } else if (monthsWithThirtyOneDays.contains(month) && (date > 31)) {
            result = false;
        } else if (isLeapYear(year) && (date > 29)) {
            result = false;
        } else if (!isLeapYear(year) && (date > 28)) {
            result = false;
        }
        return result;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }

    public String getFailureReason() {
        if(isValid()) {
            return "Date is valid.";
        }
        String reason;
        if (!isCorrectFormat()) {
            reason = MESSAGE_DATE_INVALID_FORMAT_CONSTRAINTS;
        } else {
            reason = MESSAGE_DATE_INVALID_DOES_NOT_EXIST + this.date;
        }
        return reason;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Date) {
            Date otherDate = (Date) obj;
            return date.equals(otherDate.date);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.date;
    }
}
