package by.iba.bussiness.enrollment.service;

import by.iba.bussiness.enrollment.model.Enrollment;
import by.iba.bussiness.status.insert.InsertStatus;
import javax.servlet.http.HttpServletRequest;

public interface EnrollmentService {
    Enrollment getEnrollmentByEmailAndMeetingId(HttpServletRequest request, String parentId, String email);
    Enrollment getLocalEnrollmentByEmailAndMeetingId(String parentId, String email);
    InsertStatus saveEnrollment(Enrollment enrollment);
}
