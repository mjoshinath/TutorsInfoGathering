
package model.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class AcademicDegreesAttribute {

    private String name;
    @SerializedName("school_name")
    @Expose
    private String schoolName;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("field_of_study")
    @Expose
    private String fieldOfStudy;
    private String location;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The schoolName
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * @param schoolName The school_name
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * @return The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return The fieldOfStudy
     */
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    /**
     * @param fieldOfStudy The field_of_study
     */
    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    /**
     * @return The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "AcademicDegreesAttribute{" +
                "name='" + name + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
