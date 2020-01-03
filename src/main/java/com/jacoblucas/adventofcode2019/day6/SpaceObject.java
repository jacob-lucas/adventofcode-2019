package com.jacoblucas.adventofcode2019.day6;

import io.vavr.collection.List;

public class SpaceObject {
    private final String id;
    private SpaceObject primary;
    private List<SpaceObject> satellites;

    public SpaceObject(final String id, final SpaceObject primary) {
        this.id = id;
        this.primary = primary;
        this.satellites = List.of();
    }

    @Override
    public String toString() {
        return getPrimary() == null ? getId() : String.format("%s)%s", getPrimary(), getId());
    }

    public String getId() {
        return id;
    }

    public SpaceObject getPrimary() {
        return primary;
    }

    public List<SpaceObject> getSatellites() {
        return satellites;
    }

    public void addSatellite(final SpaceObject satellite) {
        if (satellite.getPrimary().equals(this)) {
            satellites = satellites.append(satellite);
        }
    }

    void updatePrimary(final SpaceObject newPrimary) {
        this.primary = newPrimary;
        newPrimary.addSatellite(this);
    }
}
