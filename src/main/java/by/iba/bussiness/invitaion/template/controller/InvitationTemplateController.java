package by.iba.bussiness.invitaion.template.controller;

import by.iba.bussiness.invitaion.template.model.InvitationTemplate;
import by.iba.bussiness.invitaion.template.service.v1.InvitationTemplateServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class InvitationTemplateController {
    @Autowired
    private InvitationTemplateServiceImpl invitationTemplateService;

    @ApiOperation(value = "Get invitation template by id from template service", response = InvitationTemplate.class)
    @RequestMapping(value = "/template/get/{id}", method = RequestMethod.GET)
    public InvitationTemplate getInvitationTemplateById(@PathVariable(value = "id") String id, HttpServletRequest request) {
        return invitationTemplateService.getInvitationTemplateById(request, id);
    }

    @ApiOperation(value = "Get invitation template by code from template service", response = InvitationTemplate.class)
    @RequestMapping(value = "/template/code/get/{code}", method = RequestMethod.GET)
    public InvitationTemplate getInvitationTemplateByCode(@PathVariable(value = "code") String code, HttpServletRequest request) {
        return invitationTemplateService.getInvitationTemplateByCode(request, code);
    }

    @ApiOperation(value = "Get invitation template by meeting id from template service", response = InvitationTemplate.class)
    @RequestMapping(value = "/template/meeting/get/{meetingId}", method = RequestMethod.GET)
    public InvitationTemplate getInvitationTemplateByMeetingId(@PathVariable(value = "meetingId") String meetingId, HttpServletRequest request) {
        return invitationTemplateService.getInvitationTemplateByMeetingId(request, meetingId);
    }

}
