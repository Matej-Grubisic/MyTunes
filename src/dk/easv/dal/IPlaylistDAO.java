package dk.easv.dal;
import dk.easv.be.Playlist;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistDAO {
    public Playlist getPlaylistfromID(int id) throws SQLException;
    public Playlist getPlaylistfromName(String name) throws SQLException;
    public void deletePlaylist(int id);
    public void updatePlaylist(String name, int id);
    public int createPlaylist(String newName);
    public List<Playlist> getAllPlaylists();
}