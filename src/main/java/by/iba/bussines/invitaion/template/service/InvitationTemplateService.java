package by.iba.bussines.invitaion.template.service;

import by.iba.bussines.invitaion.template.model.InvitationTemplate;

import javax.servlet.http.HttpServletRequest;

public interface InvitationTemplateService {
    InvitationTemplate getInvitationTemplateById(HttpServletRequest request, String id);

    InvitationTemplate getInvitationTemplateByCode(HttpServletRequest request, String code);

    InvitationTemplate getInvitationTemplateByMeetingId(HttpServletRequest request, String MeetingId);
}
