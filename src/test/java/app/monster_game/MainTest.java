package app.monster_game;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A simple test suite to quickly add monster to the board.
 */
public class MainTest
{
    private Board board;
    private Monster s1;
    private Monster s2;
    private Monster k1;
    private Monster k2;
    private Monster p1;
    private Monster n1;
    private Monster a1;
    private Monster v1;
    private Monster m1;

    public MainTest()
    {
    }

    @Before
    public void setUp()
    {
        board = new Board(5,5);
        
        // Create 9 monsters
        s1 = new Monster("Sigmundur", board);
        s2 = new Monster("Salman",    board);
        k1 = new Monster("Kim",       board);
        k2 = new Monster("Khalifa",   board);
        p1 = new Monster("Petro",     board);
        n1 = new Monster("Nawaz",     board);
        a1 = new Monster("Asif",      board);
        v1 = new Monster("Vladimir",  board);
        m1 = new Monster("Mojo",      board);
        
        // Add 9 monsters to board (Max limit of 5x5 board)
        s1.addMonster();
        s2.addMonster();
        k1.addMonster();
        k2.addMonster();
        p1.addMonster();
        n1.addMonster();
        a1.addMonster();
        v1.addMonster();
        m1.addMonster();
    }

    @After
    public void tearDown()
    {
        board.viewBoard();
    }
    
    @Test
    public void testFindPlaceMonster() 
    {
        /** check if findPlaceMonster is out of space. (should return -1) */
        assertEquals(null, board.findPlaceMonster("Jojo"));
    }
}
