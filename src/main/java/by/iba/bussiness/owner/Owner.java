package by.iba.bussiness.owner;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "owner")
public class Owner {
    @Id
    private long id;
    private String email;
    private String name;

    public Owner() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(email, owner.email) &&
                Objects.equals(name, owner.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, id);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

