package app.monster_game;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Basic tests for monster class.
 */
public class MonsterTest
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
    
    public MonsterTest()
    {
    }

    @Before
    public void setUp()
    {
        board = new Board(5,5);
        
        // Create 9 monsters (Max limit of 5x5 board)
        s1 = new Monster("Sigmundur", board);
        s2 = new Monster("Salman",    board);
        k1 = new Monster("Kim",       board);
        k2 = new Monster("Khalifa",   board);
        p1 = new Monster("Petro",     board);
        n1 = new Monster("Nawaz",     board);
        a1 = new Monster("Asif",      board);
        v1 = new Monster("Vladimir",  board);
        m1 = new Monster("Mojo",      board);
    }

    @After
    public void tearDown()
    {
        board.viewBoard();
    }
    
    @Test
    public void testAddMonster() {
        // add 8 monsters to board
        s1.addMonster();
        s2.addMonster();
        k1.addMonster();
        k2.addMonster();
        p1.addMonster();
        n1.addMonster();
        a1.addMonster();
        v1.addMonster();
        
        // Add the 9th monster to board (Max limit of 5x5 board)
        assertTrue(m1.addMonster());
        
        board.viewBoard();
        
        // Try overloading the board
        Monster x1 = new Monster("X", board);
        assertFalse(x1.addMonster());
    }
    
    @Test
    public void testGetMonsterPosition() {
        // check monster postion is empty on start
        assertNull(s1.getMonsterPosition());
        
        s1.addMonster();
        
        // check if monster returns location
        assertTrue(s1.getMonsterPosition() instanceof int[]);
    }
    
    @Test
    public void testAttackMonster() {
        // add 9 monsters to board (Max limit of 5x5 board)
        s1.addMonster();
        s2.addMonster();
        k1.addMonster();
        k2.addMonster();
        p1.addMonster();
        n1.addMonster();
        a1.addMonster();
        v1.addMonster();
        m1.addMonster();
        
        // attack all monsters within p1 range
        assertTrue(p1.attackMonster());
        
        // attack again (should return false as all monsters within p1 are dead)
        assertFalse(p1.attackMonster());
    }
    
    @Test
    public void testGetScore() {
        // add 9 monsters to board (Max limit of 5x5 board)
        s1.addMonster();
        s2.addMonster();
        k1.addMonster();
        k2.addMonster();
        p1.addMonster();
        n1.addMonster();
        a1.addMonster();
        v1.addMonster();
        m1.addMonster();
        
        // Check p1 score is 0 at the start
        assertEquals(0, p1.getScore());
        
        p1.attackMonster();
        
        // check if p1 score increased
        assertTrue(p1.getScore() > 0);
    }
}