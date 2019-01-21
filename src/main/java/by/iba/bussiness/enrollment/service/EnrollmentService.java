package by.iba.bussiness.enrollment.service;

import by.iba.bussiness.enrollment.Enrollment;

import java.math.BigInteger;
import java.util.List;

public interface EnrollmentService {
    Enrollment save(Enrollment enrollment);

    Enrollment getByEmailAndParentId(BigInteger parentId, String userEmail);

    Enrollment getByEmailAndParentIdAndType(BigInteger parentId, String userEmail, String enrollmentStatus);

    List<Enrollment> getAllByParentId(BigInteger parentId);
}
