
package model.categories.company;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Company {
    @Expose
    private String name;
    @Expose
    private String website;
    @Expose
    private String subdomain;
    @Expose
    @SerializedName("established_on")
    private String establishedOn;
    @Expose
    private String logo;
    @Expose
    private String banner;
    @Expose
    private String description;
    @Expose
    private String city;
    @Expose
    private String state;
    @Expose
    @SerializedName("street_1")
    private String street1;
    @Expose
    private String country;
    @Expose
    @SerializedName("zip_code")
    private String zipCode;
    @Expose
    @SerializedName("employees_attributes")
    private List<EmployeesAttribute> employeesAttributes = new ArrayList<EmployeesAttribute>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getEstablishedOn() {
        return establishedOn;
    }

    public void setEstablishedOn(String establishedOn) {
        this.establishedOn = establishedOn;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<EmployeesAttribute> getEmployeesAttributes() {
        return employeesAttributes;
    }

    public void setEmployeesAttributes(List<EmployeesAttribute> employeesAttributes) {
        this.employeesAttributes = employeesAttributes;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", subdomain='" + subdomain + '\'' +
                ", establishedOn='" + establishedOn + '\'' +
                ", logo='" + logo + '\'' +
                ", banner='" + banner + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", street1='" + street1 + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", employeesAttributes=" + employeesAttributes +
                '}';
    }
}
