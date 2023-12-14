package dk.easv.gui;
import dk.easv.be.Playlist;
import dk.easv.be.Song;
import dk.easv.dal.ArtistDAO;
import dk.easv.dal.PlaylistDAO;
import dk.easv.dal.SongDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dk.easv.bll.DatabaseConnection.getConn;

public class MainController {
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
    private Button btnSongN;
    private final PlaylistDAO PlaylistDAO = new PlaylistDAO();
    private final ArtistDAO ArtistDAO = new ArtistDAO();
    private final SongDAO SongDAO = new SongDAO();
    private ArrayList<Song> mySongs = new ArrayList<>();

    private Song s;

    @FXML
    private void initialize(){
        getSongs();
        setTable(mySongs);
        playlistIni();
    }
    private void setTable(ArrayList<Song> songList){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("ArtistString"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
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
    private void getSongs() {
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
                Song s = new Song(Title, Artist, Category, Time, File);
                mySongs.add(s);
                IDcol.setCellValueFactory(new PropertyValueFactory<>("Id"));
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
                getClass().getResource("newsong.fxml")
        );
        Parent root = loader1.load();
        newSongController newSongController = loader1.getController();
        newSongController.setParentController(this);
        Stage addStage = new Stage();
        addStage.setScene(new Scene(root));
        addStage.setTitle("New Song");
        addStage.show();
    }

    @FXML
    private void editSong(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader1 = new FXMLLoader(
                getClass().getResource("newsong.fxml")
        );
        Parent root = loader1.load();
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
    }

    @FXML
    private void newPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader2 = new FXMLLoader(
                getClass().getResource("newplaylist.fxml")
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
                getClass().getResource("newplaylist.fxml")
        );
        Parent root = loader2.load();
        Stage addStage = new Stage();
        addStage.setScene(new Scene(root));
        addStage.setTitle("Edit Playlist");
        addStage.show();
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
}
