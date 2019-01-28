package by.iba.bussiness.invitation_template;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invitationTemplate")
public class InvitationTemplate {
    @Id
    private String id;
    private int index;
    private String key;
    private String from;
    private String fromName;
    private String blendedDescription;
    private String faceToFaceDescription;
    private String onlineDescription;
    private String locationBLD;
    private String locationILT;
    private String locationLVC;
    private String subject;

    public InvitationTemplate(InvitationTemplate another) {
        this.id = another.id;
        this.index = another.index;
        this.key = another.key;
        this.from = another.from;
        this.fromName = another.fromName;
        this.blendedDescription = another.blendedDescription;
        this.faceToFaceDescription = another.faceToFaceDescription;
        this.onlineDescription = another.onlineDescription;
        this.locationBLD = another.locationBLD;
        this.locationILT = another.locationILT;
        this.locationLVC = another.locationLVC;
        this.subject = another.subject;
    }

    public InvitationTemplate() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getBlendedDescription() {
        return blendedDescription;
    }

    public void setBlendedDescription(String blendedDescription) {
        this.blendedDescription = blendedDescription;
    }

    public String getFaceToFaceDescription() {
        return faceToFaceDescription;
    }

    public void setFaceToFaceDescription(String faceToFaceDescription) {
        this.faceToFaceDescription = faceToFaceDescription;
    }

    public String getOnlineDescription() {
        return onlineDescription;
    }

    public void setOnlineDescription(String onlineDescription) {
        this.onlineDescription = onlineDescription;
    }

    public String getLocationBLD() {
        return locationBLD;
    }

    public void setLocationBLD(String locationBLD) {
        this.locationBLD = locationBLD;
    }

    public String getLocationILT() {
        return locationILT;
    }

    public void setLocationILT(String locationILT) {
        this.locationILT = locationILT;
    }

    public String getLocationLVC() {
        return locationLVC;
    }

    public void setLocationLVC(String locationLVC) {
        this.locationLVC = locationLVC;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
