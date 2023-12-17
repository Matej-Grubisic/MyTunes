package dk.easv.gui.otherControllers;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MediaController {
    private MediaPlayer mPlayer;
    private File mySong;


    public void setMySong(File newSong){
        if(newSong != null){
            mPlayer = new MediaPlayer(new Media(mySong.toURI().toString()));
        }

        mySong = newSong;
    }

    public void playSong(){
        if(mPlayer == null){ return; }
        mPlayer.play();
    }
}
