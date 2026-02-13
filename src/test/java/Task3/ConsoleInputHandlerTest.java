package Task3;

import org.example.Task3.ConsoleInputHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleInputHandlerTest {
    @Test
    public void testHandle() {
        String simulatedInput = "first search\nsecond search\n/exit\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                simulatedInput.getBytes());
        System.setIn(inputStream);

        List<String> received = new ArrayList<>();
        Consumer<String> consumer = (line) -> received.add(line);

        ConsoleInputHandler handler = new ConsoleInputHandler();
        handler.start(consumer);

        assertEquals(2, received.size());
        assertEquals("first search", received.get(0));
        assertEquals("second search", received.get(1));
    }
}
