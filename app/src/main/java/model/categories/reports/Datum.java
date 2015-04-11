
package model.categories.reports;

import java.util.HashMap;
import java.util.Map;

public class Datum {

    private Integer id;
    private String createdAt;
    private String updatedAt;
    private Integer staffId;
    private Integer targetPersonId;
    private Integer year;
    private String month;
    private Integer target;
    private String targetType;
    private Integer achieved;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The staffId
     */
    public Integer getStaffId() {
        return staffId;
    }

    /**
     * 
     * @param staffId
     *     The staff_id
     */
    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    /**
     * 
     * @return
     *     The targetPersonId
     */
    public Integer getTargetPersonId() {
        return targetPersonId;
    }

    /**
     * 
     * @param targetPersonId
     *     The target_person_id
     */
    public void setTargetPersonId(Integer targetPersonId) {
        this.targetPersonId = targetPersonId;
    }

    /**
     * 
     * @return
     *     The year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * 
     * @param year
     *     The year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * 
     * @return
     *     The month
     */
    public String getMonth() {
        return month;
    }

    /**
     * 
     * @param month
     *     The month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 
     * @return
     *     The target
     */
    public Integer getTarget() {
        return target;
    }

    /**
     * 
     * @param target
     *     The target
     */
    public void setTarget(Integer target) {
        this.target = target;
    }

    /**
     * 
     * @return
     *     The targetType
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * 
     * @param targetType
     *     The target_type
     */
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    /**
     * 
     * @return
     *     The achieved
     */
    public Integer getAchieved() {
        return achieved;
    }

    /**
     * 
     * @param achieved
     *     The achieved
     */
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
