package com.example.cluster;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Loc extends MutablePair<Double, Double> {

    public Loc() {
    }

    public Loc(Double left, Double right) {
        super(left, right);
    }
}
