package state;

public class Cell {

    private boolean alive;
    private int bornIteration;

    public Cell(boolean alive) {
        this.alive = alive;
        this.bornIteration = 0;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getBornIteration() {
        return bornIteration;
    }

    public void setBornIteration(int bornIteration) {
        this.bornIteration = bornIteration;
    }

    @Override
    public String toString() {
        return alive ? "1" : "0";
    }

}