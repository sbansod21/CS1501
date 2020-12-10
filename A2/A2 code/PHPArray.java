//SDB88
//Sushruti Bansod

import java.util.*;

@SuppressWarnings("unchecked")
public class PHPArray<V> implements Iterable<V>
{
    //first lets declare the global variables

    private int N;           // number of key-value pairs in the symbol table
    private int M;           // size of linear probing table

    private Node[] entries;  // the table
    
    private Node head;       // head of the linked list
    private Node tail;       // tail of the linked list
    private Node next;
    private Node prev;

    private Iterator<Pair<V>> iter;

    private boolean reHash;
    

    //now all the constructors
    public PHPArray() 
    {
        //empty constructor
    }

    public PHPArray(int capacity) 
    {
        //constructor with a given capacity

        this.M = capacity;
        this.reHash = false;
        entries = (Node[]) new Node[M];
    }

    public PHPArray(int m, int n, Node[] table, Node h, Node t) 
    {
        this.M = m;
        this.N = n;
        
        this.entries = table;
        this.head = h;
        this.tail = t;
        
        this.reHash = false;
    }

    @Override
    public Iterator<V> iterator() 
    {
        // TODO Auto-generated method stub
        Iterator<V> i = new Iterator<V>()
        {
            Node curr = head;

            @Override
            public boolean hasNext()
            {
                if(curr != null)
                {
                    return true;
                }
                return false;
            }

            @Override
            public V next()
            {
                Node temp = curr;
                
                curr = curr.next;

                return (V)temp.pair.value;
            }
        };
        return i;
    }

    ////NODE STUFF
    @SuppressWarnings("unchecked")
    private static class Node<V extends Comparable<V>> implements Comparable<V>
    {
        private Node next;
        private Node prev;
        private Pair<V> pair;

        Node() //kinda like and empty constructor
        {
            next = null;
            prev = null;
            pair = null;
        }

        Node(Pair<V> contents)
        {
            next = null;
            prev = null;
            pair = new Pair<V>(contents.key, contents.value);
        }

        @Override
        public int compareTo(V o) 
        {
            // TODO Auto-generated method stub
            V value = pair.value;
            String s = "PHPArray values are not Comparable -- cannot be sorted";            ;

            if((!(value instanceof Comparable) || !(o instanceof Comparable)))
            {
                throw new IllegalArgumentException(s);
            }

            return value.compareTo(o);
        }
    }

    //Pair methods
    public static class Pair<V>
    {
        public String key;
        public V value;

        Pair()
        {
            this(null, null);
        }

        Pair(String k, V v)
        {
            key = k;
            value = v;
        }
    }

    //Iterator for the pair nodes p much the same
    public Iterator<Pair<V>> pairIterator() 
    {
        // TODO Auto-generated method stub
        Iterator<Pair<V>> i = new Iterator<Pair<V>>()
        {
            Node curr = head;

            @Override
            public boolean hasNext()
            {
                if(curr != null)
                {
                    return true;
                }
                return false;
            }

            @Override
            public Pair<V> next()
            {
                Node temp = curr;
                
                curr = curr.next;

                return temp.pair;
            }
        };
        return i;
    }

    public Pair<V> each()
    {
        if(iter == null)
        {
            reset();
        }

        if(iter.hasNext())
        {
            return (Pair<V>)iter.next();
        }

        return null;
    }

    public void reset()
    {
        iter = this.pairIterator();
    }   
    
    public int length()
    {
        return M;
    }

    public void showTable()
    {
        System.out.println("Raw Hash Table Contents:");

        for(int i = 0; i< entries.length; i++)
        {
            if(entries[i] == null)
            {
                System.out.println(i + ": null");
            }else
            {
                System.out.println(i + ": Key: " + entries[i].pair.key + " Value: " + (V)entries[i].pair.value);
            }
        }
    }

    // now lets do the methods that are gonna require 2
    //put, get, unset

    public V get(String key)
    {
        for (int i = hash(key); entries[i] != null; i = (i + 1) % M)
        {
            if (entries[i].pair.key.equals(key))
            {
                return (V)entries[i].pair.value;
            }
        }
        return null;
    }

    public V get(int key)
    {
        //incase user is a dumbass and the key is a int 
        Integer string = new Integer(key);
        
        return get(string.toString());
    }

    public void put(String key, V value)
    {
        if (value == null) 
        {
            unset(key);
        }

        // double table size if 50% full
        if (N >= M / 2) 
        {
            resize(2 * M);
        }

        int i;
        for (i = hash(key); entries[i] != null; i = (i + 1) % M) 
        {
            // update the value if key already exists
            if (entries[i].pair.key.equals(key)) 
            {
                entries[i].pair.value = value; return;
            }
        }
 
        Pair<V> nPair = new Pair<V>(key, value);
        // found an empty entry
        entries[i] = new Node(nPair);
        //insert the node into the linked list
        if(reHash == true)//this case ONLY IF REHASH IS T
        {
            entries[i].next = this.next;
            if(entries[i].next != null)
            {
                entries[i].next.prev = entries[i];
            }

            entries[i].prev = this.prev;

            if(entries[i].prev != null)
            {
                entries[i].prev.next = entries[i];
            }

            reHash = false;
            return;
        }
        if(head == null)
        {
            head = tail = entries[i];
            return;
        }

        entries[i].prev = tail;
        entries[i].next = null;
        tail.next = entries[i];
           
        tail = tail.next;
        
    
        N++;
    }

    public void put(int key, V value)
    {
        //generally same for the int verison of get
        put(String.valueOf(key), value);
    }

    private int hash(String key) 
    {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    //arrays product, arrays sum, easy ones that are east to implement

    public void unset(int key)  //if user is stupid
    {
        Integer string = new Integer(key);

        unset(string.toString(key));
    }

    public void unset(String key)
    {
        if(get(key) == null)
        {
            return;
        }

        // find position i of key
        int i = hash(key);
        while (!key.equals(entries[i].pair.key)) 
        {
            i = (i + 1) % M;
        }

        //cornercase: only one node
        if(head == tail)
        {
            head = null;
        }else if(entries[i] == head) //case: if the thing to delete is head
        {
            head = head.next;
            head.prev = null;
        }else if(entries[i] == tail)
        {
            System.out.println(tail.pair.key);
            tail = entries[i].prev;
        }else
        {
            entries[i].next.prev = entries[i].prev;
            entries[i].prev.next = entries[i].next;
        }

        entries[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % M;
        
        while (entries[i] != null) 
        {
            // delete and reinsert
            System.out.println("Key " + entries[i].pair.key + " rehashed...");
            
            String keyToRehash = entries[i].pair.key;
            V valToRehash = (V)entries[i].pair.value;
            
            this.next = entries[i].next;
            this.prev = entries[i].prev;

            entries[i] = null;
            N--;
            reHash = true;
            
            put(keyToRehash, valToRehash);
            i = (i + 1) % M;
        }

        N--;

        // halves size of array if it's 12.5% full or less
        if (N > 0 && N <= M/8) resize(M/2);

        assert check();
    }

    private void resize(int capacity)
    {
        System.out.println("Size: "+ N +" -- resizing array from "+ M +" to "+ capacity);

        PHPArray<V> temp = new PHPArray<V>(capacity);

        Node curr = head;

        while(curr != null)
        {
            temp.put(curr.pair.key, (V)curr.pair.value);
            curr = curr.next;
        }

        
        entries = temp.entries;
        head    = temp.head;
        tail    = temp.tail;
        M       = temp.M;
        N       = temp.N;
    }

    private boolean check()
    {
        //check if table is at most half full

        if(M < 2 * N)
        {
            System.out.println("Hash table size M = " + M + "; array size N = " + N);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < M; i++) 
        {
            if (entries[i].pair.key == null) 
            {
                continue;
            }else if (get(entries[i].pair.key) != entries[i].pair.value) 
            {
                System.out.println("get(" + entries[i].pair.key + ") = " + get(entries[i].pair.key) + "; hashTable[" + i + "].valueue = " + entries[i].pair.value);
                return false;
            }
        }
        return true;
    }

    public PHPArray<String> array_flip()
    {
        PHPArray<String> flipArray = new PHPArray<>(M);
        Node curr = head;

        while (curr != null) 
        {

            flipArray.put((String)curr.pair.value, curr.pair.key);
            curr = curr.next;
        }

        return flipArray;
    }

    public ArrayList<String> keys() 
    {
        //put all the keys in an alist
        Node curr = head;

        ArrayList<String> kList = new ArrayList<>();

        while (curr != null) 
        {
            kList.add(curr.pair.key);
            curr = curr.next;
        }

        return kList;
    }

    public ArrayList<V> values() 
    {
        //an a list of vals
        Node curr = head;
        
        ArrayList<V> vList = new ArrayList<>();

        while (curr != null) 
        {
            vList.add((V)curr.pair.value);
            curr = curr.next;
        }

        return vList;
    }

    public void asort()
    {
        //same as asort
        try 
        {
            head = merge_sort(head);
        } catch (IllegalArgumentException AR) 
        {
            System.out.println("Cannot be sorted");
        }
        head = merge_sort(head);
        Node reKey = head;
        
        PHPArray<V> sortedArray = new PHPArray<V>(M);

        for (int i = 0; reKey != null; i++) 
        {
            sortedArray.put(reKey.pair.key, (V)reKey.pair.value);
            reKey = reKey.next;
        }

        M = sortedArray.M;
        N = sortedArray.N;
        head = sortedArray.head;
        tail = sortedArray.tail;
        entries = Arrays.copyOf(sortedArray.entries, sortedArray.M);
    }

    public void sort()
    {
        //same as asort
        try {
            head = merge_sort(head);
        } catch (IllegalArgumentException AR) 
        {
            System.out.println("Cannot be sorted");
        }
        Node reKey = head;
        
        PHPArray<V> sortedArray = new PHPArray<V>(M);

        for (int i = 0; reKey != null; i++) 
        {
            sortedArray.put(String.valueOf(i), (V)reKey.pair.value);
            reKey = reKey.next;
        }

        M = sortedArray.M;
        N = sortedArray.N;
        head = sortedArray.head;
        tail = sortedArray.tail;
        entries = Arrays.copyOf(sortedArray.entries, sortedArray.M);
    }

    private Node merge_sort(Node n) 
    {
        Node curr = n;
        
        if (curr == null || curr.next == null) 
        {
            return curr;
        }

        Node mid = getMid(curr);
        Node right = mid.next;
        mid.next = null;

        return merge(merge_sort(curr), merge_sort(right));
    }

    private Node getMid(Node curr) 
    {
        if (curr == null) 
        {
            return curr;
        }
        
        Node big, small;

        small = curr;
        big = small;

        while (small.next != null && small.next.next != null) 
        {
            big = big.next;
            small = small.next.next;
        }
        return big;
    }

    private Node merge(Node l, Node r) 
    {
        Node curr, temp;
        
        temp = new Node<>();
        curr = temp;

        while (l != null && r != null) 
        {
            if (l.compareTo((Comparable)r.pair.value) < 1) 
            {
                curr.next = l;
                l = l.next;
            } else {
                curr.next = r;
                r = r.next;
            }
            curr = curr.next;
        }
        
        if (l == null) 
        {
            curr.next = r;
        } else 
        {
            curr.next = l;
        }

        return temp.next;
    }

    public PHPArray<V> array_change_key_case(Node head, int c)
    {
        if(head == null)
        {
            return null;
        }
        PHPArray<V> ar = new PHPArray<V>(M);
        String x = "";
        //if 1 then upper case
        if(c == 1)
        {
            for(int i = 0; i < ar.length(); i++)
            {
                x = (head.pair.key).toUpperCase();
                ar.put(x,(V)head.pair.value);
            }
        }

        if(c == 1)
        {
            for(int i = 0; i < ar.length(); i++)
            {
                x = (head.pair.key).toLowerCase();
                ar.put(x,(V)head.pair.value);
            }
        }

        return ar;
    }

    public PHPArray<V> array_combine(String[] keys, String[]values)
    {
        PHPArray<V> ar = new PHPArray<V>(keys.length);

        for(int i = 0; i < ar.length(); i++)
        {
            ar.put(keys[i],(V)values[i]);
        }

        return ar;
    }

}