package com.example.cluster;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayLevel extends JFrame {

    public DisplayLevel(String file) throws IOException {
        ScatterFrame frame = new ScatterFrame();
        frame.setCreateData(false);
        ClusterHelper.readData(file, frame);
        frame.setTitle(getClass().getSimpleName());
        final ChartPanel chartPanel = frame.createChart();
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws IOException {
        String inputFile = "data/sample_4.csv";
        LevelCluster cluster = new LevelCluster(5);
        String outputFile = "data/" + cluster.getClass().getSimpleName() + ".csv";
        ClusterHelper.cluster(cluster, inputFile, outputFile);
        ClusterHelper.displayJFrame(new DisplayLevel(outputFile));
    }
}
