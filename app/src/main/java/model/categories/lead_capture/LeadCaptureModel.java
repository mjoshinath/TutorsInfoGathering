
package model.categories.lead_capture;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeadCaptureModel {
    @Expose
    @SerializedName("lead_capture")
    private LeadCaptureDetails leadCapture;

    /**
     * @return The leadCapture
     */
    public LeadCaptureDetails getLeadCapture() {
        return leadCapture;
    }

    /**
     * @param leadCapture The lead_capture
     */
    public void setLeadCapture(LeadCaptureDetails leadCapture) {
        this.leadCapture = leadCapture;
    }

    @Override
    public String toString() {
        return "LeadCaptureModel{" +
                "leadCapture=" + leadCapture +
                '}';
    }
}
