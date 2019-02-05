package by.iba.bussiness.placeholder.installer;

import by.iba.bussiness.location.Location;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import by.iba.bussiness.placeholder.PlaceHoldersConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("link.properties")
public class PlaceHoldersInstaller {
    private String meetingLink;
    private String meetingJoinLink;
    private AgendaInstaller agendaInstaller;

    public PlaceHoldersInstaller(@Value("meeting_link") String meetingLink,
                                 @Value("meeting_join_link") String meetingJoinLink,
                                 AgendaInstaller agendaInstaller) {
        this.meetingLink = meetingLink;
        this.meetingJoinLink = meetingJoinLink;
        this.agendaInstaller = agendaInstaller;
    }

    public Map<String, String> installPlaceHoldersMap(Meeting meeting, Location location) {
        String type = meeting.getType();
        MeetingLocationType meetingLocationType = MeetingLocationType.valueOf(type);
        Map<String, String> placeHolders = null;

        switch (meetingLocationType) {
            case CON:
                placeHolders = installCONPlaceholdersMap(meeting, location);
                break;
            case LVC:
                placeHolders = installLVCPlaceHoldersMap(meeting);
                break;
            case ILT:
                placeHolders = installILTPlaceHoldersMap(meeting, location);
                break;
        }
        return placeHolders;
    }

    private Map<String, String> installILTPlaceHoldersMap(Meeting meeting, Location location) {
        Map<String, String> placeHolders = installCommonPlaceHoldersMap(meeting);
        placeHolders.put(PlaceHoldersConstants.LOCATION, location.toString());
        placeHolders.put(PlaceHoldersConstants.LOCATION_INFO, meeting.getLocationInfo());
        return placeHolders;
    }

    private Map<String, String> installLVCPlaceHoldersMap(Meeting meeting) {
        Map<String, String> placeHolders = installCommonPlaceHoldersMap(meeting);
        placeHolders.put(PlaceHoldersConstants.ACTIVITY_PASSCODE, meeting.getActivityPasscode());
        placeHolders.put(PlaceHoldersConstants.ACTIVITY_URL, meeting.getActivityUrl());
        placeHolders.put(PlaceHoldersConstants.JOIN, meetingJoinLink + meeting.getId());
        placeHolders.put(PlaceHoldersConstants.CALLIN_INFO, meeting.getActivityInfo());
        return placeHolders;
    }

    private Map<String, String> installCONPlaceholdersMap(Meeting meeting, Location location) {
        Map<String, String> placeHolders = installCommonPlaceHoldersMap(meeting);
        placeHolders.put(PlaceHoldersConstants.ACTIVITY_PASSCODE, meeting.getActivityPasscode());
        placeHolders.put(PlaceHoldersConstants.ACTIVITY_URL, meeting.getActivityUrl());
        placeHolders.put(PlaceHoldersConstants.JOIN, meetingJoinLink + meeting.getId());
        placeHolders.put(PlaceHoldersConstants.CALLIN_INFO, meeting.getActivityInfo());
        placeHolders.put(PlaceHoldersConstants.LOCATION, location.toString());
        placeHolders.put(PlaceHoldersConstants.LOCATION_INFO, meeting.getLocationInfo());
        return placeHolders;
    }

    private Map<String, String> installCommonPlaceHoldersMap(Meeting meeting) {
        Map<String, String> placeHolders = new HashMap<>();
        String agenda = agendaInstaller.installMeetingAgenda(meeting);
        placeHolders.put(PlaceHoldersConstants.PARENT_TITLE, "MOCK PARENT TITLE");
        placeHolders.put(PlaceHoldersConstants.PRICE, "MOCK PRICE");
        placeHolders.put(PlaceHoldersConstants.LINK, meetingLink + meeting.getId());
        placeHolders.put(PlaceHoldersConstants.AGENDA, agenda);
        placeHolders.put(PlaceHoldersConstants.CODE, meeting.getInvitationTemplate());
        placeHolders.put(PlaceHoldersConstants.CONTACT_EMAIL, meeting.getContact().getEmail());
        placeHolders.put(PlaceHoldersConstants.CONTACT_NAME, meeting.getContact().getName());
        placeHolders.put(PlaceHoldersConstants.DESCRIPTION, meeting.getDescription());
        placeHolders.put(PlaceHoldersConstants.OWNER_EMAIL, meeting.getOwner().getEmail());
        placeHolders.put(PlaceHoldersConstants.OWNER_NAME, meeting.getOwner().getName());
        placeHolders.put(PlaceHoldersConstants.TITLE, meeting.getTitle());
        placeHolders.put(PlaceHoldersConstants.SUMMARY, meeting.getSummary());
        return placeHolders;
    }
}
