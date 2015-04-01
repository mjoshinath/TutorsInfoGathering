
package model.categories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Category {

    private Integer id;
    private String name;
    private List<SubCategory> subCategories = new ArrayList<SubCategory>();

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * @return The subCategories
     */
    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    /**
     * @param subCategories The sub_categories
     */
    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subCategories=" + subCategories +
                '}';
    }
}
