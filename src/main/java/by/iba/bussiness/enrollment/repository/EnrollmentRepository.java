package by.iba.bussiness.enrollment.repository;

import by.iba.bussiness.enrollment.Enrollment;

import java.math.BigInteger;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);

    Enrollment getByEmailAndMeetingId(BigInteger parentId, String userEmail);
}
