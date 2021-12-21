package ch.uzh.softcon.four;

import ch.uzh.softcon.four.logic.Game;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;

class MainTest {

    @Test
    void main() {
        String input = "1\n0\n2\n1\nUnitTest1\n100\n0\n0\nleave";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        try {
            Field scnField = Game.class.getDeclaredField("scn");
            scnField.setAccessible(true);
            scnField.set(null, new Scanner(System.in));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }

        assertDoesNotThrow(() -> Main.main(null), "No one joins the table");
        assertDoesNotThrow(() -> Main.main(null), "One person joins the table and plays");
    }
}