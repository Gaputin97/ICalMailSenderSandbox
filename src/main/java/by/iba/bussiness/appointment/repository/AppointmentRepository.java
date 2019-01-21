package by.iba.bussiness.appointment.repository;

import by.iba.bussiness.appointment.Appointment;

import java.math.BigInteger;


public interface AppointmentRepository {
    Appointment getByMeetingId(BigInteger code);

    Appointment save(Appointment appointment);
}
