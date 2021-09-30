package server;

import external.Console;
import packets.connection.DisconnectPacket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Owner on 5/18/2017.
 */
public class Server
{
    public static final String IP_ADDRESS = "localhost";
    public static final int PORT = 3456;

    public static final String dir = "C:/Sandbox/SandboxServer/";
    //public static DatagramSocket socket;
    public static DatagramChannel channel;

    private boolean alive = true;

    public static HashMap<String, Object> settings = new HashMap<>();
    static HashMap<Short, Client> players = new HashMap<>();
    static HashMap<String, Short> playersByUsername = new HashMap<>();
    static HashMap<Short, Short> playersByPort = new HashMap<>();

    Console console = new Console();
    public Server()
    {
        settings.put("MAX_PING", 100);
    }

    public void start()
    {
        create();
        loop();
    }

    public void create()
    {
        InetSocketAddress socketAddress = null;
        try
        {
             channel = DatagramChannel.open();
             channel.configureBlocking(false);
             channel.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), PORT));
             channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
             socketAddress = (InetSocketAddress) channel.getLocalAddress();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Console.log("Server", "Setup complete. [Host] " + socketAddress.getAddress().getCanonicalHostName() + " [IP] " + socketAddress.getHostString() + " [Port] " + socketAddress.getPort());
    }

    public void loop()
    {
        while(alive)
        {
            receivePackets();
            console.update();
            for(Short c : players.keySet())
            {
                players.get(c).update();
            }
        }
        kill();
    }

    public void kill()
    {
        alive = false;
        try
        {
            channel.disconnect();
        }
        catch (IOException e)
        {

        }
    }

    private void receivePackets()
    {
        while(true)
        {
            byte[] packetData = new byte[1024];
            ByteBuffer buf = ByteBuffer.wrap(packetData);
            //DatagramPacket packet = new DatagramPacket(packetData, packetData.length);
            //System.out.println(packet.getData() == null);
            SocketAddress sa = null;
            try
            {
                sa = channel.receive(buf);
                //channel.read(buf);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            if(sa != null)
            {
                InetSocketAddress address = (InetSocketAddress) (sa);
                Packet.determinePacketType(buf.array(), address);
                //System.out.println(ad.getAddress().getCanonicalHostName() + " " + ad.getPort());
            }
            else
            {
                break;
            }

        }
    }

    public static void kick(short playerID, String reason)
    {
        System.out.println(new String(reason.getBytes()));
        players.get(playerID).sendPacket(Packet.create(Packet.DISCONNECT, new Object[]{reason}, DisconnectPacket.FORMAT));

        Console.log("Disconnect", reason);
    }

    public static void kick(String username, String reason)
    {
        kick(playersByUsername.get(username), reason);
    }

    public static int getPlayerCount()
    {
        return players.size();
    }

    public static ArrayList<Client> getAllClientsAsList()
    {
        ArrayList<Client> clients = new ArrayList<>();
        clients.addAll(players.values());
        return clients;
    }
    public static Client getClient(short id)
    {
        return players.get(id);
    }
    public static Client getClientByName(String username)
    {
        return players.get(playersByUsername.get(username));
    }
    public static Client getClientByPort(short port)
    {
        return players.get(playersByPort.get(port));
    }
}
