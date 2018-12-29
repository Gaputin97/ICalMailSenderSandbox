package by.iba.bussiness.appointment.repository;

import by.iba.bussiness.appointment.Appointment;

public interface AppointmentRepository {
    Appointment getByCode(String code);
    Appointment save(Appointment appointment);
    Appointment update(Appointment appointment);
}
