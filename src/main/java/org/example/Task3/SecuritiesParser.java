package org.example.Task3;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecuritiesParser {
    private final ObjectMapper mapper;

    public SecuritiesParser() {
        this.mapper = new ObjectMapper();
    }

    public List<SecurityRecord> parseAndFilter(String json) {
        List<SecurityRecord> result = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode securities = root.path("securities");
            JsonNode columns = securities.path("columns");
            JsonNode data = securities.path("data");

            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < columns.size(); i++) {
                columnIndex.put(columns.get(i).asText(), i);
            }

            for (JsonNode row : data) {
                Integer isTradedIdx = columnIndex.get("is_traded");
                if (isTradedIdx != null
                        && row.get(isTradedIdx) != null
                        && row.get(isTradedIdx).asInt() == 1
                ) {
                    result.add(new SecurityRecord(
                            getValue(row, columnIndex, "secid"),
                            getValue(row, columnIndex, "shortname"),
                            getValue(row, columnIndex, "regnumber"),
                            getValue(row, columnIndex, "name"),
                            getValue(row, columnIndex, "emitent_title"),
                            getValue(row, columnIndex, "emitent_inn"),
                            getValue(row, columnIndex, "emitent_okpo")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка парсинга JSON", e);
        }

        return result;
    }

    private String getValue(JsonNode row, Map<String, Integer> indexMap,
                            String field) {
        Integer idx = indexMap.get(field);
        if (idx == null || row.get(idx).isNull()) {
            return "";
        }
        return row.get(idx).asText();
    }
}
