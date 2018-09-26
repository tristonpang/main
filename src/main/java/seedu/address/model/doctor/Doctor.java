package seedu.address.model.doctor;

import java.util.ArrayList;
import java.util.Set;
import seedu.address.model.department.MedicalDepartment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAdaptedDoctor;
import seedu.address.storage.XmlAdaptedPerson;

public class Doctor extends Person {
    
    private final MedicalDepartment dept;
    // TODO: Change ArrayList to ArrayList<Appointment>
    private ArrayList<String> appointments = new ArrayList<>();
    
    public Doctor(Name name, Phone phone, Email email, Address address, Set<Tag> tags, MedicalDepartment dept) {
        
        super(name, phone, email, address, tags);
        this.dept = dept;
        // this.appointments = new ArrayList<>();
        
        // TODO: 25/9/2018 Remove this pseudo variable 
        ArrayList<String> appt = new ArrayList<>();
        appt.add("Eat Pray Shit...");
        appt.add("Tom 1130 1230");
        
        this.appointments = appt;
    }
    
    public Doctor(Person person, MedicalDepartment dept, Set<String> appointments) {
        super(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(), person.getTags());
        this.dept = dept;
        this.appointments.addAll(appointments);
    }
    
    public MedicalDepartment getMedicalDepartment () {        
        return this.dept;
    }
    
    public ArrayList<String> getAppointments() {
        return this.appointments;
    }
    
    public boolean isSamePerson(Doctor otherDoctor) {
        return super.isSamePerson(otherDoctor)
                && (this.dept == otherDoctor.dept);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } 
        if (obj instanceof Doctor) {
            Doctor otherDoctor = (Doctor) obj;
            return (super.equals(otherDoctor)) 
                    && (otherDoctor.dept.equals(this.dept));
        } else {
            return false;
        }
    }
    
    public void scheduleAppointment(String patientName, String date, String timeStart, String timeEnd) {
        // TODO: AppointmentManager.add(appointments, new Appointment(...));
    }
    
    @Override
    public XmlAdaptedPerson toXmlVersion(Person source) {
        return XmlAdaptedDoctor.adaptToXml((Doctor) source);
    }
    
    @Override
    public String toString() {
        return super.toString() + (" Department: " + this.dept);
    }
}
