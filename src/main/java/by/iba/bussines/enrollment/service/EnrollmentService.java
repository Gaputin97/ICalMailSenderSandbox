package by.iba.bussines.enrollment.service;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.status.InsertStatus;

import javax.servlet.http.HttpServletRequest;

public interface EnrollmentService {
    Enrollment getEnrollmentByEmailAndMeetingId(HttpServletRequest request, String parentId, String email);
    Enrollment getLocalEnrollmentByEmailAndMeetingId(String parentId, String email);
    void saveEnrollment(Enrollment enrollment);
    Enrollment getEnrollmentByEmailAndMeeting(HttpServletRequest request, String parentId, String email);

    Enrollment getLocalEnrollmentByEmailAndMeeting(String parentId, String email);

    InsertStatus saveEnrollment(Enrollment enrollment);
}
