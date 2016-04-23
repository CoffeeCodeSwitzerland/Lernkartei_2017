package scrollyv8;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteManager
{

    private Image[] ims;
    private int maxIms;
    private int index;
    private int currFrame;
    private double count;
    private int delay;
    private boolean loop;

    public SpriteManager()
    {
        loop = true;
        maxIms = 20;
        index = 0;
        count = 0;
        delay = 10;

        currFrame = 0;
        ims = new Image[maxIms];
    }

    public SpriteManager(String[] fNs)
    {
        loop = true;
        maxIms = 20;
        index = 0;
        count = 0;
        delay = 10;

        currFrame = 0;
        ims = new Image[fNs.length];

        try
        {
            for (int i = 0; i < fNs.length; i++)
            {
                add(ImageIO.read(new File(fNs[i])));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public SpriteManager(String[] fNs, int del)
    {
        loop = true;
        maxIms = 20;
        index = 0;
        count = 0;
        delay = del;

        currFrame = 0;
        ims = new Image[fNs.length];

        try
        {
            for (int i = 0; i < fNs.length; i++)
            {
                add(ImageIO.read(new File(fNs[i])));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public SpriteManager(String p, int tw, int th, int n,boolean l)
    {
        loop = l;
        maxIms = n;
        index = 0;
        count = 0;
        delay = 10;

        currFrame = 0;
        ims = new Image[n];

        try
        {
            BufferedImage tIm = ImageIO.read(new File(p));            
            for (int i = 0; i < n; i++)
            {
                add(tIm.getSubimage(i*tw, 0, tw, th));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }        
    }
    public boolean add(Image im)
    {
        if (index >= maxIms)
        {
            return false;
        }
        else
        {
            ims[index] = im;
            index++;
            return true;
        }
    }

    public Image get()
    {
        if (index > 0)
        {
            return ims[currFrame];
        } else
        {
            return null;
        }
    }

    public Image get(int i)
    {
        if ((i >= 0) && (i < index))
        {
            return ims[i];
        } else if (index > 0)
        {
            return ims[0];
        } else
        {
            return null;
        }
    }

    public void iterate(double d)
    {
        count += d;
        if ((int) count >= delay)
        {
            count = 0;
            if(loop)
            {
                currFrame = (currFrame + 1) % index;
            }
            else
            {
                currFrame = Math.min(currFrame + 1,index-1);
            }
        }
    }

    public int getSize()
    {
        return index;
    }
    
    public void setDelay(int td)
    {
        delay = td;
    }
}