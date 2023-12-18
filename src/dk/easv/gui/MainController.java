package dk.easv.gui;
import dk.easv.be.Playlist;
import dk.easv.be.Song;
import dk.easv.dal.ArtistDAO;
import dk.easv.dal.PlaylistDAO;
import dk.easv.dal.SongDAO;
import dk.easv.gui.otherControllers.EditPlaylistController;
import dk.easv.gui.otherControllers.EditSongController;
import dk.easv.gui.otherControllers.NewPlaylistController;
import dk.easv.gui.otherControllers.NewSongController;
import dk.easv.gui.sharedClasses.PlaylistTable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DecimalFormat;


import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static dk.easv.bll.DatabaseConnection.getConn;

public class MainController {
    private PlaylistTable playlistTableSingle = PlaylistTable.getInstance();

    boolean isPlaying;
    public Label lblMain;
    public Button btnPlaylistN;
    public Button btnPlaylistE;
    public Button btnSongE;
    public ListView songList;
    public TableView<Song> tableSong;
    public TableColumn<Song,String> colTitle;
    public TableColumn<Song,String> colArtist;
    public TableColumn<Song, String> colCategory;
    public TableColumn<Song, Integer> IDcol;
    // Playlist table
    public TableView<Playlist> tablePlaylist1;
    public TableColumn<Playlist, String> colTitle1;

    public TextField songSearchI;
    @FXML
    private Button btnSongN;
    private final PlaylistDAO PlaylistDAO = new PlaylistDAO();
    private final ArtistDAO ArtistDAO = new ArtistDAO();
    
    private ArrayList<Song> mySongs = new ArrayList<>();

    private final SongDAO SongDAO = new SongDAO();
    public Label labelPlaying;
    public Button forwardButton;
    public Button previousButton;
    public Label totalDuration;
    public Label currentDuration;
    public ToggleButton pauseButton;
    public ToggleButton playButton;
    public Slider timeSlider;

    private final ToggleGroup group = new ToggleGroup();

    private MediaPlayer player;

    private final DecimalFormat formatter = new DecimalFormat("00.00");
    private Duration totalTime;

    public Slider sliderBtn;
    public Label lblSong;

    private Song s;
    private Playlist playlist;
    int indexS;

    boolean status = false;
    Iterator<String> itr;

    @FXML
    private void initialize(){
        getSongsOrder();
        setTable(mySongs);
        playlistIni();

        playlistTableSingle.setTable(tablePlaylist1);
    }
    private void setTable(ArrayList<Song> songList){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("ArtistString"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        IDcol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableSong.getItems().clear();
        for(Song val : songList ){
            tableSong.getItems().add(val);
        }
    }

    private void songFilter(String search){
        List<Song> searchSongList = new ArrayList<>();
        List<Song> filteredSongs = mySongs.stream()
                .filter(song -> search == null || isSimilar(song.getTitle().toLowerCase(), search.toLowerCase()))
                .collect(Collectors.toList());
        ArrayList<Song> filteredArrayList = new ArrayList<>(filteredSongs);
        setTable(filteredArrayList);
    }

    public void searchSong(ActionEvent actionEvent) {
        songFilter(songSearchI.getText());
    }

    private boolean isSimilar(String str1, String str2) {
        return str1.contains(str2);
    }
    private void getSongsOrder() {
        try (Connection con = getConn()) {
            String sql = "SELECT * FROM Songs1 ORDER BY IDSong";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Integer id = rs.getInt("IDSong");
                String Title = rs.getString("Name");
                int Artist = rs.getInt("IDArtist");
                int Category = rs.getInt("IDCategory");
                String Time = rs.getString("Time");
                String File = rs.getString("FilePath");
                System.out.print(ArtistDAO.getArtist1(Artist) + ", " + Artist + '\n');
                String artist5 = ArtistDAO.getArtist1(Artist);
                String categoryName = getCategoryName(Category);
                System.out.println("Category:" + Category);
                System.out.println("Category name:" + categoryName);
                Song s = new Song(Title, artist5, categoryName, id);
                mySongs.add(s);

            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @FXML
        private void playlistIni() {
            List<Playlist> allPlaylist = PlaylistDAO.getAllPlaylists();
            colTitle1.setCellValueFactory(new PropertyValueFactory<>("PlaylistName"));

            for(dk.easv.be.Playlist value : allPlaylist){
                tablePlaylist1.getItems().add(value);
            }
        }


    @FXML
    private void newSong(ActionEvent actionEvent) throws IOException {

        System.out.println("Weeeeeeeee!");
        FXMLLoader loader1 = new FXMLLoader(
                getClass().getResource("fxmlFiles/NewSong.fxml")
        );
        Parent root = loader1.load();
        NewSongController newSongController = loader1.getController();
        newSongController.setParentController(this);
        Stage addStage = new Stage();
        addStage.setScene(new Scene(root));
        addStage.setTitle("New Song");
        addStage.show();
    }

    @FXML
    private void editSong(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(
                getClass().getResource("fxmlFiles/EditSong.fxml")
        );
        Parent root = loader2.load();
        EditSongController editSongController = loader2.getController();
        Song s = tableSong.getSelectionModel().getSelectedItem();
        editSongController.songID(s);
        tableSong.setEditable(true);
        tableSong.getItems().remove(s);
        tableSong.setEditable(false);
        editSongController.setParentController(this);
        Stage addStage = new Stage();
        addStage.setScene(new Scene(root));
        addStage.setTitle("Edit Song");
        addStage.show();
    }

    @FXML
    private void deleteSong(ActionEvent actionEvent) throws IOException{
        Song s = tableSong.getSelectionModel().getSelectedItem();
        int id = s.getId();
        System.out.println(id);
        SongDAO.deleteSong(id);
        tableSong.setEditable(true);
        tableSong.getItems().remove(s);
        tableSong.setEditable(false);
    }

    @FXML
    private void newPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(
                getClass().getResource("fxmlFiles/NewPlaylist.fxml")
        );
        Parent root = loader2.load();
        Stage addStage = new Stage();
        addStage.setScene(new Scene(root));
        addStage.setTitle("New Playlist");
        addStage.show();
    }

    @FXML
    private void editPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(
                getClass().getResource("fxmlFiles/EditPlaylist.fxml")
        );
        Parent root = loader2.load();
        EditPlaylistController editPlaylistController = loader2.getController();
        Playlist p = tablePlaylist1.getSelectionModel().getSelectedItem();
        editPlaylistController.playlistId(p);
        tablePlaylist1.setEditable(true);
        tablePlaylist1.getItems().remove(p);
        tablePlaylist1.setEditable(false);
        editPlaylistController.setParentController(this);
        Stage addStage = new Stage();
        addStage.setScene(new Scene(root));
        addStage.setTitle("Edit Playlist");
        addStage.show();
    }

    public void setPlaylistData(Playlist p) throws SQLException {
        colTitle1.setCellValueFactory(new PropertyValueFactory<>("PlaylistName"));
        tablePlaylist1.getItems().add(p);
    }

    public void setPlaylistDataEdit(Playlist p) throws SQLException {
        colTitle1.setCellValueFactory(new PropertyValueFactory<>("PlaylistName"));
        tablePlaylist1.getItems().add(p);
    }

    @FXML
    private void deletePlaylist(ActionEvent actionEvent) throws IOException{
        Playlist playlist = tablePlaylist1.getSelectionModel().getSelectedItem();
        int id = playlist.getId();
        System.out.println(id);
        PlaylistDAO.deletePlaylist(id);
        tablePlaylist1.setEditable(true);
        tablePlaylist1.getItems().remove(playlist);
    }
    private String getCategoryName(int categoryID){
        return switch (categoryID) {
            case 1 -> "Pop";
            case 2 -> "Hip Hop";
            case 3 -> "Rap";
            case 4 -> "Rock";
            default -> "None";
        };
    }
    public void setSongData(Song s) throws SQLException {
        String filepath = s.getFilepath();
        String title = s.getTitle();
        Song s1 = SongDAO.getSong1(filepath, title);
        Song s2 = new Song(s1.getTitle(), s.getArtistString(), s.getCategoryName(), s1.getId());
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("ArtistString"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        IDcol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableSong.getItems().add(s2);
    }

    public void setSongDataEdit(Song s) throws SQLException {
        Song s1 = SongDAO.getSong(s.getId());
        Song s2 = new Song(s1.getTitle(), s.getArtistString(), s.getCategoryName(), s1.getId());
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("ArtistString"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
        IDcol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableSong.getItems().add(s2);
    }

    public void playSong() throws SQLException, IOException {

        Song s = tableSong.getSelectionModel().getSelectedItem();
        //indexS then do table.get(indexS)
        //then you can get the id and the filepaths for the songs
        //You then have to make this shit go foreach element and go to the next after finishing
        //then done.

        //table.get(s);
        labelPlaying.setText(s.getTitle() + " is playing");
        List<Song> table = tableSong.getItems();
        List<String> list = new ArrayList<String>();
        for (Song i:table){
            System.out.println(i);
            Song s1 = SongDAO.getSong(i.getId());
            String filepath = s1.getFilepath();
            list.add(filepath);
        }
        itr = list.iterator();
        System.out.println(list);
        /*
        indexS = table.indexOf(s);

        */
        onStartSong(itr.next());
    }

    public void playNext(){

    }

    public void playPrevious(){

    }

    private void onStartSong(String filepath) throws SQLException {
        Media pick = new Media(new File(filepath).toURI().toString());
        player = new MediaPlayer(pick);
        player.play();
        player.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                player.stop();
                if(itr.hasNext()){
                    try {
                        onStartSong(itr.next());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                currentDuration.setText(String.valueOf(formatter.format(newValue.toSeconds())));
            }
        });



        player.setOnReady(() -> {
            totalTime = player.getMedia().getDuration();
            totalDuration.setText(" / " + String.valueOf(formatter.format(Math.floor(totalTime.toSeconds()))));
        });

        playButton.setToggleGroup(group);
        pauseButton.setToggleGroup(group);

        playButton.setOnAction(e -> {
            play();
            isPlaying = true;
            playButton.setSelected(true);
        });

        pauseButton.setOnAction(e -> {
            pause();
            isPlaying = false;
            pauseButton.setSelected(true);
        });
    }

    private void play(){
        player.play();
        playButton.setSelected(true);
    }

    private void pause(){
        player.pause();
        playButton.setSelected(false);
    }

}