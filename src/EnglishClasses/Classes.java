/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnglishClasses;

/**
 *
 * @author asad
 */
public class Classes {

    private String classId;
    private double fee;
    private String summary;
    private int duration;

    public Classes(String classId, double fee, String summary, int duration) {
        this.classId = classId;
        this.fee = fee;
        this.summary = summary;
        this.duration = duration;
    }

    public String getClassId() {
        return classId;
    }

    public double getFee() {
        return fee;
    }

    public String getSummary() {
        return summary;
    }

    public int getDuration() {
        return duration;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Classes{" + "classId=" + classId + ", fee=" + fee + ", summary=" + summary + ", duration=" + duration + '}';
    }

}
