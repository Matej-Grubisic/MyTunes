package dk.easv.dal;

import java.util.HashMap;

public interface IArtistDAO {
    public  HashMap<String, Integer> getArtist();

    public HashMap<Integer, String> getArtist1();

    public Integer checkArtist(String artist, HashMap<String,Integer> artists);
    public void createArtist(String artist);
}
