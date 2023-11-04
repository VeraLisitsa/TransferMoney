package com.example.transfermoney.util;

import com.example.transfermoney.dto.ErrorDto;
import com.example.transfermoney.dto.OperationDto;
import com.example.transfermoney.entity.ErrorEntity;
import com.example.transfermoney.entity.Operation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class MappingDto {
    private final ModelMapper mapper;

    public OperationDto operationToDto(Operation operation) {
        return (Objects.isNull(operation) ? null : mapper.map(operation, OperationDto.class));
    }

    public ErrorDto errorEntityToErrorDto(ErrorEntity errorEntity) {
        return (Objects.isNull(errorEntity) ? null : mapper.map(errorEntity, ErrorDto.class));
    }

}

