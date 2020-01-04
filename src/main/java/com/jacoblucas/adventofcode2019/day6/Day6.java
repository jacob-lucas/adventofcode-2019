package com.jacoblucas.adventofcode2019.day6;

import com.jacoblucas.adventofcode2019.utils.InputReader;
import io.vavr.collection.List;
import io.vavr.control.Try;

public class Day6 {
    public static void main(String[] args) {
        final OrbitMap orbitMap = new OrbitMap();

        InputReader.read("day6-input.txt")
                .forEach(input -> {
                    final String primaryId = input.split("\\)")[0];
                    final String id = input.split("\\)")[1];
                    final Try<SpaceObject> spaceObject = orbitMap.addSatellite(id, primaryId);
                    if (spaceObject.isFailure()) {
                        throw new RuntimeException(String.format("Could not add SpaceObject %s", input), spaceObject.getCause());
                    }
                });

        orbitMap.validate();

        System.out.println(orbitMap.checksum());

        final String src = "YOU";
        final String dest = "SAN";
        final Try<List<SpaceObject>> pathTry = Try.of(() -> {
            final SpaceObject orbiting = orbitMap.get(src).get().getPrimary();
            final SpaceObject santaOrbiting = orbitMap.get(dest).get().getPrimary();
            return orbitMap.path(orbiting.getId(), santaOrbiting.getId());
        });

        if (pathTry.isFailure()) {
            System.out.println(String.format("Unable to trace path between %s and %s", src, dest));
        } else {
            System.out.println(String.format("Minimum number of orbital transfers required = %d", pathTry.get().size() - 1));
        }
    }
}
