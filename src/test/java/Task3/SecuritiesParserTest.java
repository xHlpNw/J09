package Task3;

import org.example.Task3.SecuritiesParser;
import org.example.Task3.SecurityRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SecuritiesParserTest {

    private final SecuritiesParser parser = new SecuritiesParser();

    @Test
    public void testJsonParse() {
        String json = """
                {
                  "securities": {
                    "columns": ["secid", "shortname", "regnumber", "name", "emitent_title", "emitent_inn", "emitent_okpo", "is_traded"],
                    "data": [
                      ["SEC1", "Short1", "Reg1", "Name1", "Emitent1", "123", "456", 1],
                      ["SEC2", "Short2", "Reg2", "Name2", "Emitent2", "789", "012", 0],
                      ["SEC3", "Short3", "Reg3", "Name3", "Emitent3", "345", "678", 1]
                    ]
                  }
                }
                """;

        List<SecurityRecord> records = parser.parseAndFilter(json);

        assertEquals(2, records.size());
        assertEquals("SEC1", records.get(0).secid());
        assertEquals("SEC3", records.get(1).secid());
    }

    @Test
    void testParseAndFilter_invalidJson() {
        String invalidJson = "{ random structure json }";

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> parser.parseAndFilter(invalidJson));
        assertTrue(ex.getMessage().contains("Ошибка парсинга JSON"));
    }

}
