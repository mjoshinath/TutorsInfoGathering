
package model.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkExperiences {

    @SerializedName("company_name")
    @Expose
    private String companyName;

    /**
     * 
     * @return
     *     The companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 
     * @param companyName
     *     The company_name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "WorkExperiences{" +
                "companyName='" + companyName + '\'' +
                '}';
    }
}
