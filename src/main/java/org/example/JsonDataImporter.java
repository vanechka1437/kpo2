package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;

@Component
public class JsonDataImporter extends DataImporter {
    public JsonDataImporter(FinanceFacade facade) {
        super(facade);
    }

    @Override
    protected List<OperationDTO> parseFile(String filePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, OperationDTO.class);
            return mapper.readValue(new File(filePath), listType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
