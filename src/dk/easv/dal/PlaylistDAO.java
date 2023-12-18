package dk.easv.dal;

import dk.easv.be.Playlist;
import dk.easv.bll.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO{

    @Override
    public Playlist getPlaylistfromID(int id) throws SQLException {
        try(Connection conn = DatabaseConnection.getConn()){
            String sql = "SELECT IDPlaylist, PlaylistName FROM Playlist WHERE IDPlaylist=?";
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
    public Playlist getPlaylistfromName(String name) throws SQLException {
        try(Connection conn = DatabaseConnection.getConn()){
            String sql = "SELECT IDPlaylist, PlaylistName FROM Playlist WHERE PlaylistName=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
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
            String sql = "DELETE FROM Playlist WHERE IDPlaylist=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlaylist(String name, int id) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "UPDATE Playlist SET PlaylistName=? WHERE IDPlaylist=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int createPlaylist(String newName) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "INSERT INTO Playlist(PlaylistName) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newName);;
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating playlist failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating playlist failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlistList = new ArrayList<>();
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "SELECT * FROM Playlist";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("IDPlaylist");
                String playlistName = rs.getString("PlaylistName");
                Playlist playlist = new Playlist(id,playlistName);
                playlistList.add(playlist);
            }
            return playlistList;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
