package packets.connection;

import external.Console;
import server.*;

import java.net.InetSocketAddress;

/**
 * Created by Owner on 5/21/2017.
 */
public class ConnectionPacket
{
    static byte[] format = {DataType.STRING};

    /*

        String - Username

     */

    public static void read(byte[] data, InetSocketAddress address)
    {
        Object[] connectionObjects = Packet.convertData(data, format);
        String playerUsername = (String) connectionObjects[0] ;
        String ip = address.getHostString();
        int port = address.getPort();
        Console.log("<Connection> : Player \"" + playerUsername + "\" connected from [IP] " + ip + " [Port] " + port);
        ClientControl.addClient(new Client(address, playerUsername));
        Server.kick(playerUsername, "ur bad xddd");
    }
}
