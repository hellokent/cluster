package com.example.cluster;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayDBScan extends JFrame {

    public DisplayDBScan(String s, String file) throws IOException {
        super(s);
        ScatterFrame frame = new ScatterFrame();
        frame.setCreateData(false);
        ClusterHelper.readData(file, frame);
        frame.setTitle(s);
        final ChartPanel chartPanel = frame.createChart();
        add(chartPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) throws IOException {
        String inputFile = "data/sample_5.csv";
        DBScanCluster cluster = new DBScanCluster(0.3, 3);
        String outputFile = "data/" + cluster.getClass().getSimpleName() + ".csv";
        ClusterHelper.cluster(cluster, inputFile, outputFile);
        ClusterHelper.displayJFrame(new DisplayDBScan(cluster.getClass().getSimpleName(), outputFile));
    }
}
