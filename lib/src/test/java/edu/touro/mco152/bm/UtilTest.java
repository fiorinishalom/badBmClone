package edu.touro.mco152.bm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UtilTest {
    int counter = 0;
    List<Integer> numList = new ArrayList<>(1);

    /**
     * normal test for random integer values
     * @param max
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 1000})
    void randInt(int max) {
        int randNum = Util.randInt(1, max);
        numList.add(randNum);
        assertEquals(1, numList.size());


    }

    /**
     * Boundary Test ensuring that if min and max values are the same that randInt doesn't throw an error.
     */
    @Test
    void boundaryRandInt(){
        assertDoesNotThrow(() -> {
            Util.randInt(5, 5);
        });
    }


    /**
     * Forcing Error test when min is higher than max value.
     */
    @Test
    void getErrorFromRandInt() {

        assertThrows(IllegalArgumentException.class, () -> {
            Util.randInt(10, 5);
        });
    }


}