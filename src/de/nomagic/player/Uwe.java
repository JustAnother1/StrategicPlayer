package de.nomagic.player;

import de.nomagic.clientlib.DataReply;
import de.nomagic.clientlib.ServerConnection;
import de.nomagic.clientlib.TicTacToeWorld;

public class Uwe extends BasePlayer
{
    protected String[] playableGameTypes = {"Tic-Tac-Toe"};

    public Uwe()
    {

    }

    @Override
    protected String[] getPlayableGameTypes()
    {
        return playableGameTypes;
    }

    @Override
    protected void makeMoveOn(String gameType, String matchName,
                              DataReply MatchData, ServerConnection con)
    {
        TicTacToeWorld v = new TicTacToeWorld(matchName, MatchData, getUserName());
        // can I win ?
        for(int i = 0; i < 3; i++)
        {
            // horizontally
            if( (true == v.isTakenByMe(0,i)) && (true == v.isTakenByMe(1,i)) )
            {
                if(true == v.isempty(2, i))
                {
                    con.sendCommand(v.getMoveCommandFor(2, i));
                    return;
                }
            }
            if( (true == v.isTakenByMe(2,i)) && (true == v.isTakenByMe(1,i)) )
            {
                if(true == v.isempty(0, i))
                {
                    con.sendCommand(v.getMoveCommandFor(0, i));
                    return;
                }
            }
            if( (true == v.isTakenByMe(0,i)) && (true == v.isTakenByMe(2,i)) )
            {
                if(true == v.isempty(1, i))
                {
                    con.sendCommand(v.getMoveCommandFor(1, i));
                    return;
                }
            }
            // vertically
            if( (true == v.isTakenByMe(i,0)) && (true == v.isTakenByMe(i,1)) )
            {
                if(true == v.isempty(i, 2))
                {
                    con.sendCommand(v.getMoveCommandFor(i, 2));
                    return;
                }
            }
            if( (true == v.isTakenByMe(i,2)) && (true == v.isTakenByMe(i,1)) )
            {
                if(true == v.isempty(i, 0))
                {
                    con.sendCommand(v.getMoveCommandFor(i, 0));
                    return;
                }
            }
            if( (true == v.isTakenByMe(i,0)) && (true == v.isTakenByMe(i,2)) )
            {
                if(true == v.isempty(i, 1))
                {
                    con.sendCommand(v.getMoveCommandFor(i, 1));
                    return;
                }
            }
        }
        // diagonally
        if(true == v.isTakenByMe(1,1))
        {
            if(true == v.isTakenByMe(0,0))
            {
                if(true == v.isempty(2, 2))
                {
                    con.sendCommand(v.getMoveCommandFor(2, 2));
                    return;
                }
            }
            if(true == v.isTakenByMe(2,0))
            {
                if(true == v.isempty(0, 2))
                {
                    con.sendCommand(v.getMoveCommandFor(0, 2));
                    return;
                }
            }
            if(true == v.isTakenByMe(0,2))
            {
                if(true == v.isempty(2, 0))
                {
                    con.sendCommand(v.getMoveCommandFor(2, 0));
                    return;
                }
            }
            if(true == v.isTakenByMe(2,2))
            {
                if(true == v.isempty(0, 0))
                {
                    con.sendCommand(v.getMoveCommandFor(0, 0));
                    return;
                }
            }
        }
        else
        {
            // center field is not mine, so can not win in this move
        }

        // Don't let the enemy win
        for(int i = 0; i < 3; i++)
        {
            // horizontally
            if( (true == v.isTakenByOpponent(0,i)) && (true == v.isTakenByOpponent(1,i)) )
            {
                if(true == v.isempty(2, i))
                {
                    con.sendCommand(v.getMoveCommandFor(2, i));
                    return;
                }
            }
            if( (true == v.isTakenByOpponent(2,i)) && (true == v.isTakenByOpponent(1,i)) )
            {
                if(true == v.isempty(0, i))
                {
                    con.sendCommand(v.getMoveCommandFor(0, i));
                    return;
                }
            }
            if( (true == v.isTakenByOpponent(0,i)) && (true == v.isTakenByOpponent(2,i)) )
            {
                if(true == v.isempty(1, i))
                {
                    con.sendCommand(v.getMoveCommandFor(1, i));
                    return;
                }
            }
            // vertically
            if( (true == v.isTakenByOpponent(i,0)) && (true == v.isTakenByOpponent(i,1)) )
            {
                if(true == v.isempty(i, 2))
                {
                    con.sendCommand(v.getMoveCommandFor(i, 2));
                    return;
                }
            }
            if( (true == v.isTakenByOpponent(i,2)) && (true == v.isTakenByOpponent(i,1)) )
            {
                if(true == v.isempty(i, 0))
                {
                    con.sendCommand(v.getMoveCommandFor(i, 0));
                    return;
                }
            }
            if( (true == v.isTakenByOpponent(i,0)) && (true == v.isTakenByOpponent(i,2)) )
            {
                if(true == v.isempty(i, 1))
                {
                    con.sendCommand(v.getMoveCommandFor(i, 1));
                    return;
                }
            }
        }
        // diagonally
        if(true == v.isTakenByOpponent(1,1))
        {
            if(true == v.isTakenByOpponent(0,0))
            {
                if(true == v.isempty(2, 2))
                {
                    con.sendCommand(v.getMoveCommandFor(2, 2));
                    return;
                }
            }
            if(true == v.isTakenByOpponent(2,0))
            {
                if(true == v.isempty(0, 2))
                {
                    con.sendCommand(v.getMoveCommandFor(0, 2));
                    return;
                }
            }
            if(true == v.isTakenByOpponent(0,2))
            {
                if(true == v.isempty(2, 0))
                {
                    con.sendCommand(v.getMoveCommandFor(2, 0));
                    return;
                }
            }
            if(true == v.isTakenByOpponent(2,2))
            {
                if(true == v.isempty(0, 0))
                {
                    con.sendCommand(v.getMoveCommandFor(0, 0));
                    return;
                }
            }
        }
        else
        {
            // I will take the center field anyway if I can not win with this move
        }

        if(   (true == v.isempty(0, 0))
           && (true == v.isempty(0, 1))
           && (true == v.isempty(0, 2))
           && (true == v.isempty(1, 0))
           && (true == v.isempty(1, 1))
           && (true == v.isempty(1, 2))
           && (true == v.isempty(2, 0))
           && (true == v.isempty(2, 1))
           && (true == v.isempty(2, 2)) )
        {
            // First move -> go to one corner
            con.sendCommand(v.getMoveCommandFor(0, 0));
            return;
        }

        // take the center field
        if(true == v.isempty(1, 1))
        {
             con.sendCommand(v.getMoveCommandFor(1, 1));
             return;
        }

        // special case
        if(true == v.isTakenByMe(1,1))
        {
            if(   (true == v.isTakenByOpponent(0,0)) && (true == v.isTakenByOpponent(2,2)) )
            {
                if( (true == v.isempty(2, 0)) && (true == v.isempty(0, 2)) )
                {
                    if((true == v.isempty(0, 1)) && (true == v.isempty(2, 1)))
                    {
                        con.sendCommand(v.getMoveCommandFor(2, 1));
                        return;
                    }
                    if((true == v.isempty(1, 0)) && (true == v.isempty(1, 2)))
                    {
                        con.sendCommand(v.getMoveCommandFor(1, 2));
                        return;
                    }
                }
            }
            if( (true == v.isTakenByOpponent(2,0)) && (true == v.isTakenByOpponent(0,2)) )
             {
                if( (true == v.isempty(0, 0)) && (true == v.isempty(2, 2)) )
                {
                    if((true == v.isempty(0, 1)) && (true == v.isempty(2, 1)))
                    {
                        con.sendCommand(v.getMoveCommandFor(2, 1));
                        return;
                    }
                    if((true == v.isempty(1, 0)) && (true == v.isempty(1, 2)))
                    {
                        con.sendCommand(v.getMoveCommandFor(1, 2));
                        return;
                    }
                }
             }
        }

        // prepare a win
        if(true == v.isTakenByMe(0,0))
        {
            if(true == v.isempty(2, 0))
            {
                con.sendCommand(v.getMoveCommandFor(2, 0));
                return;
            }
            else if(true == v.isempty(0, 2))
            {
                con.sendCommand(v.getMoveCommandFor(0, 2));
                return;
            }
        }

        if(true == v.isTakenByMe(2,0))
        {
            if(true == v.isempty(0, 0))
            {
                con.sendCommand(v.getMoveCommandFor(0, 0));
                return;
            }
            else if(true == v.isempty(2, 2))
            {
                con.sendCommand(v.getMoveCommandFor(2, 2));
                return;
            }
        }

        if(true == v.isTakenByMe(2,2))
        {
            if(true == v.isempty(2, 0))
            {
                con.sendCommand(v.getMoveCommandFor(2, 0));
                return;
            }
            else if(true == v.isempty(0, 2))
            {
                con.sendCommand(v.getMoveCommandFor(0, 2));
                return;
            }
        }

        if(true == v.isTakenByMe(0,2))
        {
            if(true == v.isempty(0, 0))
            {
                con.sendCommand(v.getMoveCommandFor(0, 0));
                return;
            }
            else if(true == v.isempty(2, 2))
            {
                con.sendCommand(v.getMoveCommandFor(2, 2));
                return;
            }
        }

        // prefer the corners
        if(true == v.isTakenByMe(1, 1))
        {
            // if I own the center prefer the corner that can create a win over the center
            if((true == v.isempty(0, 0)) && (true == v.isempty(2, 2)))
            {
                con.sendCommand(v.getMoveCommandFor(0, 0));
                return;
            }
            if((true == v.isempty(2, 0)) && (true == v.isempty(0, 2)))
            {
                con.sendCommand(v.getMoveCommandFor(2, 0));
                return;
            }
            if((true == v.isempty(0, 2)) && (true == v.isempty(2, 0)))
            {
                con.sendCommand(v.getMoveCommandFor(0, 2));
                return;
            }
            if((true == v.isempty(2, 2)) && (true == v.isempty(0, 0)))
            {
                con.sendCommand(v.getMoveCommandFor(2, 2));
                return;
            }
        }
        else
        {
            // prefer corners that create two chance to win
            if(   (true == v.isempty(0, 0))
               && (true == v.isempty(2, 0))
               && (true == v.isempty(1, 0))
               && (true == v.isempty(0, 1))
               && (true == v.isempty(0, 2)) )
            {
                con.sendCommand(v.getMoveCommandFor(0, 0));
                return;
            }
            if(   (true == v.isempty(2, 0))
               && (true == v.isempty(0, 0))
               && (true == v.isempty(1, 0))
               && (true == v.isempty(2, 1))
               && (true == v.isempty(2, 2)) )
            {
                con.sendCommand(v.getMoveCommandFor(2, 0));
                return;
            }
            if(   (true == v.isempty(0, 2))
               && (true == v.isempty(0, 0))
               && (true == v.isempty(0, 1))
               && (true == v.isempty(1, 2))
               && (true == v.isempty(2, 2)) )
            {
                con.sendCommand(v.getMoveCommandFor(0, 2));
                return;
            }
            if(   (true == v.isempty(2, 2))
               && (true == v.isempty(2, 0))
               && (true == v.isempty(2, 1))
               && (true == v.isempty(1, 2))
               && (true == v.isempty(0, 2)) )
            {
                con.sendCommand(v.getMoveCommandFor(2, 2));
                return;
            }

            // If we do not have such a good position, then go for one chance
            if((true == v.isempty(0, 0)) && (true == v.isempty(0, 1)) && (true == v.isempty(0, 2)))
            {
                con.sendCommand(v.getMoveCommandFor(0, 0));
                return;
            }
            if((true == v.isempty(2, 0)) && (true == v.isempty(2, 1)) && (true == v.isempty(2, 2)))
            {
                con.sendCommand(v.getMoveCommandFor(2, 0));
                return;
            }
            if((true == v.isempty(0, 2)) && (true == v.isempty(1, 2))  && (true == v.isempty(2, 2)))
            {
                con.sendCommand(v.getMoveCommandFor(0, 2));
                return;
            }
            if((true == v.isempty(2, 2)) && (true == v.isempty(1, 2)) && (true == v.isempty(0, 2)))
            {
                con.sendCommand(v.getMoveCommandFor(2, 2));
                return;
            }

            if((true == v.isempty(0, 0)) && (true == v.isempty(1, 0)) && (true == v.isempty(2, 0)))
            {
                con.sendCommand(v.getMoveCommandFor(0, 0));
                return;
            }
            if((true == v.isempty(2, 0)) && (true == v.isempty(1, 0)) && (true == v.isempty(0, 0)))
            {
                con.sendCommand(v.getMoveCommandFor(2, 0));
                return;
            }
            if((true == v.isempty(0, 2)) && (true == v.isempty(0, 1)) && (true == v.isempty(0, 0)))
            {
                con.sendCommand(v.getMoveCommandFor(0, 2));
                return;
            }
            if((true == v.isempty(2, 2)) && (true == v.isempty(2, 1)) && (true == v.isempty(2, 0)))
            {
                con.sendCommand(v.getMoveCommandFor(2, 2));
                return;
            }
        }

        if(true == v.isempty(0, 0))
        {
            con.sendCommand(v.getMoveCommandFor(0, 0));
            return;
        }
        if(true == v.isempty(2, 0))
        {
            con.sendCommand(v.getMoveCommandFor(2, 0));
            return;
        }
        if(true == v.isempty(0, 2))
        {
            con.sendCommand(v.getMoveCommandFor(0, 2));
            return;
        }
        if(true == v.isempty(2, 2))
        {
            con.sendCommand(v.getMoveCommandFor(2, 2));
            return;
        }

        // catch all
        for(int x = 0; x < 3; x++)
        {
            for(int y = 0; y < 3; y++)
            {
                if(true == v.isempty(x, y))
                {
                    con.sendCommand(v.getMoveCommandFor(x, y));
                    return;
                }
            }
        }
        return;
    }

    @Override
    public String getUserPassword()
    {
        return "neverloose";
    }

    @Override
    public String getUserName()
    {
        return "Uwe";
    }
}
