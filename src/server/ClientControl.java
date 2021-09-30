package server;

import java.util.ArrayList;

/**
 * Created by Owner on 5/21/2017.
 */
public class ClientControl
{
    static ArrayList<Short> queue = new ArrayList<>();
    static short index = 0;
    public static void addClient(Client c)
    {
        short id = getNextTempID();
        Server.players.put(id, c);
        Server.playersByUsername.put(c.getUsername(), (short) id);
        Server.playersByPort.put((short) c.getPort(), (short) id);
        index++;
    }

    public static void removeClient(short id)
    {
        queue.add(Short.valueOf(id));
        Server.players.remove(id + "");
        Server.playersByUsername.remove(Server.players.get(id).getUsername());
        Server.playersByPort.remove(Server.players.get(id).getPort());
    }

    private static short getNextTempID()
    {
        if(queue.isEmpty())
        {
            return index;
        }
        else
        {
            return queue.get(0);
        }
    }
}
