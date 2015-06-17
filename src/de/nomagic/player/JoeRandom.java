package de.nomagic.player;

import java.util.Random;

import de.nomagic.clientlib.DataReply;
import de.nomagic.clientlib.ServerConnection;
import de.nomagic.clientlib.TicTacToeWorld;

public class JoeRandom extends BasePlayer
{
    protected String[] playableGameTypes = {"Tic-Tac-Toe"};
    private Random r = new Random();

    public JoeRandom()
    {

    }

    @Override
    protected String[] getPlayableGameTypes()
    {
        return playableGameTypes;
    }

    @Override
    protected void makeMoveOn(String gameType,
                              String matchName,
                              DataReply MatchData,
                              ServerConnection con)
    {
        TicTacToeWorld world = new TicTacToeWorld(matchName, MatchData, getUserName());

        for(int i = 0; i < 20; i++)
        {
            int x = r.nextInt(3);
            int y = r.nextInt(3);
            if(true == world.isempty(x,y))
            {
                con.sendCommand(world.getMoveCommandFor(x, y));
                return;
            }
        }
        // should get here only very seldom
        for(int x = 0; x < 3; x++)
        {
            for(int y = 0; y < 3; y++)
            {
                if(true == world.isempty(x,y))
                {
                    con.sendCommand(world.getMoveCommandFor(x, y));
                    return;
                }
            }
        }
    }

    @Override
    public String getUserPassword()
    {
        return "random";
    }

    @Override
    public String getUserName()
    {
        return "Joe";
    }
}
