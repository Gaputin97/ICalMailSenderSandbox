package by.iba.bussiness.appointment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;

@Document(collection = "appointment")
public class Appointment {
    @Id
    private BigInteger id;
    @Field
    private BigInteger meetingId;
    @Field
    private byte[] icsFile;

}
