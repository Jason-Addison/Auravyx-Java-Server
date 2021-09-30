package packets.connection;

import server.DataType;
import server.Packet;
import util.Util;

/**
 * Created by Owner on 5/19/2017.
 */
public class TestPacket
{

    static byte[] format = new byte[] {DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN, DataType.BOOLEAN};
    public TestPacket()
    {

    }

    public static void read(byte[] data)
    {
        Object[] objects = Packet.convertData(data, format);
        Util.printAllObjects(objects);
    }


}
