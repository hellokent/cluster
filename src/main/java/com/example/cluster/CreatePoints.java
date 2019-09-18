package com.example.cluster;

import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;

public class CreatePoints extends JFrame{

    public CreatePoints(String s) {
        super(s);
        final ChartPanel chartPanel = createDemoPanel();
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                Rectangle2D dataArea = chartPanel.getScreenDataArea();
                JFreeChart chart = event.getChart();
                XYPlot plot = (XYPlot) chart.getPlot();

                ValueAxis xAxis = plot.getDomainAxis();
                double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, plot.getDomainAxisEdge());
                double y = plot.getRangeAxis().java2DToValue(event.getTrigger().getY(),dataArea, plot.getRangeAxisEdge());
                System.out.println("x=" + x + ", y=" + y);
                series.add(x, y);
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {

            }
        });
        add(chartPanel, BorderLayout.CENTER);
        JPanel control = new JPanel();
        control.add(new JButton(new AbstractAction("Export") {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < series.getItemCount(); i++) {
                    series.getItems().get(i);
                }
            }
        }));
        this.add(control, BorderLayout.SOUTH);
    }

    XYSeries series = new XYSeries("Random");
    private ChartPanel createDemoPanel() {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(series);
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                "CreatePoints", "X", "Y", xySeriesCollection,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);



        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        xyPlot.getRangeAxis().setAutoRange(false);
        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setVerticalTickLabels(true);
        domain.setAutoRange(false);
        return new ChartPanel(jfreechart);
    }



    public static void main(String[] args) {
        CreatePoints createPoints = new CreatePoints("create points");
        createPoints.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createPoints.pack();
        createPoints.setLocationRelativeTo(null);
        createPoints.setVisible(true);
    }
}
