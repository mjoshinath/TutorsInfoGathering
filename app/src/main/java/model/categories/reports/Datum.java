
package model.categories.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Datum {
    @Expose
    private Integer id;
    @Expose
    @SerializedName("created_at")
    private String createdAt;
    @Expose
    @SerializedName("updated_at")
    private String updatedAt;
    @Expose
    @SerializedName("staff_id")
    private Integer staffId;
    @Expose
    @SerializedName("target_person_id")
    private Integer targetPersonId;
    @Expose
    private Integer year;
    @Expose
    private String month;
    @Expose
    private Integer target;
    @Expose
    @SerializedName("target_type")
    private String targetType;
    @Expose
    private Integer achieved;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getTargetPersonId() {
        return targetPersonId;
    }

    public void setTargetPersonId(Integer targetPersonId) {
        this.targetPersonId = targetPersonId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getAchieved() {
        return achieved;
    }

    public void setAchieved(Integer achieved) {
        this.achieved = achieved;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", staffId=" + staffId +
                ", targetPersonId=" + targetPersonId +
                ", year=" + year +
                ", month='" + month + '\'' +
                ", target=" + target +
                ", targetType='" + targetType + '\'' +
                ", achieved=" + achieved +
                '}';
    }
}
