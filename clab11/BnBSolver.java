import java.util.ArrayList;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private Pair<ArrayList<Bear>, ArrayList<Bed>> myPair;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        // DONE: Fix me.
        Pair Unsorted = new Pair((ArrayList<Bear>) bears, (ArrayList<Bed>) beds);
        myPair = quickSort(Unsorted);
    }

    private void partition(Pair<ArrayList<Bear>, ArrayList<Bed>> Unsorted,
                           Pair<ArrayList<Bear>, ArrayList<Bed>> Less,
                           Pair<ArrayList<Bear>, ArrayList<Bed>> Equal,
                           Pair<ArrayList<Bear>, ArrayList<Bed>> Greater) {
        int pivotIndex = (int) (Math.random() * Unsorted.first().size());
        Bear pivotBear = Unsorted.first().get(pivotIndex);
        for (Bed bed : Unsorted.second()) {
            int cmp = bed.compareTo(pivotBear);
            if (cmp < 0) {
                Less.second().add(bed);
            } else if (cmp == 0) {
                Equal.second().add(bed);
            } else {
                Greater.second().add(bed);
            }
        }

        Bed pivotBed = Equal.second().get(0);
        for (Bear bear : Unsorted.first()) {
            int cmp2 = bear.compareTo(pivotBed);
            if (cmp2 < 0) {
                Less.first().add(bear);
            } else if (cmp2 == 0) {
                Equal.first().add(bear);
            } else {
                Greater.first().add(bear);
            }
        }
    }

    private Pair<ArrayList<Bear>, ArrayList<Bed>> catenate(Pair<ArrayList<Bear>, ArrayList<Bed>> p1,
                                                           Pair<ArrayList<Bear>, ArrayList<Bed>> p2) {
        ArrayList<Bear> catenatedBear = new ArrayList<Bear>();
        for (Bear bear : p1.first()) {
            catenatedBear.add(bear);
        }
        for (Bear bear : p2.first()) {
            catenatedBear.add(bear);
        }

        ArrayList<Bed> catenatedBed = new ArrayList<Bed>();
        for (Bed bed : p1.second()) {
            catenatedBed.add(bed);
        }
        for (Bed bed : p2.second()) {
            catenatedBed.add(bed);
        }

        return new Pair(catenatedBear, catenatedBed);
    }

    private Pair<ArrayList<Bear>, ArrayList<Bed>> quickSort(Pair<ArrayList<Bear>, ArrayList<Bed>> pair) {
        if (pair.first().size() <= 1) {
            return pair;
        }
        Pair<ArrayList<Bear>, ArrayList<Bed>> Less = makeEmptyPair();
        Pair<ArrayList<Bear>, ArrayList<Bed>> Equal = makeEmptyPair();
        Pair<ArrayList<Bear>, ArrayList<Bed>> Greater = makeEmptyPair();
        partition(pair, Less, Equal, Greater);
        Pair<ArrayList<Bear>, ArrayList<Bed>> catenated = catenate(catenate(quickSort(Less), Equal), quickSort(Greater));
        return catenated;
    }

    private Pair<ArrayList<Bear>, ArrayList<Bed>> makeEmptyPair() {
        ArrayList<Bear> bear = new ArrayList<Bear>();
        ArrayList<Bed> bed = new ArrayList<Bed>();
        return new Pair(bear, bed);
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        // DONE: Fix me.
        return myPair.first();
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        // DONE: Fix me.
        return myPair.second();
    }
}
