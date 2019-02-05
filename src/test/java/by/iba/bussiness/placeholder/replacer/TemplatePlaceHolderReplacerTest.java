package by.iba.bussiness.placeholder.replacer;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemplatePlaceHolderReplacerTest {

    @Mock
    private FieldPlaceHolderReplacer fieldPlaceHolderReplacer;
    @InjectMocks
    private TemplatePlaceHolderReplacer templatePlaceHolderReplacer;

    @Test
    public void replacePlaceHolders() {
        String replaced = "Replaced";
        String subject = "Subject";
        String from = "From";
        String fromName = "FromName";
        String location = "Location";
        String ftf = "FTF Description";
        InvitationTemplate invitationTemplate = new InvitationTemplate();
        invitationTemplate.setSubject(subject);
        invitationTemplate.setFrom(from);
        invitationTemplate.setFromName(fromName);
        invitationTemplate.setLocationILT(location);
        invitationTemplate.setFaceToFaceDescription(ftf);
        //when
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, subject)).thenReturn(replaced + subject);
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, from)).thenReturn(replaced + from);
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, fromName)).thenReturn(replaced + fromName);
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, location)).thenReturn(replaced + location);
        when(fieldPlaceHolderReplacer.replaceFieldPlaceHolders(null, ftf)).thenReturn(replaced + ftf);
        InvitationTemplate modifiedInvitationTemplate = templatePlaceHolderReplacer.replaceTemplatePlaceHolders(null, invitationTemplate, );
        //then
        Assert.assertEquals(modifiedInvitationTemplate.getSubject(), replaced + subject);
        Assert.assertEquals(modifiedInvitationTemplate.getFrom(), replaced + from);
        Assert.assertEquals(modifiedInvitationTemplate.getFrom(), replaced + from);
        Assert.assertEquals(modifiedInvitationTemplate.getFromName(), replaced + fromName);
        Assert.assertEquals(modifiedInvitationTemplate.getLocationILT(), replaced + location);
        Assert.assertEquals(modifiedInvitationTemplate.getFaceToFaceDescription(), replaced + ftf);
    }
}
