package by.iba.bussiness.enrollment.status;

import by.iba.bussiness.enrollment.Enrollment;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

public class EnrollmentStatusCheckerTest {
    private EnrollmentStatusChecker enrollmentStatusChecker = new EnrollmentStatusChecker();

    @Test
    public void test() {
        Stream<String> stream =
                Stream.of("a", "b", "c").filter(element -> element.contains("b"));
        Optional<String> anyElement = stream.findAny();
    }
}