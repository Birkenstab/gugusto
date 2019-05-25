package game.level;

import java.util.Arrays;

public class DataView {

    private byte[] data;
    private int index = 0;

    public DataView(int size){
        data = new byte[size];
    }

    public DataView(byte[] data){
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

    public void writeStringAsCharSequence(String string){
        for(char c : string.toCharArray()) data[index++] = (byte)c;
    }

    public void writeString(String string){
        writeUint32(string.length());
        for(char c : string.toCharArray()) data[index++] = (byte)c;
    }

    public int readUint8(){
        return data[index++] & 0xFF;
    }

    public int readUint16(){
        return ((data[index++] & 0xFF) << 8) | (data[index++] & 0xFF);
    }

    public int readUint32(){
        int high = ((data[index++] & 0xFF) << 8) | (data[index++] & 0xFF);
        int low= ((data[index++] & 0xFF) << 8) | (data[index++] & 0xFF);
        return (high << 16) | low;
    }

    public String readCharSequenceAsString(int length){
        return new String(Arrays.copyOfRange(data, index, index += length));
    }

    public String readString(){
        int length = readUint32();
        return new String(Arrays.copyOfRange(data, index, index += length));
    }


    public byte[] getByteArray(){
        return data;
    }

}
