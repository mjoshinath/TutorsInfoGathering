package model.categories;

/**
 * Created by ehc on 2/4/15.
 */
public class LeadCaptureDetailsDBModel {
    private long id;
    private String details;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "LeadCaptureDetails{" +
                "id=" + id +
                ", details='" + details + '\'' +
                '}';
    }
}
