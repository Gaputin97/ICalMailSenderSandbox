package by.iba.bussiness.invitation_template.body;

public class InvitationTemplateBodyPart {

    private String location;
    private String description;
    private String plainDescription;

    public InvitationTemplateBodyPart(String location, String description, String plainDescription) {
        this.location = location;
        this.description = description;
        this.plainDescription = plainDescription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlainDescription() {
        return plainDescription;
    }

    public void setPlainDescription(String plainDescription) {
        this.plainDescription = plainDescription;
    }
}
