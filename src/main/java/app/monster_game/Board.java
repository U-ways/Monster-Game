package app.monster_game;

import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A board that you can place and manipulate monsters on.
 * The board is a grid made of the following:
 *
 * - Hedges: No monster can be placed on a hedge.
 * - Stars:  Empty spots; you can place monsters on any star.
 *
 * A monster is represented by its name first letter. (i.e. A monster named
 * "Adam" will be "A" on the board)
 *
 * @author  Uways Eid
 * @version 31/10/2018
 */
public class Board
{
    private final int Y, X;
    private static final char EDGE  = '=';
    private static final char EMPTY = '*';

    private char board      [][];
    /** An array made up of active monsters on board */
    private String monstArr [];
    /** A List of all empty spots available */
    private LinkedList<Integer> emptySpotList;

    /**
     * Construct the initial board:
     * - Create the board's edges and empty spots
     * - Initialise empty spots list and add empty spots available
     * - Initialise active monsters array
     *
     * @param int Y   height of board
     * @param int X   width  of board
     */
    public Board(int Y, int X)
    {
        this.Y = Y;
        this.X = X;
        board  = new char [Y][X];

        /** Initialise Array size to be of board's empty spots total. */
        monstArr      = new String [(Y-2)*(X-2)];
        emptySpotList = new LinkedList<>();

        /** Create empty spots ("*") */
        for (int i = 1, ii = 0; i <= Y-2; i++) {
            for (int j = 1; j <= X-2; j++, ii++) {
                board[i][j] = EMPTY;

                /**
                 * Add the linear index of each empty spot.
                 * See algorithm documentation for details.
                 */
                emptySpotList.add(ii);
            }
        }

        /** Create Y edges ("=") */
        for (int i = 0; i < Y; i++) {
            board[i][0]   = EDGE;
            board[i][X-1] = EDGE;
        }

        /** Create X edges ("=") */
        for (int i = 0; i < X; i++) {
            board[0][i]   = EDGE;
            board[Y-1][i] = EDGE;
        }
    }

    /**
     * Construct the initial board with the default size 5*5.
     *
     * @see #Board(int Y, int X)
     */
    public Board()
    {
        this(5,5);
    }
    
    /**
     *  Prints a text-based display of board.
     */
    public void viewBoard()
    {
        for (int i = 0; i <= Y-1; i++) {
            for (int j = 0; j <= X-1; j++) System.out.print(board[i][j]);
            System.out.println();
        }
    }

    /**
     * Randomly allocates a grid position for a monster if an empty spot
     * available.
     *
     * @param  String name   Monster's name
     * @return int[]         null if no spots available, the grid position otherwise.
     */
    public int[] findPlaceMonster(String name)
    {
        int emptySpots = emptySpotList.size();

        /** Return null if no empty spots left */
        if (emptySpots == 0) {
            System.out.println("Unable to add Monster: No empty spots available...");
            return null;
        }

        /** get a random index from empty spot list */
        int rndIndex = ThreadLocalRandom.current().nextInt(emptySpotList.size());
        int spot     = emptySpotList.get(rndIndex);

        /** remove active spot from empty spot list */
        emptySpotList.remove(rndIndex);

        /** update active monster array with new active monster */
        monstArr[spot] = name;

        /** Number of valid monster spots in a single row */
        int rowLen = X-2;

        /**
         * Find the y-coordinates current monster spot.
         * See algorithm documentation for details.
         */
        int yAxis = (spot + (rowLen-(spot%rowLen))) / rowLen;
        
        /**
         * Find the x-coordinates of current monster spot.
         * See algorithm documentation for details.
         */
        int xAxis =  spot - ((yAxis-1)*rowLen) + 1;

        /** Replace empty spot with monster's name first letter */
        board[yAxis][xAxis] = name.charAt(0);

        return new int[]{yAxis,xAxis};
    }

    /**
     * Launch an attack against other monsters within the attacking
     * monster range. Every successful monster elimination will increase the
     * number returned by one.
     * 
     * @exception assert     pos is not an EMPTY spot.
     * @param  int[] pos     Monster's attacking position; aggressor's position.
     * @return int           The number of monsters eliminated in this attack.
     */
    public int launchAttack(int[] pos) 
    {
        assert board[pos[0]][pos[1]] != EMPTY : "Unable to launch attack: monster is dead.";
        
        /** monster attack range: { north, south, west, east } */
        int[][] atkRange = new int[][] {
            {pos[0]-1,pos[1]}, {pos[0]+1,pos[1]}, {pos[0],pos[1]-1}, {pos[0],pos[1]+1}
        };

        /** How many monsters were successfully attacked in this round */
        int eliminations = 0;

        /** Attempt to attack all cardinal directions within the monster's range */
        for (int i = 0; i < atkRange.length; i++) {
            /** Current cardinal direction to attack */
            int[] atkDir = atkRange[i];

            /** Value of spot to be attacked */
            char atkSpot = board[atkDir[0]][atkDir[1]];
            /** Skip spot if it is an EDGE or EMPTY */
            if (atkSpot == EDGE || atkSpot == EMPTY) continue;

            /**
             * Find casualty-to-be location.
             * See algorithm documentation for details.
             */
            int casualtyLocation = ((atkDir[0]-1)*(X-2))+(atkDir[1]-1);

            /** Remove dead monster from active monsters array */
            monstArr[casualtyLocation] = null;

            /** Add dead monster's location to empty spot list */
            emptySpotList.add(casualtyLocation);
            /** remove dead monster's initial from board */
            board[atkDir[0]][atkDir[1]]= EMPTY;

            /** increase monster score by 1 */
            eliminations++;
        }

        return eliminations;
    }

    /**
     * Get the array of active monsters.
     *
     * @return String[]   Array of active monsters.
     */
    public String[] getMonstArr()
    {
        return monstArr;
    }
}
