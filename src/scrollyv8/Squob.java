package scrollyv8;

import java.awt.*;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;

//import javax.imageio.ImageIO;
public class Squob
{

    public double x, y, w, h; // Bounding box for drawing
    public double dCx, dCy, dCw, dCh; // Bounding box for collisions
    public Image im;
    public char type;
    public String path;

    public Squob(double xs, double ys, double ws, double hs)
    {
        x = xs;
        y = ys;
        w = ws;
        h = hs;
        dCx = 0;
        dCy = 0;
        dCw = w;
        dCh = h;
        type = 't';
        path = "";
    }

    public Squob(int xs, int ys, int ws, int hs, char tp)
    {
        x = xs;
        y = ys;
        w = ws;
        h = hs;
        dCx = 0;
        dCy = 0;
        dCw = w;
        dCh = h;        
        type = tp;
    }
    public Squob(double xs, double ys, double ws, double hs,Image imgur)
    {
        x = xs;
        y = ys;
        w = ws;
        h = hs;
        dCx = 0;
        dCy = 0;
        dCw = w;
        dCh = h;
        type = 't';
        path = "";
        im = imgur;
    }
    public void draw(Graphics g, double x0, double y0)
    {
        g.drawImage(im, (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
    }

    public void copy(Squob s)
    {
        x = s.x;
        y = s.y;
        w = s.w;
        h = s.h;
        dCx = s.dCx;
        dCy = s.dCy;
        dCw = s.dCw;
        dCh = s.dCh;        
        type = s.type;
        path = s.path;
        im = s.im;
    }

    public boolean isIn(Squob s)
    {
        //if ((s.x + s.w > x) && (s.x < x + w) && (s.y + s.h > y) && (s.y < y + h))
        if (( s.x+s.dCx + s.dCw > x+dCx) && (s.x+s.dCx < dCx + dCw) && (s.y+s.dCy + s.dCh > y+dCy) && (s.y+s.dCy < y+dCy + dCh))
        {
            return true;
        } else
        {
            return false;
        }

    }

    public boolean isIn(Squob s, double dx, double dy)
    {        
        //if (( s.x + s.w +dx> x) && (s.x +dx < x + w) && (s.y+ s.dCh +dy > y) && (s.y + dy< y+h))
        if (
                (s.x+s.dCx + s.dCw +dx > x+dCx) &&
                (s.x+s.dCx +dx < x +dCx + dCw) && 
                (s.y+ s.dCh +dy > y + dCy) && 
                (s.y + s.dCy + dy< y+dCy+dCh))
        {
            return true;
        } 
        else
        {
            return false;
        }
    }

}