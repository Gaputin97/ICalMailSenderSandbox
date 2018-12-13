package by.iba.bussines.token.model;

public class JavaWebToken {
    private String jwt;

    public JavaWebToken() {
    }

    public JavaWebToken(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "JavaWebToken{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}
