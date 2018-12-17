package by.iba.bussines.sender.algorithm.parser;


import by.iba.bussines.sender.algorithm.entity.Event;
import by.iba.bussines.sender.algorithm.entity.UnparsedEvent;

import java.util.ArrayList;
import java.util.List;

public class EventParser {

    public static List<Event> unparsedToParsed(List<UnparsedEvent> unparsedEvents){
        List<Event> events = new ArrayList<>();
        unparsedEvents.forEach(x->events.add(new Event(DateParser.stringToDate(x.getStartDate()), DateParser.stringToDate(x.getEndDate()))));
        return events;
    }
}
