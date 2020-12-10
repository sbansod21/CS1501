public class DLB implements DictInterface
{
    private Node root;
    public DLB()

    {
        root = new Node();
    }
    
    public boolean add(String key)
    {
        if(key == null) throw new IllegalArgumentException("calls put() with a null key");

        key = key + '$';

        if(add(root, key, 0) != null)
        {
            return true;
        }else 
        {
            return false;
        }
    }

    private Node add(Node x, String key, int pos) {
        Node result = x;
        if (x == null){
            result = new Node();
            result.letter = key.charAt(pos);
            if(pos < key.length()-1){
              result.child = add(result.child, key, pos+1);
            } else {
              result.letter = key.charAt(pos);
            }
        } else if(x.letter == key.charAt(pos)) {
            if(pos < key.length()-1){
              result.child = add(result.child, key, pos+1);
            } else {
              result.letter = key.charAt(pos); 
            }
        } else {
          result.sibling = add(result.sibling, key, pos);
        }
        return result;
    }

    public int searchPrefix(StringBuilder s)
    {
        return searchPrefix(s, 0, s.length()-1);
    }

    public int searchPrefix(StringBuilder s, int start, int end)
    {
        boolean prefix = false;
        boolean word = false;

        Node curr = root;

        for(int i = start; i <= end; i++)
        {
            int count = 0;
            while(curr != null)
            {
                if(curr.letter == s.charAt(i))
                {     
                    break;
                }else
                {
                    curr = curr.sibling;
                }
            }

            if(i == end && curr != null)
            {
                prefix = true;

                curr = curr.child;

                while(curr.sibling != null)
                {
                    count++;
                    curr = curr.sibling;
                }

                if(curr != null)
                {
                    word = true;
                    if(count < 1)
                    {
                        prefix = false;
                    }
                    break;
                }
                
            }else if(curr != null)
            {
                curr = curr.child;
            }
        }

        if (prefix && word) return 3;
		else if (word) return 2;
		else if (prefix) return 1;
		else return 0;
    }
    
    private class Node 
    {
        private char letter;
        private Node sibling;
        private Node child;
    }

}