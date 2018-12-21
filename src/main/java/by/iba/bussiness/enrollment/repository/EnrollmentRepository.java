package by.iba.bussiness.enrollment.repository;

import by.iba.bussiness.enrollment.model.Enrollment;
import by.iba.bussiness.status.insert.InsertStatus;

public interface EnrollmentRepository {
    Enrollment getByEmailAndMeetingId(String parentId, String email);
    InsertStatus save(Enrollment enrollment);
}
