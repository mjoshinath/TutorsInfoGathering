
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
    @SerializedName("employees_attributes")
    private List<EmployeesAttribute> employeesAttributes = new ArrayList<EmployeesAttribute>();

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
     * @return The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return The subdomain
     */
    public String getSubdomain() {
        return subdomain;
    }

    /**
     * @param subdomain The subdomain
     */
    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    /**
     * @return The establishedOn
     */
    public String getEstablishedOn() {
        return establishedOn;
    }

    /**
     * @param establishedOn The established_on
     */
    public void setEstablishedOn(String establishedOn) {
        this.establishedOn = establishedOn;
    }

    /**
     * @return The logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo The logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return The banner
     */
    public String getBanner() {
        return banner;
    }

    /**
     * @param banner The banner
     */
    public void setBanner(String banner) {
        this.banner = banner;
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
     * @return The employeesAttributes
     */
    public List<EmployeesAttribute> getEmployeesAttributes() {
        return employeesAttributes;
    }

    /**
     * @param employeesAttributes The employees_attributes
     */
    public void setEmployeesAttributes(List<EmployeesAttribute> employeesAttributes) {
        this.employeesAttributes = employeesAttributes;
    }

}
