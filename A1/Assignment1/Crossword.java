//CS1501 Fall 2020
//Sushruti Bansod
//sdb88

import java.io.*;
import java.util.*;

public class Crossword
{
    private static DictInterface Dict;
    private static char[][] board;
    private static int size;
    private static StringBuilder sbRow[];
    private static StringBuilder sbCol[];
    private static int mPR[];
    private static int mPC[];
    private static long start;
    public static void main(String[] args) throws IOException
    {
        start = System.nanoTime();
       // System.out.println("I am running");
        String dict = args[0];
        Scanner inScan = new Scanner(new FileInputStream(dict));

        Dict = new MyDictionary();

        String word;
        while(inScan.hasNext())
        {
            word = inScan.nextLine();
            Dict.add(word);
        }
        
        String bName = args[1];
        Scanner newScan = new Scanner(new FileInputStream(bName));

        size = newScan.nextInt();

        board = new char[size][size];

        sbRow = new StringBuilder[size];
        sbCol = new StringBuilder[size];

        for(int i = 0; i < size; i++)
        {
            sbRow[i] = new StringBuilder();
            sbRow[i].append("");
            
            sbCol[i] = new StringBuilder();
            sbCol[i].append("");
        }

        String rowWord;
        int row = 0;
        int col = 0;
        
        while(newScan.hasNext())
        {
            rowWord = newScan.next();
            for(int i = 0; i < rowWord.length(); i++)
            {
                board[row][col]= rowWord.charAt(i);
                col++;
            }

            row++;
            col = 0;
        }
        //finding those damn dashes

        mPR = new int[size];
        mPC = new int[size];

        Arrays.fill(mPR, -1);
        Arrays.fill(mPC, -1);

        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                if(board[i][j] == '-')
                {
                    mPR[i] = j;
                    mPC[j] = i;
                }

            }
        }
        solve(0,0);
    }

    public static void solve(int row, int col)
	{
       if(board[row][col] == '+') // if the spce is the board has a 
       {// you can put somehting inside
           for(char c = 'a'; c <= 'z'; c++)
           {//check this for every letter, bad runtime but still
            if(isValid(row, col, c))
            {
                //add the alphabet to the word
                sbRow[row].append(c);
                sbCol[col].append(c);

                if(col == (size-1) && row == (size -1))
                {//checks to make sure we not off the board yet
                    System.out.println(sbRow.length);

                    for(int i = 0; i < sbRow.length; i++)
                    {
                        //prints out the word
                        System.out.println(sbRow[i]);
                    }
                    long finish = System.nanoTime();
                    long timeElapsed = finish - start;
                    //System.out.println("It took this program " + timeElapsed + " nanoseconds to run");
                    System.exit(0); //stops the program
                }else if(col+1 < size) // this means we not off the board yet
                {
                    solve(row, col+1);//recurse on the next colum
                
                }else //moves to the next row
                {
                    solve(row+1, 0);
                }

                //if the code gets to this point it isnt working, so we remove the last char and interate
                sbRow[row].deleteCharAt(sbRow[row].length() -1);
                sbCol[col].deleteCharAt(sbCol[col].length() -1);
            }
           }
       /*}else if(board[row][col] == '-')
       {
           if(isValid(row, col+1, board[row][col+1]))
           {
            //sbRow[row].append(board[row][col+1]);
            //sbCol[col].append(c);

            if(col == (size-1) && row == (size -1))
            {//checks to make sure we not off the board yet
                System.out.println(sbRow.length);

                for(int i = 0; i < sbRow.length; i++)
                {
                    //prints out the word
                    System.out.println(sbRow[i]);
                }
                System.exit(0); //stops the program
            }else if(col+1 < size) // this means we not off the board yet
            {
                solve(row, col+1);//recurse on the next colum
            
            }else //moves to the next row
            {
                solve(row+1, 0);
            }

            //sbRow[row].deleteCharAt(sbRow[row].length() -1);
            //sbCol[col].deleteCharAt(sbCol[col].length() -1);
           }

           if(isValid(row+1, col, board[row+1][col]))
           {
            //sbRow[row].append(c);
            //sbCol[col].append(c);

            if(col == (size-1) && row == (size -1))
            {//checks to make sure we not off the board yet
                System.out.println(sbRow.length);

                for(int i = 0; i < sbRow.length; i++)
                {
                    //prints out the word
                    System.out.println(sbRow[i]);
                }
                System.exit(0); //stops the program
            }else if(col+1 < size) // this means we not off the board yet
            {
                solve(row, col+1);//recurse on the next colum
            
            }else //moves to the next row
            {
                solve(row+1, 0);
            }

            sbRow[row].deleteCharAt(sbRow[row].length() -1);
            sbCol[col].deleteCharAt(sbCol[col].length() -1);

           }
        */}else
        {
            if(isValid(row, col, board[row][col]))
            {
            //add the alphabet to the word
            sbRow[row].append(board[row][col]);
            sbCol[col].append(board[row][col]);

            
            if(col+1 < size) // this means we not off the board yet
            {
                solve(row, col+1);//recurse on the next colum
            
            }else //moves to the next row
            {
                solve(row+1, 0);
            }

            //if the code gets to this point it isnt working, so we remove the last char and interate
            sbRow[row].deleteCharAt(sbRow[row].length() -1);
            sbCol[col].deleteCharAt(sbCol[col].length() -1);
            }
        }
        
    }

    private static boolean isValid(int row, int col, char c)
    {
        //If j is not an end index, then rowStr[i] + the letter a must be a valid prefix in the
        //dictionary 
        boolean valid = true;
        int val;
        if(col < size-1)
        {
            if(board[row][col+1] == '-')
            {
                sbRow[row].append(c);
                val = Dict.searchPrefix(sbRow[row],0, sbRow[row].length()-1);

                if(!(val == 3 || val == 2))
                {
                    valid = false;
                }

            }else{
                 sbRow[row].append(c);
            val = Dict.searchPrefix(sbRow[row]);

            if(!(val == 3 || val == 1))
            {
                valid = false;
            }
            }
           
            
        }

        //If j is an end index, then rowStr[i] + the letter must be a valid word in the
        //dictionary 
        if(col == size-1)
        {
            
            sbRow[row].append(c);
            val = Dict.searchPrefix(sbRow[row]);

            if(!(val == 2 || val == 3))
            {
                valid = false;
            }
            
        }

        //If i is not an end index, then colStr[j] + the letter must be a valid prefix in the
        //dictionary
        if(row < size-1)
        {
            if(board[row+1][col] == '-')
            {
                sbCol[col].append(c);
                val = Dict.searchPrefix(sbCol[col],0, sbCol[col].length()-1);

                if(!(val == 3 || val == 2))
                {
                    valid = false;
                }
            }else
            {
                sbCol[col].append(c);
                val = Dict.searchPrefix(sbCol[col]);

                if(!(val == 1 || val == 3))
                {
                    valid = false;
                }
            }
            
        }

        if(row == size-1)
        {
            sbCol[col].append(c);
            val = Dict.searchPrefix(sbCol[col]);

            if(!(val == 2 || val == 3))
            {
                valid = false;
            }
            
        }

        sbRow[row].deleteCharAt(sbRow[row].length() -1);
        sbCol[col].deleteCharAt(sbCol[col].length() -1);

        return valid;
    }

}