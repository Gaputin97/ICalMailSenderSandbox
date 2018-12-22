package by.iba.bussiness.meeting.invitation.template.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:endpoint.properties")
@ConfigurationProperties
public class InvitationTemplateConstants {

    private String templateById;
    private String templateByCode;

    public String getTemplateById() {
        return templateById;
    }

    public void setTemplateById(String templateById) {
        this.templateById = templateById;
    }

    public String getTemplateByCode() {
        return templateByCode;
    }

    public void setTemplateByCode(String templateByCode) {
        this.templateByCode = templateByCode;
    }

}
