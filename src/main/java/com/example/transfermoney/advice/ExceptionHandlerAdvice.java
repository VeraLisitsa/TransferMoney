package com.example.transfermoney.advice;

import com.example.transfermoney.dto.ErrorDto;
import com.example.transfermoney.entity.ErrorEntity;
import com.example.transfermoney.exception.ErrorConfirmationException;
import com.example.transfermoney.exception.ErrorConfirmationInputDataException;
import com.example.transfermoney.exception.ErrorInputDataException;
import com.example.transfermoney.exception.ErrorTransferException;
import com.example.transfermoney.util.Logger;
import com.example.transfermoney.util.MappingDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionHandlerAdvice {

    private final MappingDto mappingDto;

    private final Logger logger;


    @ExceptionHandler
    public ResponseEntity<ErrorDto> errorInputDataExceptionHandler(ErrorInputDataException e) {
        return new ResponseEntity<>(createErrorDto(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> errorTransferExceptionHandler(ErrorTransferException e) {
        return new ResponseEntity<>(createErrorDto(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> errorConfirmationExceptionHandler(ErrorConfirmationException e) {
        return new ResponseEntity<>(createErrorDto(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> errorConfirmationInputDataExceptionHandler(ErrorConfirmationInputDataException e) {
        return new ResponseEntity<>(createErrorDto(e), HttpStatus.BAD_REQUEST);
    }

    protected ErrorDto createErrorDto(RuntimeException e) {
        ErrorEntity errorEntity = new ErrorEntity(e.getMessage());
        logger.writeToLoggerFile(" Error id = " + errorEntity.getId() + ", " + errorEntity.getMessage());
        return mappingDto.errorEntityToErrorDto(errorEntity);
    }
}
