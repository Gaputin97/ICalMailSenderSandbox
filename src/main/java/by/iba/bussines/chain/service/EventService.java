package by.iba.bussines.chain.service;

import by.iba.bussines.chain.model.Chain;
import by.iba.bussines.event.model.Event;

import java.util.List;

public interface EventService {
    List<Chain> getAllChains();

    Chain getChainById();

    List<Event> getEventsByChainId(String id);
}
