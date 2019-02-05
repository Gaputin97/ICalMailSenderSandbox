package by.iba.bussiness.placeholder.replacer;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.body.InvitationTemplateBodyPart;
import by.iba.bussiness.invitation_template.body.InvitationTemplateBodyPartDefiner;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemplatePlaceHolderReplacerTest {

    @Mock
    private FieldPlaceHolderReplacer fieldPlaceHolderReplacer;

    @Mock
    private InvitationTemplateBodyPartDefiner invitationTemplateBodyPartDefiner;

    @InjectMocks
    private TemplatePlaceHolderReplacer templatePlaceHolderReplacer;

    @Test
    public void testReplaceILTPlaceHolders() {
        InvitationTemplate invitationTemplate = new InvitationTemplate();
        invitationTemplate.setFrom("From");
        invitationTemplate.setFromName("FromName");
        invitationTemplate.setSubject("Subject");
        InvitationTemplateBodyPart invitationTemplateBodyPart = new InvitationTemplateBodyPart("ILT location", "ILT description", "ILT plain description");

        when(invitationTemplateBodyPartDefiner.defineBodyPart(invitationTemplate, MeetingLocationType.ILT)).thenReturn(invitationTemplateBodyPart);
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "From")).thenReturn("Replaced from");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "FromName")).thenReturn("Replaced fromName");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "Subject")).thenReturn("Replaced subject");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "ILT location")).thenReturn("Replaced ILT location");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "ILT description")).thenReturn("Replaced ILT description");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "ILT plain description")).thenReturn("Replaced ILT plain description");

        InvitationTemplate actualInvitationTemplate = templatePlaceHolderReplacer.replaceTemplatePlaceHolders(null, invitationTemplate, MeetingLocationType.ILT);

        assertEquals("Error caused with ILC form field", "Replaced from", actualInvitationTemplate.getFrom());
        assertEquals("Error caused with ILC form name field", "Replaced fromName", actualInvitationTemplate.getFromName());
        assertEquals("Error caused with ILC subject field", "Replaced subject", actualInvitationTemplate.getSubject());
        assertEquals("Error caused with ILC location field", "Replaced ILT location", actualInvitationTemplate.getLocationILT());
        assertEquals("Error caused with ILC description field", "Replaced ILT description", actualInvitationTemplate.getFaceToFaceDescription());
        assertEquals("Error caused with ILC plain description field", "Replaced ILT plain description", actualInvitationTemplate.getFaceToFacePlainDescription());
    }

    @Test
    public void testReplaceLVCPlaceHolders() {
        InvitationTemplate invitationTemplate = new InvitationTemplate();
        invitationTemplate.setFrom("From");
        invitationTemplate.setFromName("FromName");
        invitationTemplate.setSubject("Subject");
        InvitationTemplateBodyPart invitationTemplateBodyPart = new InvitationTemplateBodyPart("LVC location", "LVC description", "LVC plain description");

        when(invitationTemplateBodyPartDefiner.defineBodyPart(invitationTemplate, MeetingLocationType.LVC)).thenReturn(invitationTemplateBodyPart);
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "From")).thenReturn("Replaced from");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "FromName")).thenReturn("Replaced fromName");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "Subject")).thenReturn("Replaced subject");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "LVC location")).thenReturn("Replaced LVC location");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "LVC description")).thenReturn("Replaced LVC description");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "LVC plain description")).thenReturn("Replaced LVC plain description");

        InvitationTemplate actualInvitationTemplate = templatePlaceHolderReplacer.replaceTemplatePlaceHolders(null, invitationTemplate, MeetingLocationType.LVC);

        assertEquals("Error caused with LVC form field", "Replaced from", actualInvitationTemplate.getFrom());
        assertEquals("Error caused with LVC form name field", "Replaced fromName", actualInvitationTemplate.getFromName());
        assertEquals("Error caused with LVC subject field", "Replaced subject", actualInvitationTemplate.getSubject());
        assertEquals("Error caused with LVC location field", "Replaced LVC location", actualInvitationTemplate.getLocationLVC());
        assertEquals("Error caused with LVC description field", "Replaced LVC description", actualInvitationTemplate.getOnlineDescription());
        assertEquals("Error caused with LVC plain description field", "Replaced LVC plain description", actualInvitationTemplate.getOnlinePlainDescription());
    }

    @Test
    public void testReplaceCONPlaceHolders() {
        InvitationTemplate invitationTemplate = new InvitationTemplate();
        invitationTemplate.setFrom("From");
        invitationTemplate.setFromName("FromName");
        invitationTemplate.setSubject("Subject");
        InvitationTemplateBodyPart invitationTemplateBodyPart = new InvitationTemplateBodyPart("CON location", "CON description", "CON plain description");

        when(invitationTemplateBodyPartDefiner.defineBodyPart(invitationTemplate, MeetingLocationType.CON)).thenReturn(invitationTemplateBodyPart);
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "From")).thenReturn("Replaced from");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "FromName")).thenReturn("Replaced fromName");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "Subject")).thenReturn("Replaced subject");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "CON location")).thenReturn("Replaced CON location");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "CON description")).thenReturn("Replaced CON description");
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, "CON plain description")).thenReturn("Replaced CON plain description");

        InvitationTemplate actualInvitationTemplate = templatePlaceHolderReplacer.replaceTemplatePlaceHolders(null, invitationTemplate, MeetingLocationType.CON);

        assertEquals("Error caused with CON form field", "Replaced from", actualInvitationTemplate.getFrom());
        assertEquals("Error caused with CON form name field", "Replaced fromName", actualInvitationTemplate.getFromName());
        assertEquals("Error caused with CON subject field", "Replaced subject", actualInvitationTemplate.getSubject());
        assertEquals("Error caused with CON location field", "Replaced CON location", actualInvitationTemplate.getLocationBLD());
        assertEquals("Error caused with CON description field", "Replaced CON description", actualInvitationTemplate.getBlendedDescription());
        assertEquals("Error caused with CON plain description field", "Replaced CON plain description", actualInvitationTemplate.getBlendedPlainDescription());
    }
}
