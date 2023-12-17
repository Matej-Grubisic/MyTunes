package dk.easv.dal;

import dk.easv.be.Song;

import java.sql.SQLException;
import java.util.List;

public interface ISongDAO {
    public Song getSong(int id) throws SQLException;
    public Song getSong1(String filepath, String title) throws SQLException;
    public void deleteSong(int id);
    public void updateSong(Song s);
    public void createSong(Song s);
    public List<Song> getAllSongs();
}
