package de.nomagic.player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.nomagic.clientlib.ChangeUserCommand;
import de.nomagic.clientlib.JoinMatchCommand;
import de.nomagic.clientlib.LoginCommand;
import de.nomagic.clientlib.LogoutCommand;
import de.nomagic.clientlib.MessageType;
import de.nomagic.clientlib.OkReply;
import de.nomagic.clientlib.ReceivedMessageReply;
import de.nomagic.clientlib.RegisterCommand;
import de.nomagic.clientlib.SendMessageCommand;
import de.nomagic.clientlib.ServerConnection;
import de.nomagic.clientlib.ServerReply;
import de.nomagic.clientlib.StartMatchCommand;
import de.nomagic.clientlib.libTools;

public class PlayerMain extends Thread
{
    private final Logger log = (Logger) LoggerFactory.getLogger(this.getClass().getName());
    private volatile boolean shouldRun = true;

    // configuration:
    private String ServerURL = "127.0.0.1";
    private int ServerPort = 4223;
    private Player player = null;

    private ServerConnection con = new ServerConnection();

    public PlayerMain()
    {
    }

    public static void main(String[] args)
    {
        PlayerMain m = new PlayerMain();
        m.startLogging(args);
        m.getConfigFromCommandLine(args);
        m.start();
    }

    private void startLogging(final String[] args)
    {
        int numOfV = 0;
        for(int i = 0; i < args.length; i++)
        {
            if(true == "-v".equals(args[i]))
            {
                numOfV ++;
            }
        }

        // configure Logging
        switch(numOfV)
        {
        case 0: libTools.setLogLevel("warn"); break;
        case 1: libTools.setLogLevel("debug");break;
        case 2:
        default:
            libTools.setLogLevel("trace");
            System.out.println("Build from " + getCommitID());
            break;
        }
    }

    public static String getCommitID()
    {
        try
        {
            final InputStream s = PlayerMain.class.getResourceAsStream("/commit-id");
            final BufferedReader in = new BufferedReader(new InputStreamReader(s));
            final String commitId = in.readLine();
            final String changes = in.readLine();
            if(null != changes)
            {
                if(0 < changes.length())
                {
                    return commitId + "-(" + changes + ")";
                }
                else
                {
                    return commitId;
                }
            }
            else
            {
                return commitId;
            }
        }
        catch( Exception e )
        {
            return e.toString();
        }
    }

    public void getConfigFromCommandLine(String[] args)
    {
        for(int i = 0; i < args.length; i++)
        {
            if(true == args[i].startsWith("-"))
            {
                if(true == "-host".equals(args[i]))
                {
                    i++;
                    ServerURL = args[i];
                }
                else if(true == "-player".equals(args[i]))
                {
                    i++;
                    String PlayerName = args[i];
                    switch(PlayerName)
                    {
                    case "Uwe":
                        player = new Uwe();
                        break;

                    case "Det":
                        player = new DetErministic();
                        break;

                    case "Joe":
                        player = new JoeRandom();
                        break;

                    default:
                        break;
                    }
                }
                else if(true == "-port".equals(args[i]))
                {
                    i++;
                    ServerPort = Integer.parseInt(args[i]);
                }
                else if(true == "-v".equals(args[i]))
                {
                    // ignored here -> parsed in startLogging()
                }
                else
                {
                    System.err.println("Invalid Parameter : " + args[i]);
                }
            }
        }
        if(null == player)
        {
            player = new JoeRandom();
        }
    }

    public void run()
    {
        // Connect to server
        con.connectTo(ServerURL, ServerPort);
        if(false == con.isConnected())
        {
            log.error("Could not connect to Server !");
            System.exit(1);
        }
        // login
        ServerReply rep = con.sendCommand(new LoginCommand(player.getUserName(), player.getUserPassword()));
        if( ! (rep instanceof OkReply))
        {
            // lets try to register with this server
            rep = con.sendCommand(new RegisterCommand(player.getUserName(), player.getUserPassword()));
            if( ! (rep instanceof OkReply))
            {
                log.error("Reply: " + rep.toString());
                log.error("Could not login !");
                System.exit(2);
            }
            else
            {
                rep = con.sendCommand(new LoginCommand(player.getUserName(), player.getUserPassword()));
                if( ! (rep instanceof OkReply))
                {
                    log.error("Reply: " + rep.toString());
                    log.error("Could not login !");
                    System.exit(2);
                }
            }
        }
        rep = con.sendCommand(new ChangeUserCommand(player.getUserName(), "plays Tic-Tac-Toe", true));
        rep = con.sendCommand(new ChangeUserCommand(player.getUserName(), "human", false));
        while(true == shouldRun)
        {
            rep = con.waitForMessage();
            if(rep instanceof ReceivedMessageReply)
            {
                ReceivedMessageReply msg = (ReceivedMessageReply)rep;
                MessageType type = msg.getMessageType();
                switch(type)
                {
                case INVITE:
                    String gameType = msg.getGameType();
                    if(true == player.canPlay(gameType))
                    {
                        // Join that match
                        rep = con.sendCommand(new JoinMatchCommand(gameType, msg.getMatchName()));
                        if( ! (rep instanceof OkReply))
                        {
                            rep = con.sendCommand(new SendMessageCommand(msg.getSender(), type, "I could not join that Match."));
                        }
                        // start the match
                        rep = con.sendCommand(new StartMatchCommand(gameType, msg.getMatchName()));
                        if( ! (rep instanceof OkReply))
                        {
                            rep = con.sendCommand(new SendMessageCommand(msg.getSender(), type, "I could not start that Match."));
                        }
                    }
                    else
                    {
                        rep = con.sendCommand(new SendMessageCommand(msg.getSender(), type, "Thank you for the Invite, but I don't play (" + gameType + ") games."));
                    }
                    break;

                case MATCH: // change in match configuration
                    // I don't care
                    break;

                case MOVE:
                    // make a move
                    player.playOneTurn(msg.getGameType(),  msg.getMatchName(), con);
                    break;

                case CHAT:
                    // fall through
                default:
                    log.error("Received Message of type _{}_ !", type);
                    log.error("Msg: {}", msg);
                    System.exit(25);
                    // rep = con.sendCommand(new SendMessageCommand(msg.getSender(), type, "I'm a software! I don't chat with humans!"));
                    break;
                }
            }
            else
            {
                log.error("Reply: " + rep.toString());
                log.error("Invalid Reply in this state !");
                shouldRun = false;
            }
            if(interrupted())
            {
                shouldRun = false;
            }
        }
        // logout
        rep = con.sendCommand(new LogoutCommand());
        // I don't care if the logout worked
        // close connection
        con.disconnect();
        log.info("Done !");
    }

}
