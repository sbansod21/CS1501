import java.util.*;

public class Substitute implements SymCipher {

    private byte[] key;
    private byte[] decode;

    private int size = 256;
    // constuctors

    // default
    public Substitute() {

        // this time instead of an array,
        // well use arrlist so its easy to shuffle
        ArrayList<Byte> pBytes = new ArrayList<Byte>();

        // inidtialized the key and the decoding key to the size
        key = new byte[size];
        decode = new byte[size];

        // load the alist with byes from 0 - 255
        for (int i = 0; i < size; i++) {
            // pBytes.add(i);
            // not working cux i is an int so cast
            pBytes.add((byte) i);
        }

        Collections.shuffle(pBytes);

        // copies from a list to key
        for (int i = 0; i < size; i++) {
            key[i] = pBytes.get(i);
            int skey = key[i] & 0xFF;
            decode[skey] = (byte) i;
        }
    }

    // constructor with a given param
    public Substitute(byte[] arr) {
        // similar to add
        if (arr.length != 256) {
            throw new IllegalArgumentException("Invalid size parameter");
        }

        this.key = arr.clone();
        decode = new byte[size];

        for (int i = 0; i < size; i++) {
            int skey = key[i] & 0xFF;
            decode[skey] = (byte) i;
        }
    }

    @Override
    public byte[] getKey() {

        byte[] arr = key;
        return arr;
    }

    @Override
    public byte[] encode(String S) {
        // copy most from add
        byte[] en = S.getBytes();

        byte[] Str = new byte[S.length()];

        for (int i = 0; i < en.length; i++) {
            int sKey = en[i] & 0xFF;
            Str[i] = key[sKey];
        }

        return Str;
    }

    @Override
    public String decode(byte[] bytes) {
        // same as enc, just copy add but backwards

        byte[] de = new byte[bytes.length];

        for (int i = 0; i < de.length; i++) {
            byte k = decode[bytes[i] & 0xFF];
            de[i] = k;
        }

        return new String(de);
    }

}