package ru.simbirsoft.warehouse_management.controller.advice;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.simbirsoft.warehouse_management.exception.ApiError;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionsAdvice {

  @ApiOperation("Handling unknown exception")
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleException(NotFoundException e) {
    ApiError apiError = new ApiError(BAD_REQUEST);
    apiError.setMessage("Unknown error");
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }

  @ApiOperation("Handling exception when entity cannot be found")
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
    ApiError apiError = new ApiError(NOT_FOUND);
    apiError.setMessage(e.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }


  @ApiOperation("Handling exception when validation failed")
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", new Date());
    // Get all errors
    List<String> errors =
            ex.getBindingResult().getFieldErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.toList());
    body.put("errors", errors);
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}
