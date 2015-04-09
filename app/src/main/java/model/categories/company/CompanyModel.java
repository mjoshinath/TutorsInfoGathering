
package model.categories.company;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

public class CompanyModel {
    @Expose
    private Company company;

    /**
     * @return The company
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @param company The company
     */
    public void setCompany(Company company) {
        this.company = company;
    }

}
