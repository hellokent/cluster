package com.example.cluster;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

@EqualsAndHashCode(callSuper = true)
@Data
public class Loc extends MutablePair<Double, Double> {

    private boolean visited = false;
    private boolean noise = false;
    private int clusterId = -1;

    public Loc() {
    }

    public Loc(Double left, Double right) {
        super(left, right);
    }
}
