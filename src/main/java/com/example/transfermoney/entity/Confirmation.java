package com.example.transfermoney.entity;

import java.util.Objects;

public class Confirmation {
    private String operationId;
    private String code;

    public Confirmation(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Confirmation that = (Confirmation) o;
        return Objects.equals(operationId, that.operationId) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, code);
    }

    @Override
    public String toString() {
        return "Confirmation{" +
                "operationId='" + operationId + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
