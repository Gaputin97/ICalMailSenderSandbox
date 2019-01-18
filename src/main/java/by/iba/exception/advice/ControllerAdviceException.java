package by.iba.exception.advice;

import by.iba.exception.CalendarException;
import by.iba.exception.RepositoryException;
import by.iba.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ControllerAdviceHelper> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(new ControllerAdviceHelper(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<ControllerAdviceHelper> handleRepositoryException(RepositoryException ex) {
        return new ResponseEntity<>(new ControllerAdviceHelper(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CalendarException.class)
    public ResponseEntity<ControllerAdviceHelper> handleCalendarException(CalendarException ex) {
        return new ResponseEntity<>(new ControllerAdviceHelper(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


}


