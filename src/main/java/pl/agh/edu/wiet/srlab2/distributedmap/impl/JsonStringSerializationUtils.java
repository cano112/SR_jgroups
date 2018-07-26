package pl.agh.edu.wiet.srlab2.distributedmap.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonStringSerializationUtils {
    private final ObjectMapper mapper;
    public JsonStringSerializationUtils() {
        this.mapper = new ObjectMapper();
    }

    public String serializeStringMap(Map<String, String> map) throws JsonProcessingException {
        return mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(map);
    }

    public Map<String, String> deserializeStringMap(String jsonMap) throws IOException {
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
        return mapper.readValue(jsonMap, typeRef);
    }

    public String serializeOperation(SimpleStringMapOperationDTO dto) throws JsonProcessingException {
        return mapper.writeValueAsString(dto);
    }

    public SimpleStringMapOperationDTO deserializeOperation(String json) throws IOException {
        return mapper.readValue(json, SimpleStringMapOperationDTO.class);
    }
}
