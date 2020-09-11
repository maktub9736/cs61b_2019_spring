package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class
 *  @author Yuan Cao
 */

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus clorus = new Clorus(1.5);
        assertEquals(1.5, clorus.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), clorus.color());
        clorus.move();
        assertEquals(1.47, clorus.energy(), 0.01);
        clorus.move();
        assertEquals(1.44, clorus.energy(), 0.01);
        clorus.stay();
        assertEquals(1.43, clorus.energy(), 0.01);
        clorus.stay();
        assertEquals(1.42, clorus.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus clorus = new Clorus(1.5);
        Clorus son = clorus.replicate();
        boolean eql = clorus.equals(son);
        assertFalse(eql);
    }

    @Test
    // Case 1: No empty adjacent spaces; stay.
    public void testChoose() {
        Clorus clorus = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = clorus.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
    }


    @Test
    // Case 2: Plips in sight but no empty neighbor, stay.
    public void testChoose2() {
        Clorus clorus = new Clorus(1.2);
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> topPlip = new HashMap<Direction, Occupant>();
        topPlip.put(Direction.TOP, p);
        topPlip.put(Direction.BOTTOM, new Impassible());
        topPlip.put(Direction.LEFT, new Impassible());
        topPlip.put(Direction.RIGHT, new Impassible());

        Action actual = clorus.chooseAction(topPlip);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);
    }

    @Test
    // Case 3: Plips in sight and there is empty space around, attack one of the plips.
    public void testChoose3() {
        Clorus clorus = new Clorus(1.2);
        Plip p2 = new Plip(0.6);
        HashMap<Direction, Occupant> PlipsThere = new HashMap<Direction, Occupant>();
        PlipsThere.put(Direction.TOP, new Empty());
        PlipsThere.put(Direction.BOTTOM, new Empty());
        PlipsThere.put(Direction.LEFT, p2);
        PlipsThere.put(Direction.RIGHT, new Impassible());

        Action actual = clorus.chooseAction(PlipsThere);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);
        assertEquals(expected, actual);
    }

    @Test
    // Case 4: No plips around but there is empty space, energy >= 1, replicate.
    public void testChoose4() {
        Clorus c2 = new Clorus(1.4);
        HashMap<Direction, Occupant> downEmpty = new HashMap<Direction, Occupant>();
        downEmpty.put(Direction.TOP, new Impassible());
        downEmpty.put(Direction.BOTTOM, new Empty());
        downEmpty.put(Direction.LEFT, new Impassible());
        downEmpty.put(Direction.RIGHT, new Impassible());

        Action actual = c2.chooseAction(downEmpty);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.BOTTOM);

        assertEquals(expected, actual);
    }

    @Test
    // Case 5: No plips around but there is empty space, energy < 1, do not replicate.
    public void testChoose5() {
        Clorus c2 = new Clorus(0.9);
        HashMap<Direction, Occupant> downEmpty = new HashMap<Direction, Occupant>();
        downEmpty.put(Direction.TOP, new Impassible());
        downEmpty.put(Direction.BOTTOM, new Empty());
        downEmpty.put(Direction.LEFT, new Impassible());
        downEmpty.put(Direction.RIGHT, new Impassible());

        Action actual = c2.chooseAction(downEmpty);
        Action expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);

        assertEquals(expected, actual);
    }
}
