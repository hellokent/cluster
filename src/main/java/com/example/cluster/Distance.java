package com.example.cluster;

import org.apache.commons.lang3.tuple.Pair;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public enum Distance implements IDistance{
    Euclidean {
        @Override
        public double distance(Loc a, Loc b) {
            return sqrt(pow(a.getLeft() - b.getLeft(), 2.0) + pow(a.getRight() - b.getRight(), 2.0));
        }
    },
    Manhattan {
        @Override
        public double distance(Loc a, Loc b) {
            return Math.abs(a.getLeft() - b.getLeft()) + Math.abs(a.getRight() - b.getRight());
        }
    },
    Chebyshev {
        @Override
        public double distance(Loc a, Loc b) {
            return Math.max(Math.abs(a.getLeft() - b.getLeft()) , Math.abs(a.getRight() - b.getRight()));
        }
    },
    Cosine {
        @Override
        public double distance(Loc a, Loc b) {
            return (a.getLeft() * b.getLeft() + a.getRight() * b.getRight()) /
                (sqrt((pow(a.getLeft(), 2) + pow(a.getRight(), 2)) * (pow(b.getLeft(), 2) + pow(b.getRight(), 2))));
        }
    },
    ;
}
