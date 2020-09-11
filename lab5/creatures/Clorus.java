package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import javax.swing.text.html.HTMLDocument;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
/** @author Yuan Cao*/

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;

    public Clorus(double e) {
        super("clorus");
        energy = e;
        r = 34;
        g = 0;
        b = 231;
    }

    public Color color() {
        return color(r, g, b);
    }

    public void move() {
        energy -= 0.03;
        if (energy <= 0) {
            energy = 0;
        }
    }

    public void stay() {
        energy -= 0.01;
        if (energy <= 0) {
            energy = 0;
        }
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy * 0.5);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        // Rule 1: Stay if no empty space around.

        for (Direction key: neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")) {
                emptyNeighbors.addFirst(key);
            }
            if (neighbors.get(key).name().equals("plip")) {
                plipNeighbors.addFirst(key);
            }
        }
        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2: Attack randomly if any plips in sight.
        else {
            if (!plipNeighbors.isEmpty()) {
                int choices = plipNeighbors.size();
                int n = (int) Math.ceil(Math.random() * choices);
                for (int i = 1; i < n; i++) {
                    plipNeighbors.removeFirst();
                }
                return new Action(Action.ActionType.ATTACK, plipNeighbors.getFirst());
            }

            else {
                int choices2 = emptyNeighbors.size();
                int n2 = (int) Math.ceil(Math.random() * choices2);
                for (int i = 1; i < n2; i++) {
                    emptyNeighbors.removeFirst();
                }

                // Rule 3: If the Clorus has energy greater than or equal to one, it will REPLICATE to a random empty square.
                if (energy >= 1) {
                    return new Action(Action.ActionType.REPLICATE, emptyNeighbors.getFirst());
                }

                // Rule 4: Otherwise, move.
                else {
                    return new Action(Action.ActionType.MOVE, emptyNeighbors.getFirst());
                }
            }
        }
    }
}
