package by.iba.bussines.meeting.wrapper.definer;


import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.type.MeetingType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(basePackage)
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