package dk.easv.dal;

import dk.easv.be.Song;
import dk.easv.bll.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO implements ISongDAO {

    @Override
    public Song getSong(int id) throws SQLException {
        try(Connection conn = DatabaseConnection.getConn()){
            String sql = "SELECT * FROM Songs1 WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int sid = rs.getInt("id");
                String title = rs.getString("Name");
                String filepath = rs.getString("FilePath");
                String time = rs.getString("Time");
                int artist = rs.getInt("IDArtist");
                int category = rs.getInt("IDCategory");

                Song s = new Song(sid, title,artist,category, time, filepath);
                return s;
            }
            return null;
        }
    }

    @Override
    public void deleteSong(int id) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "DELETE FROM Songs1 WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSong(Song s) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "UPDATE Songs1 SET Name=?, FilePath=?, Time=?, IDArtist=?, IDCategory=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, s.getTitle());
            pstmt.setString(2, s.getFilepath());
            pstmt.setString(3, s.getTime());
            pstmt.setInt(4, s.getArtist());
            pstmt.setInt(5, s.getCategory());
            pstmt.setInt(6, s.getId());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void createSong(Song s) {
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "INSERT INTO Songs1(Name, FilePath,Time, IDArtist, IDCategory) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, s.getTitle());
            pstmt.setString(2, s.getFilepath());
            pstmt.setString(3, s.getTime());
            pstmt.setInt(4, s.getArtist());
            pstmt.setInt(5, s.getCategory());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();

        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "SELECT * FROM Songs1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("IDSong");
                String title = rs.getString("Name");
                String filepath = rs.getString("FilePath");
                String time = rs.getString("Time");
                int artist = rs.getInt("IDArtist");
                int category = rs.getInt("IDCategory");

                Song s = new Song(id,title,artist,category,filepath,time);
                songs.add(s);
            }
            return songs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
