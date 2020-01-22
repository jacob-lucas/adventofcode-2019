package com.jacoblucas.adventofcode2019.utils.coordinates;

import java.util.Objects;

public class VectorCoordinate {

    private int position;
    private int velocity;

    public VectorCoordinate(int position, int velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorCoordinate that = (VectorCoordinate) o;
        return position == that.position &&
                velocity == that.velocity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, velocity);
    }

    @Override
    public String toString() {
        return String.format("[pos=%d, vel=%d]", position, velocity);
    }
}
