package app.monster_game;

/**
 * A monster that can be placed on a board and is aggressive to its cardinal
 * directions. The more harmful a monster is, the higher it scores. In other
 * words, successful monster attacks increases the monster's score.
 *
 * @author  Uways Eid
 * @version 31/10/2018
 */
public class Monster
{
    private int    score;
    private int[]  position;
    private String name;
    private Board  board;

    /**
     * Creates a monster and binds it to a board.
     *
     * @param String name     The name of the monster.
     * @param Board  board    The board the monster is binded to.
     */
    public Monster(String name, Board board)
    {
        this.name  = name;
        this.board = board;
        score = 0;
    }

    /**
     * Add monster to board.
     * NOTE: If board is full, position will be null.
     *
     * @exception assert   position is null
     * @return boolean     true if monster is placed on board, false otherwise.s
     */
    public boolean addMonster()
    {
        assert position == null : "Monster already added to the board";
        position = board.findPlaceMonster(name);
        return position == null ? false : true;
    }

    /**
     * Get monster's position
     *
     * @return int[]    monster's position
     */
    public int[] getMonsterPosition()
    {
        return position;
    }

    /**
     * Get monster's score
     *
     * @return int    monster's score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Get monster's name
     *
     * @return String    monster's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Attack other monsters. Every successful monster elimination will
     * increase the monster score by one.
     *
     * @exception assert   position is not null.
     * @return boolean     true if attack was successful, false otherwise.
     */
    public boolean attackMonster()
    {
        assert position != null : "Monster is not on board";

        /**
         * Initiate attack sequence.
         *
         * @see Board#launchAttack(int[] position)
         */
        int eliminations = board.launchAttack(position);

        if (eliminations == 0) return false;

        System.out.println(name + " eliminated " + eliminations + " monsters in this round.");

        score += eliminations;
        return true;
    }
}
