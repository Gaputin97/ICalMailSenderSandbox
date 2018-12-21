package by.iba.bussiness.chain.service.v1;

import by.iba.bussiness.chain.model.Chain;
import by.iba.bussiness.chain.service.EventService;
import by.iba.bussiness.event.model.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Override
    public List<Chain> getAllChains() {
        return null;
    }

    @Override
    public Chain getChainById() {
        return null;
    }

    @Override
    public List<Event> getEventsByChainId(String id) {
        return null;
    }
}
