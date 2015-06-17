package de.nomagic.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.clientlib.DataReply;
import de.nomagic.clientlib.ServerConnection;
import de.nomagic.clientlib.TicTacToeWorld;

public class DetErministic extends BasePlayer
{
    private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass().getName());

    protected String[] playableGameTypes = {"Tic-Tac-Toe"};

    public DetErministic()
    {

    }

    @Override
    protected void makeMoveOn(String gameType,
                              String matchName,
                              DataReply MatchData,
                              ServerConnection con)
    {
        TicTacToeWorld world = new TicTacToeWorld(matchName, MatchData, getUserName());
        for(int x = 0; x < 3; x++)
        {
            for(int y = 0; y < 3; y++)
            {
                if(true == world.isempty(x,y))
                {
                    log.trace("Making move {},{} !", x, y);
                    con.sendCommand(world.getMoveCommandFor(x, y));
                    return;
                }
            }
        }
        log.error("Could not move all fields taken !");
        return;
    }

    @Override
    public String getUserPassword()
    {
        return "allwaysthesame";
    }

    @Override
    public String getUserName()
    {
        return "Det";
    }

    @Override
    protected String[] getPlayableGameTypes()
    {
        return playableGameTypes;
    }

}
