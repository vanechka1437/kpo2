package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.List;
import java.util.UUID;

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
            List<OperationDTO> operations = mapper.readValue(new File(filePath), listType);

            for (OperationDTO dto : operations) {
                dto.setAccountId(UUID.fromString(dto.getAccountId().toString()));
                dto.setCategoryId(UUID.fromString(dto.getCategoryId().toString()));
            }

            return operations;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }
}
