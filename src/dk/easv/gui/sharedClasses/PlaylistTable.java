package dk.easv.gui.sharedClasses;

import dk.easv.be.Playlist;
import javafx.scene.control.TableView;

// THIS IS A SINGLETON

public class PlaylistTable {
    // Single instance of playlistTable
    private static final PlaylistTable instance = new PlaylistTable();

    // The shared the table
    private TableView<Playlist> tablePlaylist1;


    public void setTable(TableView<Playlist> playlistTable){
        tablePlaylist1 = playlistTable;
    }

    public TableView<Playlist> getPlaylistTable(){
        return tablePlaylist1;
    }

    public static PlaylistTable getInstance() {
        return instance;
    }
}
