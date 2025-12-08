package Model;

public enum ShotResult {
    HIT,
    MISS,
    SUNK,
    INVALID;

    private Coordinate location;
    private String shipName;
    private int length;

    public Coordinate getLocation() {
        return location;
    }
    public void setLocation(Coordinate location) {
        this.location = location;
    }
    public String getShipName() {
        return shipName;
    }
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
}
