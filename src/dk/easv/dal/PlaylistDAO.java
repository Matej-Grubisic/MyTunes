package dk.easv.dal;

import dk.easv.be.Playlist;
import dk.easv.be.Song;
import dk.easv.bll.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO{

    @Override
    public void deletePlaylist(int id) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "DELETE FROM Playlist WHERE IDPlaylist=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();

            // Delete Playlist Songs

            String sql2 = "DELETE FROM dbo.PlaylistSongs WHERE IDPlaylist=?";
            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, id);
            pstmt2.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePlaylist(String name, int id) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "UPDATE Playlist SET IDPlaylist=?, PlaylistName=?";
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

    public void addSongToPlaylist(Song selectedSong, Playlist selectedPlaylist) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "INSERT INTO  PlaylistSongs(SongOrder, IDSong, IDPlaylist) VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, 0);
            pstmt.setInt(2, selectedSong.getId());
            pstmt.setInt(3, selectedPlaylist.getId());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Integer> getSongToPlaylist(int playlistId) {
        ArrayList<Integer> songIds = new ArrayList<>();

        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "SELECT * FROM PlaylistSongs WHERE IDPlaylist = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, playlistId);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int SongID = rs.getInt("IDSong");
                songIds.add(SongID);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return songIds;
    }
}
