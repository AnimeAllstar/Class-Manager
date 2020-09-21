/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

import java.time.LocalDate;

/**
 * @author asad
 */
public class Student {

    private String studentId;
    private String firstName;
    private String lastName;
    private String standard;
    private LocalDate DOB;
    private String notes;
    private String school;

    public Student(String studentId, String firstName, String lastName, String standard, LocalDate DOB, String notes, String school) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.standard = standard;
        this.DOB = DOB;
        this.notes = notes;
        this.school = school;
    }

    public Student(String studentId, String firstName, String lastName, String standard, String school) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.standard = standard;
        this.school = school;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" + "studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", standard=" + standard
                + ", DOB=" + DOB + ", notes=" + notes + ", school=" + school + '}';
    }

}
