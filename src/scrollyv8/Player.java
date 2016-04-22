package scrollyv8;

import java.awt.Graphics;
import java.awt.Image;

public class Player extends Squob
{

    private SpriteManager sMan_r, sMan_l, sMan_ju, sMan_Ju, sMan_jd, sMan_Jd, sMan_s, sMan_S;
    private char state;
    double dl = .2;

    public Player(double xs, double ys, double ws, double hs)
    {
        super(xs, ys, ws, hs);
        sMan_r = new SpriteManager();
        sMan_l = new SpriteManager();
        sMan_ju = new SpriteManager();
        sMan_Ju = new SpriteManager();
        sMan_jd = new SpriteManager();
        sMan_Jd = new SpriteManager();
        sMan_s = new SpriteManager();
        sMan_S = new SpriteManager();

        state = 'r';

        dCx = dl * ws;
        dCw = (1 - 2 * dl) * ws;
    }

    public Player(double xs, double ys, double ws, double hs, String[] fN_r, String[] fN_l, String[] fN_ju, String[] fN_Ju, String[] fN_jd, String[] fN_Jd, String[] fN_s, String[] fN_S, int del)
    {
        super(xs, ys, ws, hs);
        sMan_r = new SpriteManager(fN_r, del);
        sMan_l = new SpriteManager(fN_l, del);
        sMan_ju = new SpriteManager(fN_ju, del);
        sMan_Ju = new SpriteManager(fN_Ju, del);
        sMan_jd = new SpriteManager(fN_jd, del);
        sMan_Jd = new SpriteManager(fN_Jd, del);
        sMan_s = new SpriteManager(fN_s, del);
        sMan_S = new SpriteManager(fN_S, del);

        state = 'r';
        dCx = dl * ws;
        dCw = (1 - 2 * dl) * ws;

    }

    public boolean addImage(Image i, char st)
    {
        switch (st)
        {
            case 'r':
                return sMan_r.add(i);
            case 'l':
                return sMan_l.add(i);
            case 'j':
                return sMan_ju.add(i);
            case 'J':
                return sMan_Ju.add(i);
            case 'g':
                return sMan_jd.add(i);
            case 'G':
                return sMan_Jd.add(i);
            case 's':
                return sMan_s.add(i);
            default:
                return sMan_S.add(i);
        }
    }

    public void draw(Graphics g)
    {
        switch (state)
        {
            case 'r':
                g.drawImage(sMan_r.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
            case 'l':
                g.drawImage(sMan_l.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
            case 'j':
                g.drawImage(sMan_ju.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
            case 'J':
                g.drawImage(sMan_Ju.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
            case 'g':
                g.drawImage(sMan_jd.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
            case 'G':
                g.drawImage(sMan_Jd.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
            case 's':
                g.drawImage(sMan_s.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
            default:
                g.drawImage(sMan_S.get(), (int) x, (int) y, (int) w, (int) h, null);
                break;
        }

    }

    public void draw(Graphics g, double x0, double y0)
    {
        switch (state)
        {
            case 'r':
                g.drawImage(sMan_r.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 'l':
                g.drawImage(sMan_l.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 'j':
                g.drawImage(sMan_ju.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 'J':
                g.drawImage(sMan_Ju.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 'g':
                g.drawImage(sMan_jd.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 'G':
                g.drawImage(sMan_Jd.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 's':
                g.drawImage(sMan_s.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            default:
                g.drawImage(sMan_S.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
        }
    }

    public void drawStatic(Graphics g, int tx, int ty)
    {
        g.drawImage(sMan_r.get(0), tx, ty, (int) w, (int) h, null);
    }

    public void setState(char c)
    {
        state = c;
    }

    public char getState()
    {
        return state;
    }

    public void iterate(double d)
    {
        switch (state)
        {
            case 'r':
                sMan_r.iterate(2 * d);
                break;
            case 'l':
                sMan_l.iterate(2 * d);
                break;
            case 'j':
                sMan_ju.iterate(d);
                break;
            case 'J':
                sMan_Ju.iterate(d);
                break;
            case 'g':
                sMan_jd.iterate(d);
                break;
            case 'G':
                sMan_Jd.iterate(d);
                break;
            case 's':
                sMan_s.iterate(d);
                break;
            default:
                sMan_S.iterate(d);
                break;
        }
    }
}