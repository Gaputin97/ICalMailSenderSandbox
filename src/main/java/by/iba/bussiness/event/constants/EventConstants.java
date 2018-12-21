package by.iba.bussines.event.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:endpoint.properties")
@ConfigurationProperties
public class EventConstants {
    private String allEventsEndpoint;
    private String eventsByChainIdEndpoint;
    private String eventsByIdEndpoint;

    public String getAllEventsEndpoint() {
        return allEventsEndpoint;
    }

    public void setAllEventsEndpoint(String allEventsEndpoint) {
        this.allEventsEndpoint = allEventsEndpoint;
    }

    public String getEventsByChainIdEndpoint(String chainId) {
        return eventsByChainIdEndpoint + chainId;
    }

    public void setEventsByChainIdEndpoint(String eventsByChainIdEndpoint) {
        this.eventsByChainIdEndpoint = eventsByChainIdEndpoint;
    }

    public String getEventsByIdEndpoint(String id) {
        return eventsByIdEndpoint + id;
    }

    public void setEventsByIdEndpoint(String eventsByIdEndpoint) {
        this.eventsByIdEndpoint = eventsByIdEndpoint;
    }
}