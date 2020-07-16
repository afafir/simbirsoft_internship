package ru.simbirsoft.warehouse_management.controller.advice;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.simbirsoft.warehouse_management.exception.ApiError;
import ru.simbirsoft.warehouse_management.exception.NotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class ExceptionAdvice {

  @ApiOperation("Handling exception when entity cannot be found")
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleException(NotFoundException e) {
    ApiError apiError = new ApiError(BAD_REQUEST);
    apiError.setMessage("Unknown error");
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
