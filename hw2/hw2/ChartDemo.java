package hw2;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by hug. Demonstrated how to use the xchart library.
 */
public class ChartDemo {
    public static void main(String[] args) {
//        List<Double> yValues = new ArrayList<>();
//        List<Integer> NValues = new ArrayList<>();
//        int N = 5;
//        int T = 30;
//
//        for (int i = 0; i < 8; i++) {
//            PercolationFactory pf = new PercolationFactory();
//            Stopwatch sw = new Stopwatch();
//            PercolationStats pstat = new PercolationStats(N, T, pf);
//            NValues.add(N);
//            yValues.add(sw.elapsedTime());
//            N = N * 2;
//        }
//
//        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("N").yAxisTitle("time").build();
//        chart.addSeries("N - Time chart", NValues, yValues);

        List<Integer> TValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        int N2 = 20;
        int T2 = 10;

        for (int i = 0; i < 8; i++) {
            PercolationFactory pf = new PercolationFactory();
            Stopwatch sw = new Stopwatch();
            PercolationStats pstat = new PercolationStats(N2, T2, pf);
            TValues.add(T2);
            y2Values.add(sw.elapsedTime());
            T2 = T2 * 2;
        }

        XYChart chart2 = new XYChartBuilder().width(800).height(600).xAxisTitle("T").yAxisTitle("time").build();
        chart2.addSeries("T - Time chart", TValues, y2Values);

        new SwingWrapper(chart2).displayChart();

    }
}
