
package model.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Tutor {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @Expose
    private String email;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @Expose
    private String description;
    @Expose
    private String languages;
    @Expose
    private String dob;
    @Expose
    private String gender;
    @SerializedName("primary_contact_number")
    @Expose
    private String primaryContactNumber;
    @Expose
    private Address address;
    @SerializedName("work_experiences")
    @Expose
    private WorkExperiences workExperiences;

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName The display_name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    /**
     * @return The languages
     */
    public String getLanguages() {
        return languages;
    }

    /**
     * @param languages The languages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     * @return The dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob The dob
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return The primaryContactNumber
     */
    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }

    /**
     * @param primaryContactNumber The primary_contact_number
     */
    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }

    /**
     * @return The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    public WorkExperiences getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(WorkExperiences workExperiences) {
        this.workExperiences = workExperiences;
    }

    @Override
    public String toString() {
        return "Tutor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", description='" + description + '\'' +
                ", languages='" + languages + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", primaryContactNumber='" + primaryContactNumber + '\'' +
                ", address=" + address +
                ", workExperiences=" + workExperiences +
                '}';
    }
}
