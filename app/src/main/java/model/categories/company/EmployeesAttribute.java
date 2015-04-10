
package model.categories.company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class EmployeesAttribute {
    @Expose
    @SerializedName("first_name")
    private String firstName;
    @Expose
    @SerializedName("last_name")
    private String lastName;
    @Expose
    private String email;
    @Expose
    @SerializedName("is_admin")
    private String isAdmin;
    @Expose
    @SerializedName("is_mobile_signup")
    private String isMobileSignup;
    @Expose
    @SerializedName("primary_contact_number")
    private String primaryContactNumber;
    @Expose
    @SerializedName("display_name")
    private String displayName;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsMobileSignup() {
        return isMobileSignup;
    }

    public void setIsMobileSignup(String isMobileSignup) {
        this.isMobileSignup = isMobileSignup;
    }

    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "EmployeesAttribute{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                ", isMobileSignup='" + isMobileSignup + '\'' +
                ", primaryContactNumber='" + primaryContactNumber + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
