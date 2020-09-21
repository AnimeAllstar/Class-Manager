/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

/**
 * @author Asad
 */
public class ClassLog {

    private String studentId;
    private String classId;
    private String fullName;
    private String classDescription;
    private double classFee;
    private String classDate;
    private String classPayment;

    public ClassLog(String studentId, String classId, String fullName, String classDescription, double classFee, String classDate, String classPayment) {
        this.studentId = studentId;
        this.classId = classId;
        this.fullName = fullName;
        this.classDescription = classDescription;
        this.classFee = classFee;
        this.classDate = classDate;
        this.classPayment = classPayment;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public double getClassFee() {
        return classFee;
    }

    public void setClassFee(double classFee) {
        this.classFee = classFee;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getClassPayment() {
        return classPayment;
    }

    public void setClassPayment(String classPayment) {
        this.classPayment = classPayment;
    }

    public void setfulltName(String lastName) {
        this.fullName = lastName;
    }

    @Override
    public String toString() {
        return "ClassLog{" + "studentId=" + studentId + ", classId=" + classId + ", fullName=" + fullName + ", classDescription=" + classDescription + ", classFee=" + classFee + ", classDate=" + classDate + ", classPayment=" + classPayment + '}';
    }
}
