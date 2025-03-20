package org.example;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class YamlDataImporter extends DataImporter {
    public YamlDataImporter(FinanceFacade facade) {
        super(facade);
    }

    @Override
    protected List<OperationDTO> parseFile(String filePath) {
        try (FileInputStream input = new FileInputStream(filePath)) {
            LoaderOptions options = new LoaderOptions();
            Constructor constructor = new Constructor(OperationDTO.class, options);
            Yaml yaml = new Yaml(constructor);
            Iterable<Object> objects = yaml.loadAll(input);

            List<OperationDTO> list = new ArrayList<>();
            for (Object obj : objects) {
                list.add((OperationDTO) obj);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse YAML", e);
        }
    }
}
