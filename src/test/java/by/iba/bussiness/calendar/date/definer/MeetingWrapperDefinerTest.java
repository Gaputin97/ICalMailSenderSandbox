package by.iba.bussiness.calendar.date.definer;

import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.configuration.ApplicationConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@TestPropertySource(locations = "classpath:application.properties")
public class MeetingWrapperDefinerTest {

    @Autowired
    private DateHelperDefiner dateHelperDefiner;

    @Test
    public void defineMeetingWrapperTest() {
//        //given
//        Meeting meeting = new Meeting();
//        meeting.setTimeSlots(MeetingWrapperDefinerTestData.createTimeSlotsForSingleEvent());
//        //when
//        SingleMeetingWrapper singleMeetingWrapper = (SingleMeetingWrapper) dateHelperDefiner.definerDateHelper(meeting);
//        //then
//        MeetingType meetingType = singleMeetingWrapper.getMeetingType();
//        Assert.assertEquals(meetingType, MeetingWrapperDefinerTestData.MEETING_TYPE_FOR_SINGLE_EVENT);
//        Assert.assertNotNull(singleMeetingWrapper.getSession());

    }
}