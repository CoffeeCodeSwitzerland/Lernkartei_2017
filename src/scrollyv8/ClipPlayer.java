package scrollyv8;
// Thanks to Chua-Hock-Chuan for this code 
// http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum ClipPlayer {

    //SQUISH("C:\\Users\\Andrew\\Documents\\Prog\\Java\\ScrollyV7\\Sounds\\squash.wav"),  
    SQUISH("GameFiles/Sounds/squash.wav"), // Apparently netbeans defince the codebase as the project's main folder    
    DIE("GameFiles/Sounds/death.wav"),          // ... when porting to a jar file, this should be referenced to the jar.
    DEATH_ROBOB("GameFiles/Sounds/death_robob.wav"),
    DEATH_GREAPER("GameFiles/Sounds/death_greaper.wav"),
    DEATH_GATOR("GameFiles/Sounds/death_gator.wav"),
    DEATH_BOSS("GameFiles/Sounds/death_boss.wav"),
    BOSS_HIT("GameFiles/Sounds/boss_hit.wav");

    // Nested class for specifying volume
    public static enum Volume {

        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    // Constructor to construct each element of the enum with its own sound file.
    ClipPlayer(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = new File(soundFileName).toURI().toURL();
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play or Re-play the sound effect from the beginning, by rewinding.
    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning()) {
                clip.stop();   // Stop the player if it is still running
            }
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
    }

    // Optional static method to pre-load all the sound files.
    static void init() {
        values(); // calls the constructor for all the elements
    }
}
