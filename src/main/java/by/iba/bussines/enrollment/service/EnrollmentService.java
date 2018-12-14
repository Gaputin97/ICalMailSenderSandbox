package by.iba.bussines.enrollment.service;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.status.InsertStatus;

import javax.servlet.http.HttpServletRequest;

public interface EnrollmentService {
    Enrollment getEnrollmentByEmailAndMeeting(HttpServletRequest request, String parentId, String email);

    Enrollment getLocalEnrollmentByEmailAndMeeting(String parentId, String email);

    InsertStatus saveEnrollment(Enrollment enrollment);
}
