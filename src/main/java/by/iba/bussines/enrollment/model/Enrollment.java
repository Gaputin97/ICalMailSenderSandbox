package by.iba.bussines.enrollment.model;

public class Enrollment {

    private float id;
    private String parentId;
    private String userEmail;

    public float getId() {
        return id;
    }

    public String getParentId() {
        return parentId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
