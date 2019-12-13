package com.example.cluster;

import com.google.common.base.Strings;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public final class ClusterHelper {

    public static void cluster(ICluster cluster, String dataPath, String clusterDatPath) throws IOException {
        List<Loc> dataList = Files.readAllLines(Paths.get(dataPath))
            .stream()
            .filter(line -> !Strings.isNullOrEmpty(line))
            .map(line -> line.split(","))
            .map(array -> new Loc(Double.parseDouble(array[0]), Double.parseDouble(array[1])))
            .collect(Collectors.toList());
        long beginTime = System.currentTimeMillis();
        List<List<Loc>> clusterResult = cluster.cluster(dataList);
        System.out.println("using:" + (System.currentTimeMillis() - beginTime));
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < clusterResult.size(); i++) {
            for (Pair<Double, Double> pair : clusterResult.get(i)) {
                if (pair != null) {
                    builder.append(i).append(":").append(pair.getLeft()).append(",").append(pair.getRight()).append("\n");
                } else {
                    System.out.println("null pair:" + i);
                }
            }
        }
        Files.write(Paths.get(clusterDatPath), builder.toString().getBytes());
    }


    public static void readData(String file, ScatterFrame frame) throws IOException {
        Files.readAllLines(Paths.get(file))
            .stream()
            .filter(line -> !Strings.isNullOrEmpty(line))
            .forEach(str -> {
                String key = null;
                int index = str.indexOf(":");
                if (index >= 0) {
                    key = str.substring(0, str.indexOf(":"));
                    str = str.substring(index + 1);
                }
                String[] strings = str.split(",");
                if (key != null) {
                    frame.addData(key, Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
                } else {
                    frame.addData(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
                }
            });
    }

    public static void displayJFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
