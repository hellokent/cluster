package com.example.cluster;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class ScatterFrame {
    @lombok.Setter
    private String title = "Scatter";

    @Getter
    private HashMap<String, Data> dataMap = new HashMap<>();

    @Setter
    private boolean createData = false;

    @Getter
    XYSeries defXYSeries = new XYSeries("def");

    public void addData(double x, double y) {
        defXYSeries.add(x, y);
    }

    public void addData(String key, double x, double y) {
        dataMap.computeIfAbsent(key, k -> {
            Data data = new Data();
            data.xySeries = new XYSeries(k);
            return data;
        }).getXySeries().add(x, y);
    }

    public ChartPanel createChart() {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        for (String key : dataMap.keySet()) {
            xySeriesCollection.addSeries(dataMap.get(key).getXySeries());
        }
        xySeriesCollection.addSeries(defXYSeries);
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
            title, "X", "Y", xySeriesCollection,
            PlotOrientation.VERTICAL, false, false, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setBackgroundPaint(Color.WHITE);
        xyPlot.setDomainGridlinePaint(Color.DARK_GRAY);
        xyPlot.setRangeGridlinePaint(Color.DARK_GRAY);

        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < dataMap.size(); i++) {
            renderer.setSeriesPaint(i + 1, new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        }

        xyPlot.getRangeAxis().setRange(0, 10);
        xyPlot.getDomainAxis().setRange(0, 10);

        xyPlot.getRangeAxis().setAutoRange(false);
        xyPlot.getDomainAxis().setAutoRange(false);
        jfreechart.setElementHinting(true);
        jfreechart.setAntiAlias(true);
        jfreechart.setTextAntiAlias(true);
        jfreechart.setBackgroundPaint(Color.WHITE);
        ChartPanel panel = new ChartPanel(jfreechart);
        panel.setMouseZoomable(false);

        if (createData) {
            panel.addChartMouseListener(new ChartMouseListener() {
                @Override
                public void chartMouseClicked(ChartMouseEvent event) {
                    Rectangle2D dataArea = panel.getScreenDataArea();
                    JFreeChart chart = event.getChart();
                    XYPlot plot = (XYPlot) chart.getPlot();

                    double x = plot.getDomainAxis().java2DToValue(event.getTrigger().getX(), dataArea, plot.getDomainAxisEdge());
                    double y = plot.getRangeAxis().java2DToValue(event.getTrigger().getY(), dataArea, plot.getRangeAxisEdge());
                    defXYSeries.add(x, y);
                }

                @Override
                public void chartMouseMoved(ChartMouseEvent event) {

                }
            });
        }
        return panel;
    }

    @lombok.Data
    private class Data {
        private XYSeries xySeries;
    }

}
