package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.handler.IndexDeterminer;
import by.iba.bussiness.enrollment.EnrollmentUpdateChecker;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class EnrollmentUpdateCheckerTest {

    @Mock
    private IndexDeterminer indexDeterminer;
    @InjectMocks
    private EnrollmentUpdateChecker calendarCreator;

}