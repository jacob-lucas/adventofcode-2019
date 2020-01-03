package com.jacoblucas.adventofcode2019.day6;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;

import static com.jacoblucas.adventofcode2019.day6.OrbitMap.COM;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class OrbitMapTest {

    private OrbitMap orbitMap;

    private void verifySatelliteSetup(final SpaceObject satellite, final String expectedPrimaryId, final String... expectedSatellites) {
        assertThat(satellite.getPrimary().getId(), is(expectedPrimaryId));
        assertThat(satellite.getSatellites().map(SpaceObject::getId).collect(Collectors.toList()), containsInAnyOrder(expectedSatellites));
    }

    @Before
    public void setUp() {
        this.orbitMap = new OrbitMap();
    }

    @Test
    public void testGet() {
        assertThat(orbitMap.get(COM), is(Some(orbitMap.centerOfMass)));
        assertThat(orbitMap.get("unknown"), is(None()));
    }

    @Test
    public void testAddNewSatelliteWithCOMPrimary() {
        final String id = "a";
        final int size = orbitMap.size();
        final Try<SpaceObject> spaceObjectTry = orbitMap.addSatellite(id, COM);

        assertThat(spaceObjectTry.isSuccess(), is(true));
        assertThat(orbitMap.size(), is(size + 1));

        final SpaceObject a = spaceObjectTry.get();
        assertThat(a.getId(), is(id));
        assertThat(a.getPrimary(), is(orbitMap.centerOfMass));
        assertThat(a.getSatellites(), is(List.of()));
        assertThat(a.getPrimary().getSatellites(), contains(a));
    }

    @Test
    public void testFailToAddDuplicateId() {
        final String id = "a";
        final Try<SpaceObject> spaceObjectTry1 = orbitMap.addSatellite(id, COM);
        assertThat(spaceObjectTry1.isSuccess(), is(true));
        final Try<SpaceObject> spaceObjectTry2 = orbitMap.addSatellite(id, COM);
        assertThat(spaceObjectTry2.isSuccess(), is(false));
    }

    @Test
    public void testAddMissingPrimary() {
        final String id = "a";
        final String primaryId = "unknown";
        final Try<SpaceObject> spaceObjectTry = orbitMap.addSatellite(id, primaryId);

        assertThat(spaceObjectTry.isSuccess(), is(true));

        final SpaceObject added = orbitMap.get(id).get();
        final SpaceObject primary = orbitMap.get(primaryId).get();
        assertThat(primary.getPrimary(), is(nullValue()));
        assertThat(primary.getSatellites(), is(List.of(added)));
    }

    @Test
    public void testAddNewSatelliteWithExistingPrimary() {
        final String existingId = "a";
        final String id = "b";
        orbitMap.addSatellite(existingId, COM);
        orbitMap.addSatellite(id, existingId);

        assertThat(orbitMap.size(), is(3));

        final Option<SpaceObject> a = orbitMap.get(existingId);
        final Option<SpaceObject> b = orbitMap.get(id);

        assertThat(a.isDefined(), is(true));
        assertThat(b.isDefined(), is(true));

        assertThat(b.get().getId(), is(id));
        assertThat(b.get().getPrimary(), is(a.get()));
        assertThat(b.get().getSatellites(), is(List.of()));

        assertThat(a.get().getSatellites(), is(List.of(b.get())));
    }

    @Test
    public void testOutOfOrderAdds() {
        final String id1 = "a";
        final String id2 = "b";
        final String id3 = "c";
        final String id4 = "d";

        // COM)a)b)c
        // b)d
        final SpaceObject c = orbitMap.addSatellite(id3, id2).get();
        final SpaceObject a = orbitMap.addSatellite(id1, COM).get();
        final SpaceObject b = orbitMap.addSatellite(id2, id1).get();
        final SpaceObject d = orbitMap.addSatellite(id4, id2).get();

        verifySatelliteSetup(a, COM, "b");
        verifySatelliteSetup(b, "a", "c", "d");
        verifySatelliteSetup(c, "b");
        verifySatelliteSetup(d, "b");
    }

    @Test
    public void testExample() {
        orbitMap.addSatellite("B", COM);
        orbitMap.addSatellite("C", "B");
        orbitMap.addSatellite("D", "C");
        orbitMap.addSatellite("E", "D");
        orbitMap.addSatellite("F", "E");
        orbitMap.addSatellite("G", "B");
        orbitMap.addSatellite("H", "G");
        orbitMap.addSatellite("I", "D");
        orbitMap.addSatellite("J", "E");
        orbitMap.addSatellite("K", "J");
        orbitMap.addSatellite("L", "K");

        assertThat(orbitMap.size(), is(12));
        verifySatelliteSetup(orbitMap.get("B").get(), COM, "C", "G");
        verifySatelliteSetup(orbitMap.get("C").get(), "B", "D");
        verifySatelliteSetup(orbitMap.get("D").get(), "C", "E", "I");
        verifySatelliteSetup(orbitMap.get("E").get(), "D", "F", "J");
        verifySatelliteSetup(orbitMap.get("F").get(), "E");
        verifySatelliteSetup(orbitMap.get("G").get(), "B", "H");
        verifySatelliteSetup(orbitMap.get("H").get(), "G");
        verifySatelliteSetup(orbitMap.get("I").get(), "D");
        verifySatelliteSetup(orbitMap.get("J").get(), "E", "K");
        verifySatelliteSetup(orbitMap.get("K").get(), "J", "L");
        verifySatelliteSetup(orbitMap.get("L").get(), "K");
    }

    @Test
    public void testChecksum() {
        // COM)B)C)D == 3
        // COM)B)C)D)E)J)K)L == 7

        orbitMap.addSatellite("B", COM);
        orbitMap.addSatellite("C", "B");
        orbitMap.addSatellite("D", "C");
        orbitMap.addSatellite("E", "D");
        orbitMap.addSatellite("F", "E");
        orbitMap.addSatellite("G", "B");
        orbitMap.addSatellite("H", "G");
        orbitMap.addSatellite("I", "D");
        orbitMap.addSatellite("J", "E");
        orbitMap.addSatellite("K", "J");
        orbitMap.addSatellite("L", "K");

        assertThat(orbitMap.checksum(), is(42));
    }
}
