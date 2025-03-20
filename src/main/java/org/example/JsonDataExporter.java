package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonDataExporter implements DataExporter {
    private final FinanceFacade facade;

    public JsonDataExporter(FinanceFacade facade) {
        this.facade = facade;
    }

    @Override
    public void exportData(String filePath) {
        List<Operation> operations = facade.getOperations();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(filePath), operations);
            System.out.println("Data exported successfully to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error exporting JSON file", e);
        }
    }
}
