package dk.easv.be;

public class Song {
    private String categoryName;
    private int id=-1;
    private String title;
    private int artist;
    private String artistString;
    private int category;
    private String time;
    private String filepath;

    public Song(String title, int artist, int category, String time, String filepath){
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
        this.filepath = filepath;
    }

    public Song(int id,String title, int artist, int category, String time, String filepath){
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
        this.filepath = filepath;
    }

    public Song(String title, String artistString, String categoryName, int id) {
        this.title = title;
        this.artistString = artistString;
        this.categoryName = categoryName;
        this.id = id;
    }

    public Song(String title, String artistString, String categoryName, String filepath) {
        this.title = title;
        this.artistString = artistString;
        this.categoryName = categoryName;
        this.filepath = filepath;
    }

    public Song(String title, int artist, int category, int sid) {
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.id = sid;
    }

    public Song(String title, int artist, int category, String time, int id) {
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
        this.id = id;
    }

    public Song(Integer id, String title, String artist5, String categoryName, String time, String file) {
        this.id = id;
        this.title = title;
        this.artistString = artist5;
        this.categoryName = categoryName;
        this.time = time;
        this.filepath = file;
    }


    public int getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }
    public int getArtist(){
        return artist;
    }
    public String getArtistString(){return artistString;}

    public String getCategoryName(){return categoryName;}

    public int getCategory(){
        return category;
    }
    public String getTime(){
        return time;
    }
    public String getFilepath(){
        return filepath;
    }
}
