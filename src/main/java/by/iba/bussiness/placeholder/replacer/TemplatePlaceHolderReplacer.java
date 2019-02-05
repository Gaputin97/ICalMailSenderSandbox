package by.iba.bussiness.placeholder.replacer;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.body.InvitationTemplateBodyPart;
import by.iba.bussiness.invitation_template.body.InvitationTemplateBodyPartDefiner;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemplatePlaceHolderReplacer {
    private FieldPlaceHolderReplacer fieldPlaceHolderReplacer;
    private InvitationTemplateBodyPartDefiner invitationTemplateBodyPartDefiner;

    @Autowired
    public TemplatePlaceHolderReplacer(FieldPlaceHolderReplacer fieldPlaceHolderReplacer,
                                       InvitationTemplateBodyPartDefiner invitationTemplateBodyPartDefiner) {
        this.fieldPlaceHolderReplacer = fieldPlaceHolderReplacer;
        this.invitationTemplateBodyPartDefiner = invitationTemplateBodyPartDefiner;
    }

    public InvitationTemplate replaceTemplatePlaceHolders(Map<String, String> placeHolders, InvitationTemplate invitationTemplate, MeetingLocationType meetingLocationType) {
        String notModifiedSubject = invitationTemplate.getSubject();
        String notModifiedFrom = invitationTemplate.getFrom();
        String notModifiedFromName = invitationTemplate.getFromName();

        InvitationTemplateBodyPart invitationTemplateBodyPart = invitationTemplateBodyPartDefiner.defineBodyPart(invitationTemplate, meetingLocationType);
        String notModifiedLocation = invitationTemplateBodyPart.getLocation();
        String notModifiedDescription = invitationTemplateBodyPart.getDescription();
        String notModifiedPlainDescription = invitationTemplateBodyPart.getPlainDescription();

        String modifiedFrom = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedFrom);
        String modifiedFromName = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedFromName);
        String modifiedSubject = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedSubject);
        String modifiedLocation = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedLocation);
        String modifiedDescription = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedDescription);
        String modifiedPlainDescription = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedPlainDescription);

        InvitationTemplate modifiedInvitationTemplate = new InvitationTemplate(invitationTemplate);
        modifiedInvitationTemplate.setFrom(modifiedFrom);
        modifiedInvitationTemplate.setFromName(modifiedFromName);
        modifiedInvitationTemplate.setSubject(modifiedSubject);

        switch (meetingLocationType) {
            case ILT:
                modifiedInvitationTemplate.setLocationILT(modifiedLocation);
                modifiedInvitationTemplate.setFaceToFaceDescription(modifiedDescription);
                modifiedInvitationTemplate.setFaceToFacePlainDescription(modifiedPlainDescription);
                break;
            case LVC:
                modifiedInvitationTemplate.setLocationLVC(modifiedLocation);
                modifiedInvitationTemplate.setOnlineDescription(modifiedDescription);
                modifiedInvitationTemplate.setOnlinePlainDescription(modifiedPlainDescription);
            case CON:
                modifiedInvitationTemplate.setLocationBLD(modifiedLocation);
                modifiedInvitationTemplate.setBlendedDescription(modifiedDescription);
                modifiedInvitationTemplate.setBlendedPlainDescription(modifiedPlainDescription);
                break;
        }
        return modifiedInvitationTemplate;
    }
}
