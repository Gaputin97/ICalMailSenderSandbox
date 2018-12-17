package by.iba.bussines.enrollment.repository;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.status.InsertStatus;

public interface EnrollmentRepository {

    Enrollment getByEmailAndMeetingId(String parentId, String email);

    InsertStatus save(Enrollment enrollment);
}
