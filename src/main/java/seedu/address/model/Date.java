package seedu.address.model;

public class Date {

    private String date;

    public Date(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj ) {
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
}
