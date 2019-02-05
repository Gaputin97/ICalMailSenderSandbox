package by.iba.bussiness.invitation_template.body;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import org.springframework.stereotype.Component;

@Component
public class InvitationTemplateBodyPartDefiner {

    public InvitationTemplateBodyPart defineBodyPart(InvitationTemplate invitationTemplate, MeetingLocationType meetingLocationType) {
        String location = null;
        String description = null;
        String plainDescription = null;
        switch (meetingLocationType) {
            case ILT:
                location = invitationTemplate.getLocationILT();
                description = invitationTemplate.getFaceToFaceDescription();
                plainDescription = invitationTemplate.getFaceToFacePlainDescription();
                break;
            case CON:
                location = invitationTemplate.getLocationBLD();
                description = invitationTemplate.getBlendedDescription();
                plainDescription = invitationTemplate.getBlendedPlainDescription();
                break;
            case LVC:
                location = invitationTemplate.getLocationLVC();
                description = invitationTemplate.getOnlineDescription();
                plainDescription = invitationTemplate.getOnlinePlainDescription();
        }
        return new InvitationTemplateBodyPart(location, description, plainDescription);
    }
}
