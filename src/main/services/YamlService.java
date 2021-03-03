package main.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import main.models.Model;

import java.io.File;
import java.io.IOException;

public class YamlService {
    private ObjectMapper objectMapper;

    public YamlService(){
        this.objectMapper = new ObjectMapper(new YAMLFactory());
        
    }

    private File getFile(String filename){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        return new File(classLoader.getResource(filename).getFile());
    }

    private void writeFile(String filename, Model model){
        try {
            objectMapper.writeValue(getFile(filename), model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Model readFile(String filename, Model model){
        try {
            return objectMapper.readValue(getFile(filename),model.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
