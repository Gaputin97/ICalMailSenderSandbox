package by.iba.bussines.meeting.wrapper.definer;

import by.iba.bussines.calendar.factory.type.MeetingType;
import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.wrapper.model.single.SingleMeetingWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;

public class MeetingWrapperDefinerTest {

    @Autowired
    private MeetingWrapperDefiner meetingWrapperDefiner;

    @Test
    public void defineMeetingWrapperTest() {
        //given
        Meeting meeting = new Meeting();
        meeting.setTimeSlots(Data.createTimeSlotsForSingleEvent());
        //when
        SingleMeetingWrapper singleMeetingWrapper = meetingWrapperDefiner.defineMeetingWrapper(meeting);
        //then
        MeetingType meetingType = singleMeetingWrapper.getMeetingType();
        Assert.assertEquals(meetingType, Data.MEETING_TYPE_FOR_SINGLE_EVENT);
        Assert.assertNotNull(singleMeetingWrapper.getSession());


    }
}