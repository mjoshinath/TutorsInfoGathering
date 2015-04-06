
package model.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class WorkExperiencesAttribute {
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @Expose
    private String location;
    @Expose
    private String description;

    /**
     * @return The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName The company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
     * @return The jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle The job_title
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WorkExperiencesAttribute{" +
                "companyName='" + companyName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", endDate='" + endDate + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
