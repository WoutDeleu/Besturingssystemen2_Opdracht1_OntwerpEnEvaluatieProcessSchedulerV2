package Basics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.List;


// source : https://www.tutorialspoint.com/jfreechart/jfreechart_xy_chart.htm

public class XYLineChart_AWT extends ApplicationFrame {
    public XYLineChart_AWT(String applicationTitle, String chartTitle , List<Process> processes) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "ServiceTime" ,
                "Gen. TAT/Waittime" ,
                createDataset(processes) ,
                PlotOrientation.VERTICAL ,
                true , false , false
        );

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset(List<Process> processes) {

        final XYSeries waittime = new XYSeries( "waittime" );
        for(int i = 0; i<100; i++) {
            waittime.add(processes.get(i).getServicetime(), processes.get(i).getWaittime());
        }

        final XYSeries tat = new XYSeries( "gen. tat" );
        for(int i = 0; i<100; i++) {
            tat.add(processes.get(i).getServicetime(), processes.get(i).getGenTat());
        }

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(waittime);
        dataset.addSeries(tat);
        return dataset;
    }
}
