package chapman.edu.serversideapp;

/**
 * Created by abigailatchison on 5/4/17.
 */
public class GameBoard{

    private int[][] gameState;
    private boolean playerTurn;

    GameBoard() //game board
    {
        gameState = new int[][]
                {
                        {1, 0, 0, 0, 0, 0, 0},
                        {1, 1, 1, 0, 0, 0, 0},
                        {1, 1, 1, 1, 1, 0, 0},
                        {1, 1, 1, 1, 1, 1, 1},
                };

        playerTurn = true;
    }

    public int[][] getBoard() //returns the board
    {
        return gameState;
    }

    public boolean getPlayerTurn() //returns which player's turn it is
    {
        return playerTurn;
    }

    public void setBoard(int[][] given)
    {
        gameState = given;
    }

    public void printBoard() //actually prints out the board using a double nested for loop
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                System.out.print(gameState[i][j]);
            }
            System.out.println("\n");
        }

        if(this.playerTurn) //after it prints out hte board it prints out whichever player's turn it is (should always be player 1)
            System.out.println("Player 1's turn");
        else
            System.out.println("Player 2's turn");
    }

    public boolean validateMove(int row, int count) //checks if the inputted move is valid with the board
    {
        if(count>7) //if the second input is greater than 7 (past the length of the board) break out and return false
            return false;
        for(int i=0; i<count; ++i) //checks to make sure that the row is not full of 0's
        {
            if(gameState[row][i]==0)
                return false;
        }
        return true;
    }

    public void makeMove(int row, int count)//if valid then go to the row and change the 1's to 0's depending on what the second input is
    {
        //makes move
        for(int i=0; i<count; ++i)
        {
            gameState[row][i] = 0;
        }

        //switch playerTurn
        playerTurn = !playerTurn;
        adjustBoard(row, count);
    }

    private void adjustBoard(int row, int position) //when sticks are removed, move the remaining sticks to the left
    {
        if(position==7)
            return;
        if(gameState[row][position]==1)
        {
            int count = 0;
            System.out.print(position);
            for(int i = position-1; position<gameState[row].length; ++i)
            {
                if(i == 7)
                    break;
                if(gameState[row][i]==1) //goes stick by stick and moves them to the left 1
                {
                    gameState[row][count] = 1;
                    gameState[row][i] = 0;
                    count++;
                }

            }
        }
    }

    public boolean checkWin() //checks if the board is all 0's or not
    {
        for(int r=0; r<gameState.length; ++r)
        {
            for(int c=0; c<gameState[0].length; ++c)
            {
                if(gameState[r][c]==1) //if not then return false and continue
                    return false;
            }
        }

        return true; //otherwise return true and end the game
    }
}
