package ch.uzh.softcon.one.utils;

import ch.uzh.softcon.one.abstraction.Player;
import ch.uzh.softcon.one.turn.Turn;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class MoveStorageTest {

    @Test
    void push() {
        Turn expectedRed = new Turn(0, 0, 1, 1);
        Turn lastWhite = new Turn(0, 0, 1, 1);
        for (int i = 0; i < 62; i += 2) {
            MoveStorage.push(new Turn(0, 0, 1, 1), Player.RED, null);
            MoveStorage.push(new Turn(0, 0, 1, 1), Player.WHITE, null);
        }
        MoveStorage.push(new Turn(0, 0, 1, 1), Player.RED, null);
        MoveStorage.push(expectedRed, Player.RED, null);
        MoveStorage.push(lastWhite, Player.WHITE, null);
        Turn key = null;
        try {
            Field movesHashMap = MoveStorage.class.getDeclaredField("moves");
            movesHashMap.setAccessible(true);
            //noinspection unchecked
            HashMap<Turn, Player>[] extractedMap = (HashMap<Turn, Player>[]) movesHashMap.get(null);
            Optional<Turn> optionalTurn = extractedMap[62].keySet().stream().findFirst();
            if (optionalTurn.isPresent())
                key = optionalTurn.get();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail();
        }
        assert key != null;
        assertEquals(expectedRed, key);
    }
}