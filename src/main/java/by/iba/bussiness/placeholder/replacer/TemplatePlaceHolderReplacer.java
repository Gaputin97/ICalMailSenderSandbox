package by.iba.bussiness.placeholder.replacer;

import by.iba.bussiness.invitation_template.InvitationTemplate;
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

    public InvitationTemplate replaceTemplatePlaceHolders(Map<String, String> placeHolders, InvitationTemplate invTemplateWithPlaceHolders) {
        String notModifiedSubject = invTemplateWithPlaceHolders.getSubject();
        String notModifiedFrom = invTemplateWithPlaceHolders.getFrom();
        String notModifiedFromName = invTemplateWithPlaceHolders.getFromName();
        String notModifiedLocation = invTemplateWithPlaceHolders.getLocationILT();
        String notModifiedDescription = invTemplateWithPlaceHolders.getFaceToFaceDescription();

        String modifiedDescription = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedDescription);
        String modifiedSubject = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedSubject);
        String modifiedFrom = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedFrom);
        String modifiedFromName = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedFromName);
        String modifiedLocation = fieldPlaceHolderReplacer.replaceFieldPlaceHolders(placeHolders, notModifiedLocation);

        InvitationTemplate modifiedInvitationTemplate = new InvitationTemplate(invTemplateWithPlaceHolders);
        modifiedInvitationTemplate.setSubject(modifiedSubject);
        modifiedInvitationTemplate.setFrom(modifiedFrom);
        modifiedInvitationTemplate.setFromName(modifiedFromName);
        modifiedInvitationTemplate.setLocationILT(modifiedLocation);
        modifiedInvitationTemplate.setFaceToFaceDescription(modifiedDescription);
        return modifiedInvitationTemplate;
    }
}
