package by.iba.bussiness.placeholder.replacer;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemplatePlaceHolderReplacer {
    private final FieldPlaceHolderReplacer fieldPlaceHolderReplacer;

    @Autowired
    public TemplatePlaceHolderReplacer(FieldPlaceHolderReplacer fieldPlaceHolderReplacer) {
        this.fieldPlaceHolderReplacer = fieldPlaceHolderReplacer;
    }

    public InvitationTemplate replaceTemplatePlaceHolders(Map<String, String> placeHolders, InvitationTemplate invitationTemplate, MeetingLocationType meetingLocationType) {
        String notModifiedSubject = invitationTemplate.getSubject();
        String notModifiedFrom = invitationTemplate.getFrom();
        String notModifiedFromName = invitationTemplate.getFromName();
        String notModifiedLocation = null;
        String notModifiedDescription = null;
        switch (meetingLocationType) {
            case ILT:
                notModifiedLocation = invitationTemplate.getLocationILT();
                notModifiedDescription = invitationTemplate.getFaceToFaceDescription();
                break;
            case CON:
                notModifiedLocation = invitationTemplate.getLocationBLD();
                notModifiedDescription = invitationTemplate.getBlendedDescription();
                break;
            case LVC:
                notModifiedLocation = invitationTemplate.getLocationLVC();
                notModifiedDescription = invitationTemplate.getOnlineDescription();
        }
        String modifiedDescription = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedDescription);
        String modifiedSubject = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedSubject);
        String modifiedFrom = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedFrom);
        String modifiedFromName = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedFromName);
        String modifiedLocation = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedLocation);

        InvitationTemplate modifiedInvitationTemplate = new InvitationTemplate(invitationTemplate);
        modifiedInvitationTemplate.setSubject(modifiedSubject);
        modifiedInvitationTemplate.setFrom(modifiedFrom);
        modifiedInvitationTemplate.setFromName(modifiedFromName);
        modifiedInvitationTemplate.setLocationILT(modifiedLocation);
        modifiedInvitationTemplate.setFaceToFaceDescription(modifiedDescription);
        return modifiedInvitationTemplate;
    }
}
