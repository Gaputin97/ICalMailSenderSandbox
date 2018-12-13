package by.iba.bussines.token.constants;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:endpoint.properties")
public class TokenConstants {
    private String tokenEndpoint;

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    @Override
    public String toString() {
        return "TokenConstants{" +
                "tokenEndpoint='" + tokenEndpoint + '\'' +
                '}';
    }
}
