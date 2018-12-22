package by.iba.bussiness.enrollment.repository;

import by.iba.bussiness.enrollment.model.Enrollment;
import by.iba.bussiness.status.insert.InsertStatus;

import java.math.BigInteger;

public interface EnrollmentRepository {
    InsertStatus save(Enrollment enrollment);

    Enrollment getByEmailAndMeetingId(BigInteger parentId, String userEmail);
}
