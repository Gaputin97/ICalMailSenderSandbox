package by.iba.bussiness.placeholder;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class PlaceHolderReplacer {

    public InvitationTemplate replacePlaceHolders(Map<String, String> placeHolders, InvitationTemplate invTemplateWithoutPlaceHolders) {
        String notModifiedSubject = invTemplateWithoutPlaceHolders.getSubject();
        String notModifiedFrom = invTemplateWithoutPlaceHolders.getFrom();
        String notModifiedFromName = invTemplateWithoutPlaceHolders.getFromName();
        String notModifiedLocation = invTemplateWithoutPlaceHolders.getLocationILT();
        String notModifiedDescription = invTemplateWithoutPlaceHolders.getFaceToFaceDescription();

        String modifiedSubject = replaceFieldPlaceholders(placeHolders, notModifiedSubject);
        String modifiedFrom = replaceFieldPlaceholders(placeHolders, notModifiedFrom);
        String modifiedFromName = replaceFieldPlaceholders(placeHolders, notModifiedFromName);
        String modifiedLocation = replaceFieldPlaceholders(placeHolders, notModifiedLocation);
        String modifiedDescription = replaceFieldPlaceholders(placeHolders, notModifiedDescription);

        InvitationTemplate modifiedInvitationTemplate = new InvitationTemplate();
        modifiedInvitationTemplate.setSubject(modifiedSubject);
        modifiedInvitationTemplate.setFrom(modifiedFrom);
        modifiedInvitationTemplate.setFromName(modifiedFromName);
        modifiedInvitationTemplate.setLocationILT(modifiedLocation);
        modifiedInvitationTemplate.setFaceToFaceDescription(modifiedDescription);
        return modifiedInvitationTemplate;

    }


    public String replaceFieldPlaceholders(Map<String, String> placeHolders, String field) {
        AtomicReference<String> atomicReference = new AtomicReference<>();
        placeHolders.entrySet().forEach(map -> {
            String placeHolder = map.getKey();
            String placeHolderValue = map.getValue();
            atomicReference.set(field.replaceAll(placeHolder, placeHolderValue));
        });
        return atomicReference.get();

    }
}
