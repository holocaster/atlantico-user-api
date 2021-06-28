package br.gov.inst.atlan.userapi.rest.handlers;

import br.gov.inst.atlan.userapi.exceptions.UserNotAdminException;
import br.gov.inst.atlan.userapi.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException constraintViolationException) {
        List<String> errors = new ArrayList<>(constraintViolationException.getConstraintViolations().size());

        constraintViolationException.getConstraintViolations().forEach(e -> {
            errors.add(e.getPropertyPath() + " : " + e.getMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List> handleBindException(BindException e) {
        return new ResponseEntity(e.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserNotAdminException.class)
    public ResponseEntity<String> handleUserNotAdminException(UserNotAdminException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário logado não tem perfil de administrador");
    }

}
