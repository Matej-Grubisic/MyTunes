package dk.easv.dal;
import dk.easv.be.Playlist;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistDAO {
    public Playlist getPlaylist(int id) throws SQLException;
    public void deletePlaylist(int id);
    public void updatePlaylist(Playlist pLaylist);
    public int createPlaylist(String newName);
    public List<Playlist> getAllPlaylists();
}