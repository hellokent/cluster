package com.example.cluster;

import com.google.common.collect.Lists;
import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DBScanCluster implements ICluster {

    private final IDistance distance = Distance.Euclidean;
    private final double minRadius;
    private final int minPts;

    public DBScanCluster(double minRadius, int minPts) {
        this.minRadius = minRadius;
        this.minPts = minPts;
    }

    @Override
    public List<List<Loc>> cluster(List<Loc> dataList) {
        int size = dataList.size();

        int k = -1;
        for (int i = 0; i < size; i++) {
            Loc loc = dataList.get(i);
            if (loc.isVisited()) {
                continue;
            }
            List<Loc> nearLocationList = nearLoc(dataList, loc);
            if (nearLocationList.size() < 2) {
                loc.setVisited(true);
                loc.setNoise(true);
            } else if (nearLocationList.size() >= minPts) {
                k++;
                loc.setVisited(true);
                loc.setClusterId(k);
                expandCluster(nearLocationList, k, dataList);
            }
        }
        System.out.println("K:" + k);
        List<List<Loc>> result = new ArrayList<>();
        for (int i = 0; i <= k + 1; i++) {
            result.add(new ArrayList<>());
        }
        int finalK = k;
        dataList.stream()
            .forEach(loc -> {
                if (loc.isNoise()) {
                    result.get(finalK + 1).add(loc);
                } else if (loc.getClusterId() >= 0){
                    List<Loc> locs = result.get(loc.getClusterId());
                    //locs.addAll(nearLoc(dataList, loc));
                    locs.add(loc);
                }
            });


        return result;
    }

    private void expandCluster(List<Loc> nearLocList, int k, List<Loc> dataList) {
        for (int i = 0; i < nearLocList.size(); i++) {
            Loc loc = nearLocList.get(i);
            if (!loc.isVisited()) {
                loc.setVisited(true);
                List<Loc> subNearLoc = nearLoc(dataList, loc);
                if (subNearLoc.size() >= minPts) {
                    loc.setClusterId(k);
                }
                if (subNearLoc.size() >= 1) {
                    nearLocList.addAll(subNearLoc);
                }
            }
            loc.setClusterId(k);
        }
    }

    public List<Loc> nearLoc(List<Loc> dataList, Loc center) {
        return dataList.parallelStream()
            .filter(loc -> distance.distance(loc, center) < minRadius)
            .collect(Collectors.toList());
    }
}
