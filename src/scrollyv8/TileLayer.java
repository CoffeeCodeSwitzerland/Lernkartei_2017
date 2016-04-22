package scrollyv8;

public class TileLayer
{

    private Tile[] list;
    private int size;
    private int maxSize;

    public TileLayer(int max)
    {
        size = 0;
        maxSize = max;
        list = new Tile[maxSize];
        for (int i = 0; i < maxSize; i++)
        {
            list[i] = new Tile(0, 0, 0, 0, 't',null);
        }
    }

    public int size()
    {
        return size;
    }

    public void add(Tile s)
    {
        list[size].copy(s);
        size++;
    }

    public Tile get(int i)
    {
        if (i < size)
        {
            return list[i];
        } else
        {
            return null;
        }
    }
    
    public void sort() // Currently bubbleSort since the lists are small. Could do quickSort too.
    {
        Tile t;
        int lMax = size;
        while(lMax>1)
        {
            for(int i = 0;i<lMax-1;i++)
            {
                if((list[i].x>list[i+1].x) || ( (list[i].x==list[i+1].x)&&((list[i].y>list[i+1].y))))
                {
                    t = list[i];
                    list[i] = list[i+1];
                    list[i+1] = t;
                }
            }
            lMax--;
        }

    }
}