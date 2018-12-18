package by.iba.bussines.enrollment.service;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.status.insert.InsertStatus;
import javax.servlet.http.HttpServletRequest;

public interface EnrollmentService {
    Enrollment getEnrollmentByEmailAndMeetingId(HttpServletRequest request, String parentId, String email);
    Enrollment getLocalEnrollmentByEmailAndMeetingId(String parentId, String email);
    InsertStatus saveEnrollment(Enrollment enrollment);
}
