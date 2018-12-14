package by.iba.bussines.enrollment.repository;

import by.iba.bussines.enrollment.model.Enrollment;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    Enrollment getByEmailAndMeetingId(String parentId, String email);
}
