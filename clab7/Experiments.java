import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Skeleton created by hug.
 * Methods written by Yuan Cao.
 */

public class Experiments {
    public static void experiment1() {
        // it runs a computational experiment where you insert 5000 random items into a BST.
        RandomGenerator r = new RandomGenerator();
        BST b = new BST();

        List<Double> yValues = new ArrayList<>();
        List<Double> y2Values = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();

        for (int x = 0; x < 5000; x += 1) {
            b.add(r.getRandomInt(100000));
            Double avgDep = b.averageDepth();
            xValues.add(x);
            yValues.add(avgDep);
            double optAvgDep = ExperimentHelper.optimalAverageDepth(x);
            y2Values.add(optAvgDep);
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("BST Size").yAxisTitle("Depth").build();
        chart.addSeries("Average Depth Of Our BST", xValues, yValues);
        chart.addSeries("Optimal Average Depth", xValues, y2Values);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment2() {
        RandomGenerator r = new RandomGenerator();
        BST b = new BST();

        List<Double> yValues = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();

        for (int i = 0; i < 500; i++) {       // N = 5000
            b.add(r.getRandomInt(1000000));
        }

        for (int m = 0; m <= 200000; m++) {      // M = 1000
            Double avgDep = b.averageDepth();  // Record the starting depth
            xValues.add(m);
            yValues.add(avgDep);
            b.deleteTakingSuccessor(b.getRandomKey()); // Randomly delete an item
            b.add(r.getRandomInt(1000000));        // Randomly insert a new item
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Time").yAxisTitle("Average Depth").build();
        chart.addSeries("Average Depth", xValues, yValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void experiment3() {
        RandomGenerator r = new RandomGenerator();
        BST b = new BST();

        List<Double> yValues = new ArrayList<>();
        List<Integer> xValues = new ArrayList<>();

        for (int i = 0; i < 500; i++) {       // N = 5000
            b.add(r.getRandomInt(1000000));
        }

        for (int m = 0; m <= 200000; m++) {      // M = 1000
            Double avgDep = b.averageDepth();  // Record the starting depth
            xValues.add(m);
            yValues.add(avgDep);
            b.deleteTakingRandom(b.getRandomKey()); // Randomly delete an item
            b.add(r.getRandomInt(1000000));        // Randomly insert a new item
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("Time").yAxisTitle("Average Depth").build();
        chart.addSeries("Average Depth", xValues, yValues);

        new SwingWrapper(chart).displayChart();
    }

    public static void main(String[] args) {
        experiment3();
    }

}
