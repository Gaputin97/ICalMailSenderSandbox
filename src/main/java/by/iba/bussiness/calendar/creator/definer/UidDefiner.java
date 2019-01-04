package by.iba.bussiness.calendar.creator.definer;

import by.iba.bussiness.enrollment.Enrollment;
import net.fortuna.ical4j.model.property.Uid;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UidDefiner {
    public Uid defineUid(Enrollment enrollment) {
        Uid UID;
        if (enrollment != null) {
            UID = new Uid(enrollment.getCurrentCalendarUid());
        } else {
            UID = new Uid(UUID.randomUUID().toString());
        }
        return UID;
    }
}
