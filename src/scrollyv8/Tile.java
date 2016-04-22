package scrollyv8;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Andrew MacRae <macrae@berkeley.edu>
 */
public class Tile extends Squob
{
    int gid = -1;
//    TileManager tm;
    public Tile(int xs, int ys, int ws, int hs, int g, Image img)
    {
        super(xs,ys,ws,hs,img);
        gid = g;
        im = img;
    }
    
    public void draw(Graphics g, double x0, double y0)
    {
        g.drawImage(im,(int)(x-x0),(int)(y-y0),(int)w,(int)h,null);
    }
    public Image getImage()
    {
        return im;        
    }
    public String toString()
    {
        String tmp = "(x,y,w,h) = ("+x+","+y+","+w+","+h+")\n Image: "+im.toString();
        return tmp;        
    }
}
