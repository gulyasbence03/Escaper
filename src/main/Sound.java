package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {

    private static final int MAX_SOUND_URL = 10; // Maximum number of sounds can be stored for the game

    Clip clip; // It has start, stop and loop methods for sounds
    URL[] soundURL; // Stores the sounds(wav) of the game (songs,and sound-effects)

    public Sound(){
        soundURL = new URL[MAX_SOUND_URL];
        // Main song
        soundURL[0] = getClass().getClassLoader().getResource("sound/mainsong.wav");
        // Step sound-effect
        soundURL[1] = getClass().getClassLoader().getResource("sound/step.wav");
        // Keys pickup sound-effect
        soundURL[2] = getClass().getClassLoader().getResource("sound/keys_pickup.wav");
        // Cell door opening/close sound-effect
        soundURL[3] = getClass().getClassLoader().getResource("sound/cell_door_opening.wav");
    }

    public void setFile(int i){
        try{
            // Creates clip of i. number of sound from array of sounds
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e); // the AudioInputStream requires all of these
        }
    }
    public void play(){
        clip.start();
    } // Plays the sound
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    } // Loop the sound until it's stopped
    public void stop(){
        clip.stop();
    } // Stops the sound
}
