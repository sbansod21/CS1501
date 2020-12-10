
//Sushruti Bansod
//SDB88
//1501 Assignment 3

import java.io.*;

public class LZWmod {

    // these are not variables
    private static final int R = 256; // number of input chars
    private static final int L = 65536; // number of codewords
    // private static final int W = 12; // codeword width

    // these are variables cuz they need to be able to be changed
    private static int new_W = 9;
    private static int new_L = 512;

    public static void compress() {

        TSTmod<Integer> st = new TSTmod<Integer>();

        for (int i = 0; i < R; i++) {
            st.put(new StringBuilder().append((char) i), i);
        }
        int code = R + 1;// R is codeword for EOF

        // here is where things will change
        // so we need to read the file CHAR BY CHAR
        // best way to do it is make a helper method
        StringBuilder input;
        StringBuilder t = new StringBuilder();

        input = readChar_bit();

        while (input != null) {

            StringBuilder s = st.longestPrefixOf(input);

            while (s.length() == input.length()) {
                t = readChar_bit();
                input.append(t);
                s = st.longestPrefixOf(input);
            }

            BinaryStdOut.write(st.get(s), new_W);

            if (t != null && code < new_L) {
                st.put(input, code++);
                if (code == new_L) {
                    if (new_W < 16) {
                        new_L = (int) Math.pow(2, ++new_W);
                    }
                }
            }

            input = t;
        }

        BinaryStdOut.write(R, new_W);
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L];
        int i;

        for (i = 0; i < R; i++) {
            st[i] = "" + (char) i;
        }
        st[i++] = "";

        int codeword = BinaryStdIn.readInt(new_W);
        String val = st[codeword];

        while (true) {

            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(new_W);
            if (codeword == R) {
                break;
            }
            String s = st[codeword];
            if (i == codeword) {
                s = val + val.charAt(0);
            }

            if (i + 1 < L) {
                st[i++] = val + s.charAt(0);
            }

            if (i + 1 == new_L && new_W < 16) {
                new_L = (int) Math.pow(2, ++new_W);
            }

            if (i == L) {
                BinaryStdOut.write(val);
                codeword = BinaryStdIn.readInt(new_W);
                while (codeword != R) {
                    BinaryStdOut.write(st[codeword]);
                    codeword = BinaryStdIn.readInt(new_W);
                }
                break;
            }

            val = s;
        }

        BinaryStdOut.close();
    }

    private static StringBuilder readChar_bit() {
        char c;
        StringBuilder sb = new StringBuilder();

        try {
            c = BinaryStdIn.readChar();
            sb.append((char) c);
        } catch (Exception e) {
            sb = null;
        }

        return sb;

    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            compress();
        } else if (args[0].equals("+")) {
            expand();
        } else {
            throw new RuntimeException("Illegal command line argument");
        }

        System.exit(0);
    }

}