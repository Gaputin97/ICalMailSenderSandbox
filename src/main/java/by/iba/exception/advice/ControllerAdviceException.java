package by.iba.exception.advice;

import by.iba.exception.RepositoryException;
import by.iba.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException {

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<AdditionalException> handleServiceException(ServiceException ex) {
        return new ResponseEntity<>(new AdditionalException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RepositoryException.class)
    protected ResponseEntity<AdditionalException> handleDaoException(RepositoryException ex) {
        return new ResponseEntity<>(new AdditionalException(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}


