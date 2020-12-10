import java.util.*;

public class Add128 implements SymCipher {

    private byte[] bArray;
    int size = 128;

    // lets add a few constructors

    // default constructor
    public Add128() {
        // so the best way to do this is to kind of gp o though the
        // array and anuse the random nums to populate it with diff bytes
        Random r = new Random();

        bArray = new byte[size];
        r.nextBytes(bArray);
    }

    // constructor if the array is given
    public Add128(byte[] arr) {
        // we gotta check if its the right size or
        // were gonna get an error later

        if (arr.length != size) {
            throw new IllegalArgumentException("Invalid size parameter");

        }

        bArray = new byte[size];
        this.bArray = arr;
    }

    @Override
    public byte[] getKey() {
        byte[] arr = bArray;
        return arr;
    }

    @Override
    public byte[] encode(String S) {
        // this gotta encode the method
        // first we need to return a byte array so make that
        byte[] en = S.getBytes();

        // now we need to store the bytes in the right place
        // ERROR!!!possible that its more than 120

        for (int i = 0; i < en.length; i++) {
            int x = i % bArray.length;
            // en[i]+= bArray[x];
            // this^^ is giving me the wrong thing and idk why wtf
            // need case cuz the type is an integer
            en[i] = (byte) (en[i] + bArray[x]);

        }
        return en;
    }

    @Override
    public String decode(byte[] bytes) {
        // Im copying the above method and then like reversing
        byte[] de = bytes.clone();

        // now we need to store the bytes in the right place
        // ERROR!!!possible that its more than 120

        for (int i = 0; i < de.length; i++) {
            int x = i % bArray.length;
            // en[i]+= bArray[x];
            // this^^ is giving me the wrong thing and idk why wtf
            // need case cuz the type is an integer
            de[i] = (byte) (de[i] - bArray[x]);

        }

        // remeber that need to return as a string so cast
        return new String(de);
    }

}