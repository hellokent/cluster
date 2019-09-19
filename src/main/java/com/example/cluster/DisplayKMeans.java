package com.example.cluster;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayKMeans extends JFrame {
    private ScatterFrame frame = new ScatterFrame();

    public DisplayKMeans(String s, String file) throws IOException {
        super(s);
        frame.setCreateData(false);
        ClusterHelper.readData(file, frame);
        frame.setTitle(s);
        final ChartPanel chartPanel = frame.createChart();
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws IOException {
        String inputFile = "data/sample_1.csv";
        KMeansCluster cluster = new KMeansCluster(5);
        String outputFile = "data/" + cluster.getClass().getSimpleName() + ".csv";
        ClusterHelper.cluster(cluster, inputFile, outputFile);
        DisplayKMeans demo = new DisplayKMeans(cluster.getClass().getSimpleName(), outputFile);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.pack();
        demo.setLocationRelativeTo(null);
        demo.setVisible(true);
    }
}
