package by.iba.bussines.enrollment.dao;

import by.iba.bussines.enrollment.model.Enrollment;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    Enrollment getByEmailAbdMeetingId(String meetingId, String email);
}
