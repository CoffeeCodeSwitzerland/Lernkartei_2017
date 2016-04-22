package scrollyv8;

import java.awt.Graphics;

public class Grunt extends Enemy
{

    public char state;
    private SpriteManager sMan, sMan_d;

    public Grunt(double xs, double ys, double ws, double hs, String sPath)
    {
        super(xs, ys, ws, hs);
        alive = true;

        String[] fN =
        {
            sPath + "grunt_2.png", sPath + "grunt_1.png", sPath + "grunt_2.png", sPath + "grunt_3.png"
        };
        String[] fNd =
        {
            sPath + "grunt_d.png"
        };

        sMan = new SpriteManager(fN);
        sMan_d = new SpriteManager(fNd);
        state = 'r';

        hitPoints = 0;
    }

    public boolean addImage(String fN)
    {
        return false;
    }

    public void draw(Graphics g, double x0, double y0)
    {
        switch (state)
        {
            case 'r':
                g.drawImage(sMan.get(), (int) (x - x0), (int) (y - y0), (int) w, (int) h, null);
                break;
            case 'd':
                g.drawImage(sMan_d.get(), (int) (x - x0), (int) (y - y0 + w - 5), (int) w, (int) 5, null);
                break;
        }
    }

    public void iterate(double d, boolean collH, boolean collV, boolean willFall, double dVX, double dVY)
    {
        super.iterate(2.5 * d, collH, collV, willFall, dVX, dVY);
        sMan.iterate(2.5 * d);
    }

    public void kill()
    {
        alive = false;
        state = 'd';
    }
}