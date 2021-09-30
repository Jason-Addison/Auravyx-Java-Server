package client;

import server.Server;

/**
 * Created by Owner on 5/21/2017.
 */
public class Ping
{

    long timeAtPing = System.currentTimeMillis();
    boolean needToPing = true;
    public void ping()
    {
        if(needToPing)
        {
            needToPing = false;
            timeAtPing = System.currentTimeMillis();
        }
        if(System.currentTimeMillis() >= timeAtPing + (int) (Server.settings.get("MAX_PING")))
        {

        }
    }

    private void sendPing()
    {

    }

}
