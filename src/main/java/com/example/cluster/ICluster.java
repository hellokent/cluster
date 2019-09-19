package com.example.cluster;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface ICluster {
    List<List<Loc>> cluster(List<Loc> dataList);
}
