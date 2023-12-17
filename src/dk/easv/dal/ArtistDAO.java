package dk.easv.dal;

import dk.easv.bll.DatabaseConnection;

import java.sql.*;
import java.util.HashMap;

public class ArtistDAO implements IArtistDAO{
    @Override
    public HashMap<String, Integer> getArtist(){
        try (Connection connection1 = DatabaseConnection.getConn()) {
            String sql = "SELECT * FROM Artist1";
            Statement stmt = connection1.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            HashMap<String, Integer> artists = null;
            while (rs.next()) {
                int id = rs.getInt("IDArtist");
                String name = rs.getString("ArtistName");
                artists = new HashMap<String, Integer>();
                artists.put(name, id);
            }
            return artists;
            //return persons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getArtist1(int ArtistId){
        try (Connection connection1 = DatabaseConnection.getConn()) {
            String sql = "SELECT * FROM Artist1 WHERE IDArtist = ?";
            PreparedStatement pstmt = connection1.prepareStatement(sql);
            pstmt.setInt(1, ArtistId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //System.out.println("a");
                //int id = rs.getInt("IDArtist");
                String name = rs.getString("ArtistName");
                return name;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer checkArtist(String artist, HashMap<String,Integer> artists){
        if(artists == null){
            return -1;
        }
        else if(artists.containsKey(artist)){
            Integer idArtist = artists.get(artist);
            return idArtist;
        }
        else{
            return -1;
        }
    }

    @Override
    public void createArtist(String artist){
        try(Connection con = DatabaseConnection.getConn())
        {
            String sql = "INSERT INTO Artist1(ArtistName) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, artist);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
