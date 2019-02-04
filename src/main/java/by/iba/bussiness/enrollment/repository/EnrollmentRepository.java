package by.iba.bussiness.enrollment.repository;

import by.iba.bussiness.enrollment.Enrollment;

import java.math.BigInteger;
import java.util.List;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);

    Enrollment getByEmailAndParentId(String userEmail, BigInteger parentId);

    Enrollment getByEmailAndParentIdAndStatus(BigInteger parentId, String userEmail, String enrollmentStatus);

    List<Enrollment> getAllByParentId(BigInteger parentId);
}
