
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
    private String city;
    @Expose
    private String street;
    @Expose
    private String country;
    @Expose
    @SerializedName("zip_code")
    private String zipCode;

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
     * @return The isAdmin
     */
    public String getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin The is_admin
     */
    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return The isMobileSignup
     */
    public String getIsMobileSignup() {
        return isMobileSignup;
    }

    /**
     * @param isMobileSignup The is_mobile_signup
     */
    public void setIsMobileSignup(String isMobileSignup) {
        this.isMobileSignup = isMobileSignup;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street The street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode The zip_code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
