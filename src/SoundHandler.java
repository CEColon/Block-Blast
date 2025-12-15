import java.io.*;
import javax.sound.sampled.*;

public class SoundHandler {

    public SoundHandler(){}

    public void play(String filename){
        new Thread(new Runnable() {
            public void run() {
                try {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());
                    Clip clp = AudioSystem.getClip();
                    clp.open(ais);
                    clp.start();
                }
                catch(Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
}