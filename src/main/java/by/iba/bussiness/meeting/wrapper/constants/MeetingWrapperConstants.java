package by.iba.bussiness.meeting.wrapper.constants;

import org.springframework.stereotype.Component;

@Component
public class MeetingWrapperConstants {
    private final int amountOfSessionsForSingleEvent = 1;
    private final int numberOfFirstTimeSlot = 0;

    public int getAmountOfSessionsForSingleEvent() {
        return amountOfSessionsForSingleEvent;
    }

    public int getNumberOfFirstTimeSlot() {
        return numberOfFirstTimeSlot;
    }
}