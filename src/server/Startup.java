package server;

import external.Console;

/**
 * Created by Owner on 5/18/2017.
 */
public class Startup
{

    public static final long y = 1495142569471l;
    private static Server server = new Server();
    public static void main(String[] args)
    {
        /*try
        {
            Runtime.getRuntime().exec("C:\\Sandbox\\SandboxServer\\out\\artifacts\\SandboxServer_jar\\game.exe");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/
        Console.log("Server", "Starting server...");
        server.start();
    }

}
