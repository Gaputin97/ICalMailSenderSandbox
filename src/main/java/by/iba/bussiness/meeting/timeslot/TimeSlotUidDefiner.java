package by.iba.bussiness.meeting.timeslot;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotUidDefiner {

    public String defineTimeSlotUid(String meetingId, String timeSlotId) {
        byte[] encodedBytes = (meetingId + ":" + timeSlotId).getBytes();
        String uid = Base64.encodeBase64String(encodedBytes);
        return uid;
    }
}
