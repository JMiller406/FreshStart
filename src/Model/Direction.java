package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public enum Direction {
    NORTH(1), 
    SOUTH(2), 
    EAST(3), 
    WEST(4);

    private int value;
    private static Map<Integer, Direction> map = new HashMap<>();

    private Direction(int value){
        this.value = value;
    }

    static {
        for(Direction dir : Direction.values()){
            map.put(dir.value, dir);
        }
    }

    public static Direction equivalentTo(int value){
        return map.get(value);
    }

    public int getValue() {
        return this.value;
    }

    private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Direction randomDirection()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}