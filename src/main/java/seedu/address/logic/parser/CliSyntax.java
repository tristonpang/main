package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    /* For general usage */
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_NRIC = new Prefix("ic/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_MEDICAL_DEPARTMENT = new Prefix("md/");

    /* For Appointments */
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_START_TIME = new Prefix("st/");
    public static final Prefix PREFIX_END_TIME = new Prefix("et/");
    public static final Prefix PREFIX_DOCTOR_NAME = new Prefix("dn/");
    public static final Prefix PREFIX_PATIENT_NAME = new Prefix("pn/");
    public static final Prefix PREFIX_DOCTOR_NRIC = new Prefix("di/");
    public static final Prefix PREFIX_PATIENT_NRIC = new Prefix("pi/");

    /* For Medical Records */
    public static final Prefix PREFIX_MEDICAL_RECORD = new Prefix("mr/");
    public static final Prefix PREFIX_DIAGNOSIS = new Prefix("dg/");
    public static final Prefix PREFIX_TREATMENT = new Prefix("tr/");
    public static final Prefix PREFIX_COMMENT = new Prefix("c/");

    public static final Prefix PREFIX_GLOBAL = new Prefix("gl/");
}
