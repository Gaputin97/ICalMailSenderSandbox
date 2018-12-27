package by.iba.bussiness.meeting.repository;

import by.iba.bussiness.meeting.Meeting;

import java.math.BigInteger;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    Meeting getById(BigInteger id);
}
