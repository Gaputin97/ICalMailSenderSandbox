package by.iba.bussiness.placeholder;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlaceHolderReplacer {

    public InvitationTemplate replacePlaceHolders(Map<String, String> placeHolders, InvitationTemplate invTemplateWithPlaceHolders) {
        String notModifiedSubject = invTemplateWithPlaceHolders.getSubject();
        String notModifiedFrom = invTemplateWithPlaceHolders.getFrom();
        String notModifiedFromName = invTemplateWithPlaceHolders.getFromName();
        String notModifiedLocation = invTemplateWithPlaceHolders.getLocationILT();
        String notModifiedDescription = invTemplateWithPlaceHolders.getFaceToFaceDescription();

        String modifiedDescription = replaceFieldPlaceHolders(placeHolders, notModifiedDescription);
        String modifiedSubject = replaceFieldPlaceHolders(placeHolders, notModifiedSubject);
        String modifiedFrom = replaceFieldPlaceHolders(placeHolders, notModifiedFrom);
        String modifiedFromName = replaceFieldPlaceHolders(placeHolders, notModifiedFromName);
        String modifiedLocation = replaceFieldPlaceHolders(placeHolders, notModifiedLocation);


        InvitationTemplate modifiedInvitationTemplate = new InvitationTemplate(invTemplateWithPlaceHolders);
        modifiedInvitationTemplate.setSubject(modifiedSubject);
        modifiedInvitationTemplate.setFrom(modifiedFrom);
        modifiedInvitationTemplate.setFromName(modifiedFromName);
        modifiedInvitationTemplate.setLocationILT(modifiedLocation);
        modifiedInvitationTemplate.setFaceToFaceDescription(modifiedDescription);
        return modifiedInvitationTemplate;

    }

    public String replaceFieldPlaceHolders(Map<String, String> placeHolders, String field) {
        String modifiedField = field;
        for (Map.Entry<String, String> map : placeHolders.entrySet()) {
            String placeHolder = map.getKey();
            String placeHolderValue = map.getValue();
            modifiedField = modifiedField.replace(placeHolder, placeHolderValue);
        }
        return modifiedField;

    }
}
