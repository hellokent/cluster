package com.example.cluster;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DisplayData extends JFrame {

    private DisplayData(String s, String file) throws IOException {
        super(s);
        ScatterFrame frame = new ScatterFrame();
        frame.setCreateData(false);
        ClusterHelper.readData(file, frame);
        frame.setTitle(s);
        add(frame.createChart(), BorderLayout.CENTER);
    }



    public static void main(String[] args) throws IOException {
        ClusterHelper.displayJFrame(new DisplayData("Display Sample", "data/sample_1.csv"));
    }
}
