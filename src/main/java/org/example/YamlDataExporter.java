package org.example;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class YamlDataExporter implements DataExporter {
    private final FinanceFacade facade;

    public YamlDataExporter(FinanceFacade facade) {
        this.facade = facade;
    }

    @Override
    public void exportData(String filePath) {
        List<Operation> operations = facade.getOperations();

        try {
            YAMLMapper mapper = new YAMLMapper();
            mapper.writeValue(new File(filePath), operations);
            System.out.println("Data exported successfully to " + filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error exporting YAML file", e);
        }
    }
}
