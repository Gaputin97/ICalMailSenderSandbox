package by.iba.exception.advice;

import by.iba.exception.RepositoryException;
import by.iba.exception.SendingException;
import by.iba.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<AdditionalException> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(new AdditionalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<AdditionalException> handleRepositoryException(RepositoryException ex) {
        return new ResponseEntity<>(new AdditionalException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SendingException.class)
    public ResponseEntity<AdditionalException> handleCalendarSendingException(SendingException ex) {
        return new ResponseEntity<>(new AdditionalException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}


