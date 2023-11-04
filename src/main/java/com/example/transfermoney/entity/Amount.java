package com.example.transfermoney.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Amount {
    private int value;
    private String currency;
}
