package scrollyv8;

import java.awt.Graphics;

public class Robob extends Enemy
{

    private SpriteManager sMan_r, sMan_l, sMan_d, sMan_D;
    public char state;

    public Robob(double xs, double ys, double ws, double hs, String sPath)
    {
        super(xs, ys, ws, hs);
        alive = true;
        type = 'r';
        dCy=-4;

        sMan_r = new SpriteManager(sPath + "robob_right.png", 40, 30, 4, true);
        sMan_l = new SpriteManager(sPath + "robob_left.png", 40, 30, 4, true);
        sMan_d = new SpriteManager(sPath + "robob_right_death.png", 40, 30, 8, false);
        sMan_D= new SpriteManager(sPath + "robob_left_death.png", 40, 30, 8, false);
        sMan_d.setDelay(8);
        sMan_D.setDelay(8);
        state = 'r';
    }

    public boolean addImage(String fN)
    {

        return false;
    }

    public void draw(Graphics g, double x0, double y0)
    {
        int dy = 4;
        switch (state)
        {
            case 'r':
                g.drawImage(sMan_r.get(), (int) (x - x0), (int) (y - y0+dy), (int) w, (int) h, null);
                break;
            case 'l':
                g.drawImage(sMan_l.get(), (int) (x - x0), (int) (y - y0+dy), (int) w, (int) h, null);
                break;
            case 'd':
                g.drawImage(sMan_d.get(), (int) (x - x0), (int) (y - y0+dy), (int) w, (int) h, null);
                break;
            case 'D':
                g.drawImage(sMan_D.get(), (int) (x - x0), (int) (y - y0+dy), (int) w, (int) h, null);
                break;
        }
    }

    public void iterate(double d, boolean collH, boolean collV, boolean willFall, double dVX, double dVY)
    {
        super.iterate(d, collH, collV, willFall, dVX, dVY);
        if (isAlive())
        {
            if (vx >= 0)
            {
                state = 'r';
            } else
            {
                state = 'l';
            }
        }
        switch (state)
        {
            case 'r':
                sMan_r.iterate(d);
                break;
            case 'l':
                sMan_l.iterate(d);
                break;
            case 'd':
                sMan_d.iterate(d);
                break;
            case 'D':
                sMan_D.iterate(d);
                break;
        }
    }

    public boolean setState(char c)
    {
        if (c == 'l')
        {
            state = 'l';
            return true;
        } else if (c == 'r')
        {
            state = 'r';
            return true;
        } else
        {
            return false;
        }
    }

    public void kill()
    {
        alive = false;
        if(state == 'r')
        {
            state = 'd';
        }
        else state = 'D';
    }
}