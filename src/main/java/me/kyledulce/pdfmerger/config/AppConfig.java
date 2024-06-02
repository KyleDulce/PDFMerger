package me.kyledulce.pdfmerger.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import me.kyledulce.pdfmerger.PDFMerger;

import java.io.IOException;
import java.util.NoSuchElementException;

public class AppConfig {
    private final JsonNode rootNode;
    private final ObjectMapper mapper;

    private AppConfig(String configFileLocation) throws IOException {
        // Read file
        mapper = new ObjectMapper(new YAMLFactory());
        this.rootNode = mapper.readTree(PDFMerger.class
                .getResource(configFileLocation));
    }

    public boolean doesPathExist(String path) {
        JsonNode resolvedNode = resolvePathToNode(path);

        return !resolvedNode.isMissingNode();
    }

    public Object get(String path, Class<?> javaClass) {
        JsonNode resolvedNode = resolvePathToNode(path);

        try {
            return mapper.treeToValue(resolvedNode, javaClass);
        } catch (JsonProcessingException e) {
            throw new ClassCastException("Cannot map to class");
        }
    }

    public Object get(ConfigPath path, Class<?> javaClass) {
        return get(path.toString(), javaClass);
    }

    public String getString(String path) {
        JsonNode resolvedNode = resolvePathToNode(path);
        return resolvedNode.asText();
    }

    public String getString(ConfigPath path) {
        return getString(path.toString());
    }

    public int getInt(String path) {
        JsonNode resolvedNode = resolvePathToNode(path);
        if(!resolvedNode.isValueNode()) {
            throw new ClassCastException("Cannot map non value node to int");
        }
        return resolvedNode.asInt();
    }

    public int getInt(ConfigPath path) {
        return getInt(path.toString());
    }

    public long getLong(String path) {
        JsonNode resolvedNode = resolvePathToNode(path);
        if(!resolvedNode.isValueNode()) {
            throw new ClassCastException("Cannot map non value node to long");
        }
        return resolvedNode.asLong();
    }

    public long getLong(ConfigPath path) {
        return getLong(path.toString());
    }

    public double getDouble(String path) {
        JsonNode resolvedNode = resolvePathToNode(path);
        if(!resolvedNode.isValueNode()) {
            throw new ClassCastException("Cannot map non value node to double");
        }
        return resolvedNode.asDouble();
    }

    public double getDouble(ConfigPath path) {
        return getDouble(path.toString());
    }

    public boolean getBoolean(String path) {
        JsonNode resolvedNode = resolvePathToNode(path);
        if(!resolvedNode.isValueNode()) {
            throw new ClassCastException("Cannot map non value node to boolean");
        }
        return resolvedNode.asBoolean();
    }

    public boolean getBoolean(ConfigPath path) {
        return getBoolean(path.toString());
    }

    private JsonNode resolvePathToNode(String path) {

        String[] resolvedPath = path.split("\\.");
        JsonNode currentNode = rootNode;
        for(String pathPart : resolvedPath) {
            currentNode = currentNode.path(pathPart);
        }

        return currentNode;
    }

    public static AppConfig instantiateConfiguration(String configFileLocation) throws IOException {
        return new AppConfig(configFileLocation);
    }
}
