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
        Files.readAllLines(Paths.get(file))
            .stream()
            .filter(Strings::isNotBlank)
            .forEach(str -> {
                String key = null;
                int index = str.indexOf(":");
                if (index >= 0) {
                    key = str.substring(0, str.indexOf(":"));
                    str = str.substring(index + 1);
                }
                String[] strings = str.split(",");
                System.out.printf("add:" + str);
                if (key != null) {
                    frame.addData(key, Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
                } else {
                    frame.addData(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
                }
            });
        frame.setTitle(s);
        final ChartPanel chartPanel = frame.createChart();
        add(chartPanel, BorderLayout.CENTER);
    }



    public static void main(String[] args) throws IOException {
        String file = "data/cluster_1.csv";
        DisplayData demo = new DisplayData("DisplaySample", file);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.pack();
        demo.setLocationRelativeTo(null);
        demo.setVisible(true);
    }
}
