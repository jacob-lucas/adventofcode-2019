package com.jacoblucas.adventofcode2019.day6;


import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

public class OrbitMap {
    static final String COM = "COM";
    SpaceObject centerOfMass;

    private Map<String, SpaceObject> objects;

    public OrbitMap() {
        centerOfMass = new SpaceObject(COM, null);
        objects = HashMap.of(COM, centerOfMass);
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

    public List<SpaceObject> path(final String fromId, final String toId) {
        final Option<SpaceObject> fromOption = get(fromId);
        final Option<SpaceObject> toOption = get(toId);
        if (fromOption.isEmpty() || toOption.isEmpty()) {
            return List.of();
        }

        final SpaceObject from = fromOption.get();
        final SpaceObject to = toOption.get();

        return path(to, List.of(), Queue.of(from), HashSet.of());
    }

    private List<SpaceObject> path(final SpaceObject destination, List<SpaceObject> pathSoFar, Queue<SpaceObject> queue, Set<SpaceObject> visited) {
        if (queue.isEmpty()) {
            return List.of(); // not found
        }

        final SpaceObject current = queue.head();
        final List<SpaceObject> path = pathSoFar.append(current);
        final Set<SpaceObject> updatedVisited = visited.add(current);

        if (current.equals(destination)) {
            return path;
        } else {
            final SpaceObject primary = current.getPrimary();
            final List<SpaceObject> unvistedSatellites = current.getSatellites().filter(s -> !updatedVisited.contains(s));
            Queue<SpaceObject> updatedQueue = queue.tail();

            // attempt to find in all unvisited satellites
            for (final SpaceObject s : unvistedSatellites) {
                final List<SpaceObject> attemptedPath = path(destination, path, updatedQueue.append(s), updatedVisited);
                if (!attemptedPath.isEmpty()) {
                    return attemptedPath;
                }
            }

            // add primary to the queue
            if (primary != null && !updatedVisited.contains(primary)) {
                updatedQueue = updatedQueue.append(primary);
            }

            return path(destination, path, updatedQueue, updatedVisited);
        }
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
