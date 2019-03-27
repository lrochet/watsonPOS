package speech;
/**
 * @author lrochet
 * copyright IBM france 2018
 */

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;


public class PlaySound {
	
	
    public static void main (String args[]) throws Exception {
        playSound (args[0]);
    }

    public static void playSound (String filename) throws Exception {
        AudioInputStream 
        audioStream = AudioSystem.getAudioInputStream(new File (filename));

        int BUFFER_SIZE = 128000;
        AudioFormat audioFormat = null;
        SourceDataLine sourceLine = null;

        audioFormat = audioStream.getFormat();

        sourceLine = AudioSystem.getSourceDataLine(audioFormat);
        sourceLine.open(audioFormat);
        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = 
                audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (nBytesRead >= 0) {
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }


}