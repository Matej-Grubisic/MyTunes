package dk.easv.gui.otherControllers;

import dk.easv.be.Song;
import dk.easv.gui.sharedClasses.PlaylistTable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.List;

public class MediaController {
    // Single instance of playlistTable
    private static final MediaController instance = new MediaController();


    private MediaPlayer mPlayer;


    private List<Song> songList;




    public void playSong(){
        if(mPlayer == null){ return; }
        mPlayer.play();
    }

    public void setIniSongList(List<Song> newSongList){
        songList = newSongList;
        iniSongList();
    }



    public static MediaController getInstance() {
        return instance;
    }


    private void iniSongList() {
        if(songList == null || songList.size() < 1){ return; }

        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.dispose();
            System.gc();
            mPlayer = null;
        }


        if(songList.getFirst().getFilepath() != null){
            File songFile = new File( songList.getFirst().getFilepath() );
            if(songFile != null){
                mPlayer = new MediaPlayer(new Media( songFile.toURI().toString() ));
            }
        }
    }


    private void setMySong(File songFile){
        if(songFile != null){
            mPlayer = new MediaPlayer(new Media(songFile.toURI().toString()));
        }
    }
}
