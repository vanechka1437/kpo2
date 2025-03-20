package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {
    private OperationType type;
    private UUID accountId;
    private double amount;
    private LocalDate date;
    private String description;
    private UUID categoryId;
}
