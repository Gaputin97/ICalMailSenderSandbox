package by.iba.bussiness.invitaion.template.service;

import by.iba.bussiness.invitaion.template.model.InvitationTemplate;

import javax.servlet.http.HttpServletRequest;

public interface InvitationTemplateService {
    InvitationTemplate getInvitationTemplateById(HttpServletRequest request, String id);

    InvitationTemplate getInvitationTemplateByCode(HttpServletRequest request, String code);

    InvitationTemplate getInvitationTemplateByMeetingId(HttpServletRequest request, String MeetingId);
}
