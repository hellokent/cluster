package com.example.cluster;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayKMeans extends JFrame {
    private ScatterFrame frame = new ScatterFrame();

    public DisplayKMeans(String file) throws IOException {
        frame.setCreateData(false);
        ClusterHelper.readData(file, frame);
        frame.setTitle(getClass().getSimpleName());
        final ChartPanel chartPanel = frame.createChart();
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws IOException {
        String inputFile = "data/sample_2.csv";
        KMeansCluster cluster = new KMeansCluster(5);
        String outputFile = "data/" + cluster.getClass().getSimpleName() + ".csv";
        ClusterHelper.cluster(cluster, inputFile, outputFile);
        ClusterHelper.displayJFrame(new DisplayKMeans(outputFile));
    }
}
