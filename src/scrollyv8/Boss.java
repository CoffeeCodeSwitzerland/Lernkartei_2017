package scrollyv8;

import java.awt.Graphics;


public class Boss extends Enemy
{

    private SpriteManager sMan_w, sMan_s, sMan_d;
    public char state;

    public Boss(double xs, double ys, double ws, double hs, String sPath)
    {        
        super(xs, ys, ws, hs);
        alive = true;
        type = 'B';
        String[] fN_w =
        {
            sPath + "boss_walk1.png", sPath + "boss_walk2.png", sPath + "boss_walk3.png", sPath + "boss_walk2.png"
        };
        String[] fN_s =
        {
            sPath + "boss_stand.png"
        };
        String[] fN_d =
        {
            sPath + "boss_dead.png"
        };

        sMan_w = new SpriteManager(fN_w);
        sMan_s = new SpriteManager(fN_s);
        sMan_d = new SpriteManager(fN_d);
        state = 's';

        hitPoints = 7;
        
        jumper = true;
        vx=-vx;
    }

    public boolean addImage(String fN)
    {
        return false;
    }

    public void draw(Graphics g, double x0, double y0)
    {
        switch (state)
        {
            case 'w':
                g.drawImage(sMan_w.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 's':
                g.drawImage(sMan_s.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 'd':
                g.drawImage(sMan_d.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
        }
    }

    public void iterate(double d, boolean collH, boolean collV, boolean willFall, double dVX, double dVY)
    {
        super.iterate(d, collH, collV, willFall, dVX, dVY);        
        if (isAlive())
        {
            if (Math.abs(vx) > 0)
            {
                state = 'w';
            } else
            {
                state = 's';
            }
        } else
        {
            state = 'd';
        }
        switch (state)
        {
            case 'w':
                sMan_w.iterate(2 * d);
                break;
            case 's':
                sMan_s.iterate(2 * d);
                break;
            case 'd':
                sMan_d.iterate(2 * d);
                break;
        }
    }

    public void kill()
    {
        hitPoints--;
        if (hitPoints < 0)
        {
            type = 'b';
            alive = false;
            state = 'd';
        }
    }

    public boolean setState(char c)
    {
        if (c == 'w')
        {
            state = 'w';
            return true;
        } else if (c == 's')
        {
            state = 's';
            return true;
        } else if (c == 'd')
        {
            state = 'd';
            return true;
        } else
        {
            return false;
        }
    }

    public char getState()
    {
        return state;
    }

    public boolean isAlive()
    {
        return alive;
    }
}