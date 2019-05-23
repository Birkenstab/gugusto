package game.level;

import java.util.Arrays;

public class ByteList {

    private byte[] data;
    private int index = 0;

    public ByteList(int size){
        data = new byte[size];
    }

    public ByteList(byte[] data){
        this.data = data;
    }

    public void writeUint8(byte nbr){
        data[index++] = nbr;
    }

    public void writeUint16(short nbr){
        data[index++] = (byte)(nbr >> 8);
        data[index++] = (byte)nbr;
    }

    public void writeUint32(int nbr){
        data[index++] = (byte)(nbr >> 24);
        data[index++] = (byte)(nbr >> 16);
        data[index++] = (byte)(nbr >> 8);
        data[index++] = (byte)nbr;
    }

    public void writeString(String str){
        writeUint32(str.length());
        for(char c : str.toCharArray()) data[index++] = (byte)c;
    }

    public byte readUint8(){
        return data[index++];
    }

    public short readUint16(){
        return (short)((data[index++] << 8) | data[index++]);
    }

    public int readUint32(){
        int high = (data[index++] << 8) | data[index++];
        int low= (data[index++] << 8) | data[index++];
        return (high << 16) | low;
    }

    public String readString(){
        int length = readUint32();
        return new String(Arrays.copyOfRange(data, index, index += length));
    }

    public byte[] getByteArray(){
        return data;
    }

}
