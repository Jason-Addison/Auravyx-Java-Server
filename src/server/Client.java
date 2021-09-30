package server;

import client.Ping;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Created by Owner on 5/18/2017.
 */
public class Client
{

    InetSocketAddress address;
    Ping ping = new Ping();
    String username;
    public Client(InetSocketAddress address, String username)
    {
        this.address = address;
        this.username = username;
        sendPacket("yo".getBytes());
    }

    public void update()
    {
        ping.ping();
        receive();
    }
    public void receive()
    {

    }

    public int getPort()
    {
        return address.getPort();
    }

    public String getIP()
    {
        return address.getHostString();
    }

    public String getUsername()
    {
        return username;
    }

    public void sendPacket(byte[] packetData)
    {

        ByteBuffer buf = ByteBuffer.wrap(packetData);
        try
        {
            Server.channel.send(buf, address);
        }
        catch (IOException i)
        {
            System.err.println("Failed to send packet to client : " + address.getHostName() + " " + address.getPort());
        }
    }

}
