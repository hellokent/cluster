package com.example.cluster;

import com.google.common.collect.Lists;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class LevelCluster implements ICluster {

    @Setter
    private IDistance distance = Distance.Euclidean;

    private final int K;

    public LevelCluster(int k) {
        K = k;
    }

    @Override
    public List<List<Loc>> cluster(List<Loc> dataList) {
        List<List<Loc>> result = new ArrayList<>(dataList.size());
        dataList.forEach(loc -> result.add(Lists.newArrayList(loc)));

        double maxDis = 0.5;

        while(true) {
            int originSize = result.size();
            for (int i = 0; i < originSize; i++) {
                List<Loc> locList = result.get(i);
                if (locList.isEmpty()) {
                    continue;
                }
                IntStream.range(i + 1, originSize)
                        .parallel()
                        .mapToObj(result::get)
                        .filter(list -> !list.isEmpty())
                        .filter(l -> singleDistance(locList, l) < maxDis)
                        .peek(locList::addAll)
                        .forEach(Collection::clear);
            }
            result.removeIf(Collection::isEmpty);
            int newSize = result.size();
            if (newSize == originSize) {
                break;
            }
        }

//        while (result.size() > K) {
//            IntStream.range(0, result.size())
//                .boxed()
//                .flatMap(i -> IntStream.range(i + 1, result.size())
//                    .boxed()
//                    .map(j -> Pair.of(i, j)))
//                .parallel()
//                .min(Comparator.comparingDouble(pair -> singleDistance(result.get(pair.getLeft()), result.get(pair.getRight()))))
//                .ifPresent(pair -> {
//                    result.get(pair.getLeft()).addAll(result.get(pair.getRight()));
//                    result.remove(pair.getRight().intValue());
//                });
//        }
        return result;
    }

    private double singleDistance(List<Loc> aList, List<Loc> bList) {
        return IntStream.range(0, aList.size())
            .boxed()
            .flatMap(i -> IntStream.range(0, bList.size())
                .boxed()
                .map(j -> Pair.of(i, j)))
            .mapToDouble(pair -> distance.distance(aList.get(pair.getLeft()), bList.get(pair.getRight())))
            .min()
            .orElse(10);
    }
}
