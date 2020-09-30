package main.model;

public class WardModel {
    private String name;
    private long max;

    public WardModel(String name, long max) {
        this.name = name;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return name + " ward with capacity " + max;
    }
}
