package by.iba.bussiness.enrollment.service;

import by.iba.bussiness.enrollment.Enrollment;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

public interface EnrollmentService {
    Enrollment getEnrollmentByEmailAndParentId(HttpServletRequest request, BigInteger parentId, String email);
    List<Enrollment> getEnrollmentByParentId(HttpServletRequest request, BigInteger parentId);
}
