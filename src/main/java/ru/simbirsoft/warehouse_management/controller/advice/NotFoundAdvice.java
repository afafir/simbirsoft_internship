package ru.simbirsoft.warehouse_management.controller.advice;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.simbirsoft.warehouse_management.exception.ApiError;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class NotFoundAdvice extends ResponseEntityExceptionHandler {

  @ApiOperation("Handling exception when entity cannot be found")
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
    ApiError apiError = new ApiError(NOT_FOUND);
    apiError.setMessage(e.getMessage());
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
