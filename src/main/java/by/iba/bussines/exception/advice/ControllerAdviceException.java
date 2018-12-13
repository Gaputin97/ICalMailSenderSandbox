package by.iba.bussines.exception.advice;

import by.iba.bussines.exception.ServiceException;
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

}


