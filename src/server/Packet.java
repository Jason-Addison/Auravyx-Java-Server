package server;

import packets.connection.ConnectionPacket;
import util.Util;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Owner on 5/18/2017.
 */
public class Packet
{

    public static final short UNUSED = 0;
    public static final short PLAYER_ID = 1;
    public static final short DISCONNECT = 2;


    public static final short GLOBAL_MESSAGE = 2000;

    public Packet()
    {

    }

    public static void processPacket(byte[] packetData)
    {

    }

    public static void determinePacketType(byte[] packetData, InetSocketAddress address)
    {
        byte[] byteID = {packetData[0], packetData[1]};
        short id = ByteBuffer.wrap(byteID).order(ByteOrder.LITTLE_ENDIAN).getShort();
        if(id == UNUSED)
        {

        }
        else if(id == PLAYER_ID)
        {
            ConnectionPacket.read(packetData, address);
        }
    }

    public static Object[] convertData(byte[] data, byte... dataFormat)
    {
        ArrayList<Object> objects = new ArrayList<>();
        short index = 2;
        short formatIndex = 0;
        while(true)
        {
            if(formatIndex >= dataFormat.length)
            {
                break;
            }
            if(dataFormat[formatIndex] == DataType.NULL)
            {
                System.out.println("place holder");
            }
            if(dataFormat[formatIndex] == DataType.BOOLEAN)
            {
                if(data[index] == 0)
                {
                    objects.add(false);
                }
                else
                {
                    objects.add(true);
                }
                index ++; // 0 or 1
                formatIndex++;
            }
            if(dataFormat[formatIndex] == DataType.STRING)
            {
                short size = Util.bytesToShort(data[index++], data[index++]);
                String string = new String(Arrays.copyOfRange(data, index, index + size));
                objects.add(string);
                index ++; // 0 or 1
                formatIndex++;
            }
        }
        return objects.toArray();
    }

    private static int determineFormatSize(byte[] format, Object[] objects)
    {
        int size = 0;
        int index = 0;
        for(byte b : format)
        {
            if(b == DataType.BOOLEAN)
            {
                size += 1;
            }
            if(b == DataType.BYTE)
            {
                size += Byte.SIZE / 8;
            }
            if(b == DataType.CHAR)
            {
                size += Character.SIZE / 8;
            }
            if(b == DataType.SHORT)
            {
                size += Short.SIZE / 8;
            }
            if(b == DataType.INTEGER)
            {
                size += Integer.SIZE / 8;
            }
            if(b == DataType.LONG)
            {
                size += Long.SIZE / 8;
            }
            if(b == DataType.FLOAT)
            {
                size += Float.SIZE / 8;
            }
            if(b == DataType.STRING)
            {
                String string = (String) objects[index];
                size += string.getBytes().length + 2;
            }
            if(b == DataType.OBJECT)
            {
                /* Nothing here */
            }
            index++;
        }
        return size;
    }

    public static byte[] create(short packetID, Object[] objects, byte... dataFormat)
    {
        byte[] data = new byte[determineFormatSize(dataFormat, objects) + 2]; /* add 2 for ID */
        byte[] idInBytes = Util.shortToBytes(packetID);
        data[0] = idInBytes[0];
        data[1] = idInBytes[1];
        int index = 2;
        int formatIndex = 0;
        while(true)
        {
            if(formatIndex >= dataFormat.length)
            {
                break;
            }
            byte format = dataFormat[formatIndex];
            Object obj = objects[formatIndex];
            if(format == DataType.BOOLEAN)
            {
                boolean bool = (boolean) obj;
                if(bool)
                {
                    data[index] = 1;
                }
                else
                {
                    data[index] = 0;
                }
                index += 1;
            }
            else if(format == DataType.BYTE)
            {
                byte by = (byte) obj;
                data[index] = by;
                index++;
            }
            else if(format == DataType.CHAR)
            {
                char character = (char) obj;
                data = Util.combineArray(data, Util.charToBytes(character), index);
                index += 2;
            }
            else if(format == DataType.SHORT)
            {
                short sh = (short) obj;

                byte[] bytes = Util.shortToBytes(sh);
                data = Util.combineArray(data, bytes, index);
                index += 2;
            }
            else if(format == DataType.INTEGER)
            {
                int integer = (int) obj;
                data = Util.combineArray(data, Util.intToBytes(integer), index);
                index += 4;
            }
            else if(format == DataType.LONG)
            {
                long bigint = (long) obj;
                data = Util.combineArray(data, Util.longToBytes(bigint), index);
                index += 8;
            }
            else if(format == DataType.FLOAT)
            {
                float floatingp = (float) obj;
                data = Util.combineArray(data, Util.floatToBytes(floatingp), index);
                index += 4;
            }
            else if(format == DataType.DOUBLE)
            {
                double d = (double) obj;
                data = Util.combineArray(data, Util.doubleToBytes(d), index);
                index += 8;
            }
            else if(format == DataType.STRING)
            {
                String str = (String) objects[formatIndex];
                short length = (short) str.length();
                byte[] stringLength = Util.shortToBytes(length);
                data[index++] = stringLength[0];
                data[index++] = stringLength[1];

                data = Util.combineArray(data, str.getBytes(), index);
                index += str.getBytes().length;
            }
            else if(format == DataType.OBJECT)
            {
                /* temp */
            }
            formatIndex++;
        }
        return data;
    }

    private static void objectToBytes(byte[] currentData, byte format, Object obj, int offset)
    {
        if(format == DataType.BOOLEAN)
        {
            boolean bool = (boolean) obj;
        }
    }
}
