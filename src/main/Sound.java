package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/** Handling sound effects
 */
public class Sound {
    /** Maximum number of sounds that can hold the array
     */
    private static final int MAX_SOUND_URL = 15; // Maximum number of sounds can be stored for the game

    /** Starts and stops sound effects
     */
    Clip clip; // It has start, stop for sounds
    /** Stores sound effects
     */
    URL[] soundURL; // Stores the sounds(wav) of the game (songs,and sound-effects)

    /** Creates sounds array, and loads in sound effects from "res" directory
     */
    public Sound(){
        soundURL = new URL[MAX_SOUND_URL];
        // Main song
        soundURL[0] = getClass().getClassLoader().getResource("sound/mainsong.wav");
        // Step sound-effect
        soundURL[1] = getClass().getClassLoader().getResource("sound/step.wav");
        // Keys pickup sound-effect
        soundURL[2] = getClass().getClassLoader().getResource("sound/pickup.wav");
        // Cell door opening/close sound-effect
        soundURL[3] = getClass().getClassLoader().getResource("sound/cell_door_opening.wav");
        soundURL[4] = getClass().getClassLoader().getResource("sound/swing.wav");
        soundURL[5] = getClass().getClassLoader().getResource("sound/hurt.wav");
        soundURL[6] = getClass().getClassLoader().getResource("sound/death.wav");
        soundURL[7] = getClass().getClassLoader().getResource("sound/denied.wav");
        soundURL[8] = getClass().getClassLoader().getResource("sound/zap.wav");
        soundURL[9] = getClass().getClassLoader().getResource("sound/whistle.wav");
        soundURL[10] = getClass().getClassLoader().getResource("sound/menu_step.wav");
        soundURL[11] = getClass().getClassLoader().getResource("sound/menu_select.wav");
        soundURL[12] = getClass().getClassLoader().getResource("sound/gameover.wav");
        soundURL[13] = getClass().getClassLoader().getResource("sound/escaped.wav");
        soundURL[14] = getClass().getClassLoader().getResource("sound/bed.wav");
    }

    /** Cretes clip of sound with the given index, the index says which sound in array
     * @param i - index of sound in sound array
     */
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

    /** Plays sound-effect
     */
    public void play(){
        clip.start();
    } // Plays the sound
}
