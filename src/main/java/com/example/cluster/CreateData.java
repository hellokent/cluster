package com.example.cluster;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import lombok.Setter;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CreateData extends JFrame {
    @Setter
    private String file;
    private ScatterFrame frame = new ScatterFrame();

    public CreateData(String s) {
        super(s);
        frame.setCreateData(true);
        frame.setTitle(s);
        final ChartPanel chartPanel = frame.createChart();
        add(chartPanel, BorderLayout.CENTER);
        JPanel control = new JPanel();
        control.add(new JButton(new AbstractAction("Export") {

            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder buffer = new StringBuilder();
                XYSeries added = frame.getDefXYSeries();
                for (int i = 0; i < added.getItemCount(); i++) {
                    XYDataItem item = (XYDataItem) added.getItems().get(i);
                    buffer.append(item.getX()).append(",").append(item.getY()).append("\n");
                }
                try {
                    File f = new File(file);
                    if ((!f.exists() && f.createNewFile()) || f.exists()) {
                        Files.write(buffer.toString().getBytes(), f);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
        this.add(control, BorderLayout.SOUTH);
    }



    public static void main(String[] args) {
        String file = "data/sample_3.csv";
        CreateData demo = new CreateData("CreateData");
        demo.setFile(file);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.pack();
        demo.setLocationRelativeTo(null);
        demo.setVisible(true);
    }
}
