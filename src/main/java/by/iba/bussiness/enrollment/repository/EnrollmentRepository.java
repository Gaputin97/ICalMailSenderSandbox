package by.iba.bussiness.enrollment.repository;

import by.iba.bussiness.enrollment.Enrollment;

import java.math.BigInteger;
import java.util.List;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    Enrollment getByEmailAndParentId(BigInteger parentId, String userEmail);
    Enrollment getByEmailAndParentIdAndType(String parentId, String userEmail, String enrollmentStatus);
    List<Enrollment> getAllByParentId(String parentId);
}
