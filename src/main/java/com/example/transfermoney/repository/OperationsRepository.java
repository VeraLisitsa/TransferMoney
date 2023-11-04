package com.example.transfermoney.repository;

import com.example.transfermoney.entity.Operation;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@NoArgsConstructor
public class OperationsRepository {

    private final ConcurrentHashMap<String, Operation> operationsList = new ConcurrentHashMap<>();


    public List<Operation> allOperations() {
        List<Operation> list = operationsList.values().stream()
                .collect(Collectors.toList());
        return list;
    }

    public Optional<Operation> getById(String id) {
        Operation operation = operationsList.get(id);
        return Optional.ofNullable(operation);
    }

    public boolean addOperation(Operation operation) {
        operationsList.put(operation.getId(), operation);
        return true;
    }
}
