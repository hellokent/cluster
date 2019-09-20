package com.example.cluster;

import java.util.List;

public class OpticsCluster implements ICluster {

    private final IDistance distance = Distance.Euclidean;

    @Override
    public List<List<Loc>> cluster(List<Loc> dataList) {
        return null;
    }
}
