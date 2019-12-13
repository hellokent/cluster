package com.example.cluster;

import com.google.common.collect.Lists;
import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
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
        List<List<Loc>> result = new ArrayList<>();

        int k = 0;
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
                loc.setVisited(true);
                int clusterId = k++;
                loc.setClusterId(clusterId);
                expandCluster(nearLocationList, clusterId, dataList);

                result.add(new Vector<>());
            }
        }
        result.add(new Vector<>());
        int noiseIndex = k;
        dataList
            .parallelStream()
            .forEach(loc -> result.get(loc.isNoise() ? noiseIndex: loc.getClusterId()).add(loc));

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
