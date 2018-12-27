package by.iba.bussiness.enrollment.service;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.status.insert.InsertStatus;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

public interface EnrollmentService {

    Enrollment getEnrollmentByEmailAndMeetingId(HttpServletRequest request, BigInteger parentId, String email);

    Enrollment getLocalEnrollmentByEmailAndMeetingId(BigInteger parentId, String email);

    InsertStatus saveEnrollment(Enrollment enrollment);
}
