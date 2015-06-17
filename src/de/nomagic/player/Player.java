package de.nomagic.player;

import de.nomagic.clientlib.ServerConnection;

public interface Player
{
    boolean canPlay(String gameType);
    void playOneTurn(String gameType, String matchName, ServerConnection con);
    String getUserPassword();
    String getUserName();
}
