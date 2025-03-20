package org.example;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public abstract class DataImporter {
    protected final FinanceFacade facade;

    public DataImporter(FinanceFacade facade) {
        this.facade = facade;
    }

    protected abstract List<OperationDTO> parseFile(String filePath);
}