package com.jacoblucas.adventofcode2019.day14;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Set;
import io.vavr.control.Option;

import java.util.function.Function;

public class Nanofactory {
    public static final String ORE = "ORE";
    public static final String FUEL = "FUEL";

    private final Map<String, Reaction> reactions;
    private Set<Chemical> supply;
    private Queue<Chemical> orders;

    public Nanofactory(final List<Reaction> reactions) {
        this.reactions = reactions.toMap(r -> r.getOutput().getId(), Function.identity());
        this.supply = HashSet.empty();
        this.orders = Queue.empty();
    }

    public int produce(final String chemical, final int amount) {
        orders = orders.append(ImmutableChemical.of(chemical, amount));

        int ore = 0;
        while (!orders.isEmpty()) {
            final Chemical order = orders.head();
            final String orderChemical = order.getId();
            final int orderAmount = order.getQuantity();
            final int inStock = getSupply(orderChemical);

            if (orderChemical.equals(ORE)) {
                ore += orderAmount;
            } else if (orderAmount <= inStock) {
                updateSupply(orderChemical, inStock - orderAmount);
            } else {
                final int amountNeeded = orderAmount - inStock;
                final Reaction reaction = reactions.get(orderChemical).get();
                final int productions = (int) Math.ceil((double)amountNeeded / reaction.getOutput().getQuantity());
                for (final Chemical input : reaction.getInputs()) {
                    orders = orders.append(ImmutableChemical.copyOf(input).withQuantity(input.getQuantity() * productions));
                }
                final int remainderQty = productions * reaction.getOutput().getQuantity() - amountNeeded;
                updateSupply(orderChemical, remainderQty);
            }

            orders = orders.tail();
        }

        return ore;
    }

    private int getSupply(final String chemical) {
        return supply.find(ch -> ch.getId().equals(chemical))
                .map(Chemical::getQuantity)
                .orElse(() -> Option.of(0))
                .get();
    }

    private void updateSupply(final String chemical, final int amount) {
        final Option<Chemical> chemicalInSupply = supply.find(ch -> ch.getId().equals(chemical));
        if (chemicalInSupply.isDefined()) {
            supply = supply.replace(chemicalInSupply.get(), ImmutableChemical.copyOf(chemicalInSupply.get()).withQuantity(amount));
        } else {
            supply = supply.add(ImmutableChemical.of(chemical, amount));
        }
    }
}
