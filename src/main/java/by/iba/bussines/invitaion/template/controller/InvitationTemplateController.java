package by.iba.bussines.invitaion.template.controller;

import by.iba.bussines.invitaion.template.model.InvitationTemplate;
import by.iba.bussines.invitaion.template.service.v1.InvitationTemplateServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class InvitationTemplateController {

    private InvitationTemplateServiceImpl invitationTemplateService;

    @Autowired
    public InvitationTemplateController(InvitationTemplateServiceImpl invitationTemplateService) {
        this.invitationTemplateService = invitationTemplateService;
    }

    @ApiOperation(value = "Get invitation template by id from template service", response = InvitationTemplate.class)
    @RequestMapping(value = "/template/getById", method = RequestMethod.POST)
    @ResponseBody
    public InvitationTemplate getInvitationTemplateById(@RequestParam(value = "id") String id, HttpServletRequest request) {
        return invitationTemplateService.getInvitationTemplateById(request, id);
    }

    @ApiOperation(value = "Get invitation template by code from template service", response = InvitationTemplate.class)
    @RequestMapping(value = "/template/getByCode", method = RequestMethod.POST)
    @ResponseBody
    public InvitationTemplate getInvitationTemplateByCode(@RequestParam(value = "code") String code, HttpServletRequest request) {
        return invitationTemplateService.getInvitationTemplateByCode(request, code);
    }

    @ApiOperation(value = "Get invitation template by meeting id from template service", response = InvitationTemplate.class)
    @RequestMapping(value = "/template/getByMeetingId", method = RequestMethod.POST)
    @ResponseBody
    public InvitationTemplate getInvitationTemplateByMeetingId(@RequestParam(value = "meetingId") String meetingId, HttpServletRequest request) {
        return invitationTemplateService.getInvitationTemplateByMeetingId(request, meetingId);
    }

}
