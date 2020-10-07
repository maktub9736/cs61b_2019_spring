package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import java.util.*;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private List<Vertex> solR;                  // solution in reverse order
    private double timeSpent;
    private int numStatesExplored;
    private double solWeight;
    private Map<Vertex, Vertex> edgeTo;
    private Map distTo;
    private ArrayHeapMinPQ<Vertex> fringe;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        fringe = new ArrayHeapMinPQ<>();
        Stopwatch sw = new Stopwatch();

        distTo.put(start, 0.0);
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        Vertex curr = start;
        while (sw.elapsedTime() < timeout) {
            if (curr.equals(end)|| fringe.size() == 0) {
                break;
            }
            curr = fringe.removeSmallest();
            numStatesExplored += 1;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(curr);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e, input, end);
            }
        }

        timeSpent = sw.elapsedTime();
        if (curr.equals(end)) {
            outcome = SolverOutcome.SOLVED;
            solWeight = (double) distTo.get(end);
            solR = new ArrayList<Vertex>();
            while (!curr.equals(start)) {
                solR.add(curr);
                curr = (Vertex) edgeTo.get(curr);
            }
            solR.add(start);
        } else {
            outcome = SolverOutcome.UNSOLVABLE;
        }
    }

    public void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> input, Vertex end) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (!distTo.containsKey(q)) {
            distTo.put(q, Double.MAX_VALUE);
        }
        if ((double) distTo.get(p) + w < (double) distTo.get(q)) {
            distTo.replace(q, (double) distTo.get(p) + w);
            if (fringe.contains(q)) {
                fringe.changePriority(q, (double) distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                edgeTo.replace(q, p);
            } else {
                fringe.add(q, (double) distTo.get(q) + input.estimatedDistanceToGoal(q, end));
                edgeTo.put(q, p);
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        if (outcome.equals(SolverOutcome.SOLVED)) {
            int m = solR.size();
            ArrayList sol = new ArrayList(m);
            for (int i = 0; i < m; i++) {
                sol.add(solR.get(m - 1 - i));
            }
            return sol;
        } else {
            return List.of();
        }
    }

    @Override
    public double solutionWeight() {
        return solWeight;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }
}


