package app.monster_game;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Main class for the "Monster Game" application. This class provides a text-
 * based user interface and keeps track of all active components in the
 * application.
 *
 * @author  Uways Eid
 * @version 31/10/2018
 */
public class Main
{
    private Board board;
    private Scanner reader;
    private boolean finished;

    /** A map of all monsters created */
    private Map<String, Monster> monstersMap;

    /**
     * Program's main method (entry point)
     *
     * @see #GameMain(int Y, int X)
     */
    public static void main(String[] args)
    {
        new Main(5,5);
    }

    /**
     * Initialise the "Monster Game" application.
     * It will also display the UI and start the game.
     *
     * @see   Board(Y,X)
     * @param int Y   height of board
     * @param int X   width  of board
     * @return
     */
    public Main(int Y, int X)
    {
        board = new Board(Y,X);
        monstersMap = new HashMap<>();

        this.opt3_ViewBoard();

        System.out.println(
          "\nPlease choose an action from the menu:\n"
          + "1. Enter a monster onto the board. Format: 1 {NEW_MONSTER_NAME}\n"
          + "2. Launch an attack on a monster.  Format: 2 {AGGRESSOR_NAME}\n"
          + "3. View the board.                 Format  3\n"
          + "4. Retrieve a score of a monster.  Format: 4 {MONSTER_NAME}\n"
          + "5. List all players alive.         Format: 5\n"
          + "6. Quit game.                      Format: (6|q)"
        );

        this.play();
    }

    /**
     * Construct a new game with default board dimensions (5*5).
     *
     * @see #GameMain(int Y, int X)
     */
    public Main() {
        this(5,5);
    }

    /**
     * A parser to read and process user input.
     *
     * Every time it is called it reads a line from the terminal and
     * tries to interpret the line as a one or two-word command.
     */
    private void processOption ()
    {
        String inputLine;
        String option = null;

        System.out.print("\n> "); // prompt
        inputLine = reader.nextLine(); // get full line input

        Scanner tokenizer = new Scanner(inputLine); // create a tokenizer to process the input.

        if(tokenizer.hasNext()) {
            option = tokenizer.next();

            switch (option) {
                case "1":
                    if (tokenizer.hasNext())
                       this.opt1_CreateMonster(tokenizer.next());
                    else
                       System.out.println("Error: cannot create monster. Missing input: monster name");
                break;
                case "2":
                    if (tokenizer.hasNext())
                       this.opt2_LaunchAttack(tokenizer.next());
                    else
                       System.out.println("Error: cannot launch attack. Missing input: monster name");
                break;
                case "3":
                    this.opt3_ViewBoard();
                break;
                case "4":
                    if (tokenizer.hasNext())
                       this.opt4_GetMonsterScore(tokenizer.next());
                    else
                       System.out.println("Error: cannot retrieve score. Missing input: monster name");
                break;
                case "5":
                    this.opt5_ListMonstersAlive();
                break;
                case "6":
                case "q":
                    this.opt6_Quit();
                break;
                default:
                    System.out.println("Sorry, I didn't recognise that last item.");
            }
        }

        tokenizer.close();
    }

    /**
     * Creates an event loop to accept and process user commands input.
     */
    public void play ()
    {
        finished = false;
        reader   = new Scanner(System.in);

        while (!finished) {
            this.processOption();
        }

        reader.close();
    }

    /**
     * Creates a new monster and add it to board.
     * If board is full the monster won't be added and will be discarded.
     *
     * @see #Monster
     * @param String name  The name of the monster.
     */
    public void opt1_CreateMonster(String name)
    {
        Monster m = new Monster(name, board);

        /** Try adding the monster to board */
        if (m.addMonster()) {
            int[] loc = m.getMonsterPosition();

            /** add new monster to map */
            monstersMap.put(name, m);

            System.out.println(
                "Created monster: " + name
             + " at: (" + loc[1] +"," + loc[0] + ")"
            );
        }
        else {
            m = null;
            System.out.println("Unable to create monster; Board is full..." );
        }
    }

    /**
     * Let monster launch attack on other monsters.
     *
     * @see   Monster#attackMonster()
     * @param String name  The name of the monster.
     */
    public void opt2_LaunchAttack(String name)
    {
        if (monstersMap.containsKey(name)) monstersMap.get(name).attackMonster();
        else                               System.out.println("The monster: " + name + " does not exist.");
    }

    /**
     * Print current game board.
     *
     * @see Board#viewBoard()
     */
    public void opt3_ViewBoard()
    {
        System.out.println();
        board.viewBoard();
    }

    /**
     * Print monster score.
     *
     * @see Monster#getScore()
     * @param String name  The name of the monster.
     */
    public void opt4_GetMonsterScore(String name)
    {
        int score = monstersMap.get(name).getScore();
        System.out.println("Monster: " + name + " - score: " + score );
    }

    /**
     * Print all active monsters on board.
     *
     * @see Board#getMonstArr()
     */
    public void opt5_ListMonstersAlive()
    {
        String[] monstArr = board.getMonstArr();

        if (monstArr == null) System.out.println("No monsters alive on board...");
        else {
            for (int i = 0; i < monstArr.length; i++) {
                String name = monstArr[i];
                if (name == null) continue;

                int[] spot  = monstersMap.get(name).getMonsterPosition();

                System.out.println(
                    "Monster: " + name + " - " +
                    "Located: (" + spot[1] +"," + spot[0] + ")"
                );
            }
        }
    }

    /**
     * End the game.
     */
    public void opt6_Quit()
    {
        finished = true;
    }
}
