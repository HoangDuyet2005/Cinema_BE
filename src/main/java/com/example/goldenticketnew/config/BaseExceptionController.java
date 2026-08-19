package com.example.goldenticketnew.config;


import com.example.goldenticketnew.dtos.FieldRequestError;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.payload.response.ResponseBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class BaseExceptionController {
    private static final Logger log = LoggerFactory.getLogger(BaseExceptionController.class);

    public BaseExceptionController() {
    }



    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("", e);
        return new ResponseEntity<>(new ResponseBase<>(1, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleArgumentInvalidException(MethodArgumentNotValidException e) {
        List<FieldRequestError.Error> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new FieldRequestError.Error(fieldName, errorMessage));
        });
        log.error("Invalid argument: {}", errors);
        return new ResponseEntity<>(new ResponseBase(
                new FieldRequestError(errors),
                ResponseCode.INVALID_PARAM.getCode(),
                ResponseCode.INVALID_PARAM.getMessage()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({PropertyReferenceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBase handlePropertyReferenceException(PropertyReferenceException e) {
        return new ResponseBase(
                new FieldRequestError.Error(e.getPropertyName(), String.format("Field %s is not accepted", e.getPropertyName())),
                ResponseCode.INVALID_PARAM.getCode(),
                ResponseCode.INVALID_PARAM.getMessage()
        );
    }
}
