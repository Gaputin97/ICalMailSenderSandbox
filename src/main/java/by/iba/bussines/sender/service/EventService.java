package by.iba.bussines.sender.service;

public interface EventService {
    void sentEventViaRequestMethod();
    void sentEventViaPublishMethod();
    void sentEventViaCancelMethod();
}
