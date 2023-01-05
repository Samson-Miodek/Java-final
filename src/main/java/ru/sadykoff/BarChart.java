package ru.sadykoff;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;

public class BarChart extends JFrame {
    public BarChart(CategoryDataset dataset) {
        var chart = ChartFactory.createBarChart(
                "График процентного соотношения пользователей в интернете от всего населения по субрегионам",
                "",
                "Процент пользователей интернета в субрегионе",
                dataset,
                PlotOrientation.HORIZONTAL,
                false, true, false);
        var chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("График процентного соотношения пользователей в интернете от всего населения по субрегионам");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        EventQueue.invokeLater(() -> {
            setVisible(true);
        });
    }
}
