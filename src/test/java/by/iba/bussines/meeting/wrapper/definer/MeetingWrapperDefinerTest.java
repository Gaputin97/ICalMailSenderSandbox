package by.iba.bussines.meeting.wrapper.definer;


import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.meeting.wrapper.definer.MeetingWrapperDefiner;
import by.iba.bussiness.meeting.wrapper.model.single.SingleMeetingWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


public class MeetingWrapperDefinerTest {

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