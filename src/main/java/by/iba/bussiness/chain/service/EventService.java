package by.iba.bussiness.chain.service;

import by.iba.bussiness.chain.Chain;
import by.iba.bussiness.event.Event;

import java.util.List;

public interface EventService {
    List<Chain> getAllChains();

    Chain getChainById();

    List<Event> getEventsByChainId(String id);
}
