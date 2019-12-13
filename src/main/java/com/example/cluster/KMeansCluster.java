package com.example.cluster;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KMeansCluster implements ICluster {
    @Setter
    private IDistance distance = Distance.Euclidean;

    private final int K;
    @Getter
    private List<Loc> centroidList;

    public KMeansCluster(int k) {
        K = k;
        centroidList = new ArrayList<>(K);
    }

    @Override
    public List<List<Loc>> cluster(List<Loc> dataList) {
        Random random = new Random();
        List<List<Loc>> result = new ArrayList<>();

//        centroidList.add(new Loc(1.0, 0.5));
//        centroidList.add(new Loc(1.5, 7.5));
//        centroidList.add(new Loc(8.5, 8.0));
//        centroidList.add(new Loc(3.5, 4.0));
//        centroidList.add(new Loc(7.5, 2.5));
        for (int i = 0; i < K; i++) {
//            随机点
//            centroidList.add(new Loc(random.nextDouble() * 10, random.nextDouble() * 10));
//            dataList 里面随机选点
//            centroidList.add(dataList.get(random.nextInt(dataList.size())));
            result.add(new Vector<>());
        }

        List<Loc> tempLocList = new ArrayList<>(dataList);
        centroidList.add(dataList.get(0));
        for (int i = 1; i < K; i++) {
            List<Double> distList = tempLocList.stream()
                .mapToDouble(loc -> distance.distance(loc, centroidList.stream().min(Comparator.comparingDouble(l -> distance.distance(l, loc))).orElse(dataList.get(0))))
                .boxed()
                .collect(Collectors.toList());
            double sum = distList.stream().mapToDouble(Double::doubleValue).sum();
            double r = random.nextDouble() * sum;

            for (int j = 0; j < tempLocList.size(); j++) {
                r = r - distList.get(j);
                if (r <= 0) {
                    centroidList.add(tempLocList.get(j));
                    break;
                }
            }
        }

        int roundCount = 0;
        do {
            System.out.println("round:" + roundCount++);
            for (int i = 0; i < K; i++) {
                result.get(i).clear();
            }
            dataList.parallelStream()
                .forEach(loc -> {
                    int minIndex = 0;
                    double dist = distance.distance(loc, centroidList.get(0));
                    for (int i = 1; i < centroidList.size(); i++) {
                        Loc l = centroidList.get(i);
                        double newDist = distance.distance(loc, l);
                        if (newDist < dist) {
                            minIndex = i;
                            dist = newDist;
                        }
                    }
                    result.get(minIndex).add(loc);
                });

            List<Loc> newCentroidList = new ArrayList<Loc>();
            for (int i = 0; i < K; i++) {
                newCentroidList.add(new Loc());
            }
            IntStream.range(0, K)
                .parallel()
                .forEach(i -> {
                    newCentroidList.get(i).setLeft(result.get(i).parallelStream().mapToDouble(Loc::getLeft).average().orElse(0));
                    newCentroidList.get(i).setRight(result.get(i).parallelStream().mapToDouble(Loc::getRight).average().orElse(0));
                });
            if (IntStream.range(0, K)
                .allMatch(i -> Double.compare(newCentroidList.get(i).getLeft(), centroidList.get(i).getLeft()) == 0
                    && Double.compare(newCentroidList.get(i).getRight(), centroidList.get(i).getRight()) == 0)) {
                return result;
            }
            centroidList.clear();
            centroidList.addAll(newCentroidList);
        } while (true);
    }
}
