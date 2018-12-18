package by.iba.exception.advice;

import by.iba.exception.CalendarSendingException;
import by.iba.exception.RepositoryException;
import by.iba.exception.ServiceException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(ServiceException.class)
    protected HttpEntity<AdditionalException> handleServiceException(ServiceException ex) {
        return new HttpEntity<>(new AdditionalException(ex.getMessage()));
    }

    @ExceptionHandler(RepositoryException.class)
    protected HttpEntity<AdditionalException> handleRepositoryException(RepositoryException ex) {
        return new HttpEntity<>(new AdditionalException(ex.getMessage()));
    }

    @ExceptionHandler(CalendarSendingException.class)
    protected HttpEntity<AdditionalException> handleCalendarSengingException(CalendarSendingException ex) {
        return new HttpEntity<>(new AdditionalException(ex.getMessage()));
    }

}


