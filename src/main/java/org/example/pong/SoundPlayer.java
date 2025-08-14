package org.example.pong;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private static Clip clip;
    public static boolean audio=true;



    public static void playSound(String filePath) {
        try {
            if(audio){
                File soundFile = new File(filePath);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

                clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        }
        public static void stopSound() {
            audio = false; // mute para futuras reproducciones
            if(clip != null && clip.isRunning()) { // detener sonido actual
                clip.stop();
                clip.close();
                clip = null;
            }
    }
    public static void unmute() { // para volver a activar el sonido
        audio = true;
    }
}