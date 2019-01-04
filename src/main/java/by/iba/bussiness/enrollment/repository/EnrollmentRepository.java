package by.iba.bussiness.enrollment.repository;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentType;
import org.bson.types.ObjectId;

import java.math.BigInteger;
import java.util.List;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    Enrollment getByEmailAndParentId(ObjectId parentId, String userEmail);

    Enrollment getByEmailAndParentIdAndType(ObjectId parentId, String userEmail, EnrollmentType enrollmentType);

    List<Enrollment> getAllByParentId(ObjectId parentId);

    Enrollment getOneByParentId(ObjectId parentId);
}
