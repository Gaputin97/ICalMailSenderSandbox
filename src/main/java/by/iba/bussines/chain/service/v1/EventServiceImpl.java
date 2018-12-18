package by.iba.bussines.chain.service.v1;

import by.iba.bussines.chain.model.Chain;
import by.iba.bussines.chain.service.EventService;
import by.iba.bussines.sender.algorithm.entity.Event;
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
