package com.example.cluster;

import lombok.Setter;
import org.apache.logging.log4j.util.Strings;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DisplayData extends JFrame {
    private ScatterFrame frame = new ScatterFrame();

    public DisplayData(String s, String file) throws IOException {
        super(s);
        frame.setCreateData(false);
        ClusterHelper.readData(file, frame);
        frame.setTitle(s);
        final ChartPanel chartPanel = frame.createChart();
        add(chartPanel, BorderLayout.CENTER);
    }



    public static void main(String[] args) throws IOException {
        String inputFile = "data/sample_1.csv";
        DisplayData demo = new DisplayData("", inputFile);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.pack();
        demo.setLocationRelativeTo(null);
        demo.setVisible(true);
    }
}
