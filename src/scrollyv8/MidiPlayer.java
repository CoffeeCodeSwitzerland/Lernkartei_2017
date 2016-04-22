package scrollyv8;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;

public class MidiPlayer
{

    private String currentTrack;
    private Sequence song;
    private Sequencer player;
    private boolean loaded;

    public MidiPlayer(String path, boolean loop)
    {
        setTrack(path,loop);
        loaded = false;
        try
        {
            player = MidiSystem.getSequencer();
        } catch (MidiUnavailableException ex)
        {
            System.out.println("Could not initialize sequencer");
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean setTrack(String path, boolean loop)
    {
        if (!(player==null) && player.isRunning())
        {
            player.stop();
        }

        try
        {
            player = MidiSystem.getSequencer();
        } catch (MidiUnavailableException ex)
        {
            System.out.println("Could not initialize sequencer");
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            song = MidiSystem.getSequence(new File(path));
            player.setSequence(song);
            if(loop) player.setLoopCount(10);
            try
            {
                player.open();
            } catch (MidiUnavailableException ex)
            {
                Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            loaded = true;
        } catch (InvalidMidiDataException ex)
        {
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex)
        {
            Logger.getLogger(MidiPlayer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

    public boolean isLoaded()
    {
        return loaded;
    }

    public boolean play()
    {
        if (loaded)
        {
            if (!player.isRunning())
            {
                player.start();
            }
            return true;
        } else
        {
            return false;
        }
    }

    public void stop()
    {
        if (player.isRunning())
        {
            player.stop();
        }
    }
}