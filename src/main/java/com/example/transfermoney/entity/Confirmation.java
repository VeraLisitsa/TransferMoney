package com.example.transfermoney.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Confirmation {
    private String operationId;
    private String code;

}
