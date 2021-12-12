package Audio;

import javax.sound.sampled.*;
import java.util.Objects;

/*! \class AudioPlayer
    \brief Implementeaza partea audio a proiectului.
 */
public class AudioPlayer {

    private Clip clip;

    /*! \fn public AudioPlayer(String s)
        \brief Constructorul de initializare al clasei.
        \param s path-ul catre imaginea pentru background.
     */
    public AudioPlayer(String s) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResourceAsStream(s)));
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*! \fn public void play()
        \brief Porneste redarea unui sunet.
     */
    public void play() {
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /*! \fn public void stop()
        \brief Opreste redarea unui sunet.
     */
    public void stop() {
        if(clip.isRunning()) clip.stop();
    }
}