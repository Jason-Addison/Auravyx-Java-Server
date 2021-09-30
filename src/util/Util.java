package util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Owner on 5/19/2017.
 */
public class Util
{

    public static byte[] shortToBytes(short s)
    {
        byte[] arr = new byte[2];
        arr[0] = (byte)(s & 0xff);
        arr[1] = (byte)((s >> 8) & 0xff);
        return arr;
    }

    public static byte[] intToBytes(int i)
    {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    public static byte[] floatToBytes(float f)
    {
        return ByteBuffer.allocate(4).putFloat(f).array();
    }
    public static byte[] longToBytes(long l)
    {
        return ByteBuffer.allocate(8).putLong(l).array();
    }
    public static byte[] charToBytes(char b)
    {
        return ByteBuffer.allocate(2).putChar(b).array();
    }
    public static byte[] doubleToBytes(double d)
    {
        return ByteBuffer.allocate(8).putDouble(d).array();
    }
    public static short bytesToShort(byte... arr)
    {
        return ByteBuffer.wrap(arr).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public static void printAllObjects(Object[] arr)
    {
        for(Object o : arr)
        {
            System.out.print(o + "   ");
        }
        System.out.print(System.lineSeparator());
    }
    public static void printAllBytes(byte[] arr)
    {
        for(byte o : arr)
        {
            System.out.print(o + "   ");
        }
        System.out.print(System.lineSeparator());
    }

    public static byte[] combineArray(byte[] base, byte[] added, int offset)
    {
        for(int i = 0; i < added.length; i++)
        {
            base[i + offset] = added[i];
        }
        return base;
    }
}
