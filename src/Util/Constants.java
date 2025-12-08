package Util;

import java.util.Map;

public class Constants {
    public final static Map<String, Integer> shipSpecifications = Map.of("Carrier", 5, "Battleship", 4, "Destroyer", 3, "Submarine", 3, "Patrol Boat", 2);

    public static class Dimensions {
        public final static int CELL_SIZE = 36;
        public final static int GRID_SIZE = CELL_SIZE * 10 + 1;
        public final static int STATUS_AREA_HEIGHT = 80;
        public final static int FOOTER_AREA_HEIGHT = 35;
        public final static int WINDOW_WIDTH = GRID_SIZE + 10;
        public final static int WINDOW_HEIGHT = 2 * GRID_SIZE + STATUS_AREA_HEIGHT + 2 * FOOTER_AREA_HEIGHT;
        public final static int PEG_DIAMETER = 10;
    }
    
	private Constants(){}
}
