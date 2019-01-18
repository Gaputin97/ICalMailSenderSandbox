package by.iba.bussiness.template;

import by.iba.bussiness.owner.Owner;

public class Template {

    private String summary;
    private Owner owner;
    private String description;
    private String location;
    private String timeSlots;
    private String type;

    public Template() {
    }

    public Template(Template template) {
        this.summary = template.getSummary();
        this.owner = template.getOwner();
        this.description = template.getDescription();
        this.timeSlots = template.getTimeSlots();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeSlots() {
        return timeSlots;
    }

    public void setSessions(String timeSlots) {
        this.timeSlots = timeSlots;
    }
}
