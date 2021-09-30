package command;

import external.Console;
import packets.interaction.communication.GlobalMessagePacket;
import server.Client;
import server.Packet;
import server.Server;

import java.util.ArrayList;

/**
 * Created by Owner on 5/21/2017.
 */
public class Command
{

    public static void execute(String command)
    {
        if(!command.isEmpty())
        {
            if(command.startsWith("/"))
            {

            }
            else
            {
                Console.log("Server", command);
                ArrayList<Client> clients = Server.getAllClientsAsList();
                for(Client c : clients)
                {
                    c.sendPacket(Packet.create(Packet.GLOBAL_MESSAGE, new Object[]{command}, GlobalMessagePacket.format));
                }
            }
        }
    }

}
