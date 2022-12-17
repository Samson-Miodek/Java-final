package ru.sadykoff;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.StandardChartTheme;

import org.jfree.ui.RectangleInsets;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.HorizontalAlignment;

import org.jfree.data.general.PieDataset;

public class PieChart extends ApplicationFrame
{

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }
    public PieChart(String title, PieDataset dataset) {
        super(title);
        setContentPane(createChartPanel(dataset));
    }
    public JPanel createChartPanel(PieDataset dataset)
    {
        var chart = createChart(dataset);
        chart.setPadding(new RectangleInsets(15, 15, 15, 15));

        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    private JFreeChart createChart(PieDataset dataset)
    {
        var chart = ChartFactory.createPieChart(
                "Пользователи в интернете от всего населения по субрегионам",
                dataset
        );

        var title = chart.getTitle();
        title.setHorizontalAlignment(HorizontalAlignment.LEFT);
        title.setPaint(Color.DARK_GRAY);
        title.setFont(new Font("Arial", Font.BOLD, 26));

        var plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.DARK_GRAY);
        plot.setLabelBackgroundPaint(null);
        return chart;
    }

    public static void showDataset(PieDataset dataset)
    {
        var demo = new PieChart("График процентного соотношения пользователей в интернете от всего населения по субрегионам.",dataset);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
