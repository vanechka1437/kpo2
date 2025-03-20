package org.example;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CsvDataImporter extends DataImporter {
    private static final Logger logger = LoggerFactory.getLogger(CsvDataImporter.class);

    public CsvDataImporter(FinanceFacade facade) {
        super(facade);
    }

    @Override
    protected List<OperationDTO> parseFile(String filePath) {
        List<OperationDTO> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 6) {
                    continue;
                }

                try {
                    OperationDTO dto = new OperationDTO();
                    dto.setType(OperationType.valueOf(parts[0].trim()));
                    dto.setAccountId(UUID.fromString(parts[1].trim()));
                    dto.setAmount(Double.parseDouble(parts[2].trim()));
                    dto.setDate(LocalDate.parse(parts[3].trim()));
                    dto.setDescription(parts[4].trim());
                    dto.setCategoryId(UUID.fromString(parts[5].trim()));

                    result.add(dto);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при парсинге CSV-файла", e);
        }
        return result;
    }

}
