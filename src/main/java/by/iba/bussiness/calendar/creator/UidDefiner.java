package by.iba.bussiness.calendar.creator;

import net.fortuna.ical4j.model.property.Uid;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UidDefiner {
    public Uid defineUid(String calendarUid) {
        Uid UID;
        if (calendarUid.isEmpty()) {
            UID = new Uid(UUID.randomUUID().toString());
        } else {
            UID = new Uid(calendarUid);
        }
        return UID;
    }
}
