package by.iba.bussines.enrollment.dao;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.status.InsertStatus;

public interface EnrollmentRepository {
    InsertStatus save(Enrollment enrollment);
    Enrollment getByEmailAbdMeetingId(String meetingId, String email);
}
