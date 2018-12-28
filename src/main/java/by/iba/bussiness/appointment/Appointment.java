package by.iba.bussiness.appointment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "appointment")
public class Appointment {
    @Id
    private BigInteger id;
    private BigInteger meetingId;
    private byte[] icsFile;

}
