
package model.categories.lead_capture;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class LeadCaptureDetails {
    @Expose
    @SerializedName("client_name")
    private String clientName;
    @Expose
    private String address;
    @Expose
    @SerializedName("client_type")
    private String clientType;
    @Expose
    @SerializedName("interaction_type")
    private String interactionType;
    @Expose
    @SerializedName("contact_no")
    private String contactNo;
    @Expose
    private String email;
    @Expose
    private String notes;
    @Expose
    @SerializedName("follow_up")
    private String followUp;

    /**
     * @return The clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName The client_name
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The clientType
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * @param clientType The client_type
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * @return The interactionType
     */
    public String getInteractionType() {
        return interactionType;
    }

    /**
     * @param interactionType The interaction_type
     */
    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    /**
     * @return The contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * @param contactNo The contact_no
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
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
     * @return The notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes The notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return The followUp
     */
    public String getFollowUp() {
        return followUp;
    }

    /**
     * @param followUp The follow_up
     */
    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    @Override
    public String toString() {
        return "LeadCapture{" +
                "clientName='" + clientName + '\'' +
                ", address='" + address + '\'' +
                ", clientType='" + clientType + '\'' +
                ", interactionType='" + interactionType + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", email='" + email + '\'' +
                ", notes='" + notes + '\'' +
                ", followUp='" + followUp + '\'' +
                '}';
    }
}
