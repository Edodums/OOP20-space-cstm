package main.services;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import main.exceptions.DirectoryNotCreated;
import main.models.ObservableModel;
import main.utils.enums.FileType;
import main.utils.enums.ResourcePath;

import java.io.*;

public class YamlService {
    private final ObjectMapper objectMapper;

    public YamlService(){
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    public void writeFile(String filename, ObservableModel model) {
        try {
            this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
            this.objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
            this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            this.objectMapper.writeValue(getFile(filename), model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableModel readFile(String filename, Class<? extends ObservableModel> modelClass) {
        try {
            this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            this.objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
            this.objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
            
            return this.objectMapper.readValue(getFile(filename), modelClass);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private File getFile(String filename){
        String completeFilename = filename.concat(".").concat(FileType.YAML.toString());
        String yamlPath = ResourcePath.DATA_PATH.toString();

        if (JarService.runningFromJar())  {
            String defaultYamlPath = yamlPath;
            yamlPath = ResourcePath.EXTERNAL_DATA_PATH.toString();

            try {
                FileService.createDirectoryIfNotExist(yamlPath);
                FileService.copyFile(defaultYamlPath.concat(completeFilename), yamlPath.concat(completeFilename));
            } catch (DirectoryNotCreated | IOException directoryNotCreated) {
                directoryNotCreated.printStackTrace();
            }
        }

        return new File(yamlPath.concat(completeFilename));
    }
}
