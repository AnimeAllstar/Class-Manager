/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

/**
 * @author Asad
 */
public class StudentLog {

    private String classId;
    private String date;
    private String paymentStatus;
    private String feedback;
    private Integer duration;
    private String summary;
    private Double fee;

    public StudentLog(String classId, String date, String paymentStatus, String feedback, Integer duration, String summary, double fee) {
        this.classId = classId;
        this.date = date;
        this.paymentStatus = paymentStatus;
        this.feedback = feedback;
        this.duration = duration;
        this.summary = summary;
        this.fee = fee;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "StudentLog{" + "classId=" + classId + ", date=" + date + ", paymentStatus=" + paymentStatus + ", feedback=" + feedback + ", duration=" + duration + ", summary=" + summary + '}';
    }
}
