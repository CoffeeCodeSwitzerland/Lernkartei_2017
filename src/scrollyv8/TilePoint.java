package scrollyv8;

import java.awt.Image;

/**
 *
 * @author Andrew MacRae <macrae@berkeley.edu>
 */
public class TilePoint
{
    private Image im;
    public int gid;
    public int width,height;
    
    public TilePoint(Image img,int w, int h, int g)
    {
        width = w;
        height = h;
        gid = g;
        im = img;
    }
    
    public Image getImage()
    {
        return im;
    }
}
