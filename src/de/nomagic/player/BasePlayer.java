package de.nomagic.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.clientlib.DataReply;
import de.nomagic.clientlib.RequestMatchDataCommand;
import de.nomagic.clientlib.ServerConnection;
import de.nomagic.clientlib.ServerReply;

public abstract class BasePlayer implements Player
{
    private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public boolean canPlay(String gameType)
    {
        if(null == gameType)
        {
            return false;
        }
        String[] playableGameTypes = getPlayableGameTypes();
        for(int i = 0; i < playableGameTypes.length; i++)
        {
            if(true == gameType.equals(playableGameTypes[i]))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void playOneTurn(String gameType, String matchName, ServerConnection con)
    {
        if(false == canPlay(gameType))
        {
            log.error("Asked to make a move on unsupported gametype({}) !", gameType);
            return;
        }
        ServerReply rep = con.sendCommand(new RequestMatchDataCommand(gameType, matchName));
        if(!(rep instanceof DataReply))
        {
            log.error("Asked to make a move on invalid match({}) !", matchName);
            return;
        }
        else
        {
            DataReply dr = (DataReply) rep;
            makeMoveOn(gameType, matchName, dr, con);
        }
    }

    protected abstract String[] getPlayableGameTypes();
    protected abstract void makeMoveOn(String gameType, String matchName,
                                       DataReply MatchData, ServerConnection con);

}
