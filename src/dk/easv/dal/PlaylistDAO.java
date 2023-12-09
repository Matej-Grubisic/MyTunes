package dk.easv.dal;

import dk.easv.be.Playlist;
import dk.easv.bll.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO{
    @Override
    public Playlist getPlaylist(int id) throws SQLException {
        try(Connection conn = DatabaseConnection.getConn()){
            String sql = "SELECT IDPlaylist, PlaylistName FROM Playlist WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int pid = rs.getInt("IDPlaylist");
                String playlistName = rs.getString("PlaylistName");
                Playlist playlist = new Playlist(pid, playlistName);
                return playlist;
            }
            return null;
        }
    }

    @Override
    public void deletePlaylist(int id) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "DELETE FROM Playlist WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlaylist(Playlist pLaylist) {

    }

    @Override
    public void createPlaylist(Playlist playlist) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "INSERT INTO Playlist(PlaylistName) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, playlist.getPlaylistName());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return null;
    }

}
