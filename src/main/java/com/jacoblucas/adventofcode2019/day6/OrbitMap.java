package com.jacoblucas.adventofcode2019.day6;


import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

public class OrbitMap {
    static final String COM = "COM";
    static final SpaceObject CENTER_OF_MASS = new SpaceObject(COM, null);

    private Map<String, SpaceObject> objects;

    public OrbitMap() {
        objects = HashMap.of(COM, CENTER_OF_MASS);
    }

    public Option<SpaceObject> get(final String id) {
        return objects.get(id);
    }

    public int checksum() {
        return objects.values()
                .map(SpaceObject::toString)
                .map(str -> str.split("\\)").length - 1)
                .sum()
                .intValue();
    }

    public Try<SpaceObject> addSatellite(final String id, final String primaryId) {
        return Try.of(() -> {
            final SpaceObject primary = get(primaryId).getOrElse(() -> {
                final SpaceObject primaryTemp = new SpaceObject(primaryId, null);
                objects = objects.put(primaryId, primaryTemp);
                return primaryTemp;
            });

            final Option<SpaceObject> existingOption = get(id);
            if (existingOption.isDefined()) {
                final SpaceObject existing = existingOption.get();
                if (existing.getPrimary() != null) {
                    throw new RuntimeException(String.format("Cannot add satellite [%s] more than once", id));
                } else {
                    // need to position existing as satellite orbiting the primaryId
                    existing.updatePrimary(primary);
                    return existing;
                }
            } else {
                // add as new satellite under primary
                final SpaceObject newSatellite = new SpaceObject(id, primary);
                objects = objects.put(id, newSatellite);
                primary.addSatellite(newSatellite);
                return newSatellite;
            }
        });
    }

    int size() {
        return objects.size();
    }

    void validate() {
        final Seq<Validation<SpaceObject, SpaceObject>> validations = objects.values()
                .filter(spaceObject -> !spaceObject.getId().equals(COM))
                .filter(spaceObject -> spaceObject.getPrimary() == null)
                .map(Validation::invalid);

        final Seq<Validation<SpaceObject, SpaceObject>> invalids = validations.filter(Validation::isInvalid);
        if (!invalids.isEmpty()) {
            final String nullPrimaries = invalids.map(i -> i.get().getId()).mkString(",");
            throw new RuntimeException(
                    String.format("invalid orbit map detected - null primaries found for the following satellites: %s",
                            nullPrimaries));
        }
    }
}
