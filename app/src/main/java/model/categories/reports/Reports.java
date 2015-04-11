
package model.categories.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reports {

    private String status;
    private List<Datum> data = new ArrayList<Datum>();

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Reports{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
