package by.iba.bussiness.placeholder;

import by.iba.bussiness.meeting.Meeting;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlaceHoldersInstaller {

    public Map<String, String> installPlaceHoldersMap(Meeting meeting) {
        Map<String, String> placeHolders = new HashMap<>();
        placeHolders.put(PlaceHoldersConstants.CODE, meeting.getInvitationTemplate());
        placeHolders.put(PlaceHoldersConstants.CONTACT_EMAIL, meeting.getContact().getEmail());
        placeHolders.put(PlaceHoldersConstants.CONTACT_NAME, meeting.getContact().getName());
        placeHolders.put(PlaceHoldersConstants.DESCRIPTION, meeting.getDescription());
        placeHolders.put(PlaceHoldersConstants.LOCATION, meeting.getLocation());
        placeHolders.put(PlaceHoldersConstants.LOCATION_INFO, meeting.getLocationInfo());
        placeHolders.put(PlaceHoldersConstants.OWNER_EMAIL, meeting.getOwner().getEmail());
        placeHolders.put(PlaceHoldersConstants.OWNER_NAME, meeting.getOwner().getName());
        placeHolders.put(PlaceHoldersConstants.TITLE, meeting.getTitle());
        placeHolders.put(PlaceHoldersConstants.SUMMARY, meeting.getSummary());
        return placeHolders;

    }
}
