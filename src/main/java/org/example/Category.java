package org.example;
import lombok.Getter;

@Getter
public class Category {
    private final int id;
    private final OperationType type;
    private final String name;

    public Category(int id, OperationType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

}
