package by.iba.bussiness.owner;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.math.BigInteger;

@Document(collection = "owner")
public class Owner {
    private String email;
    private String name;
    @Id
    private BigInteger id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

