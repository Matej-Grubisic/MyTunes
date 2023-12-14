package dk.easv.gui;

import dk.easv.be.Song;
import dk.easv.dal.ArtistDAO;
import dk.easv.dal.SongDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

public class editSongController {
    public TextField titleField;
    public TextField artistField;
    public TextField timeField;
    public TextField filePathField;
    public Button btnClose;
    public Button btnSave;
    public MenuButton btnDrop;
    private MainController MainController;
    private Song s;
    private final dk.easv.dal.SongDAO SongDAO = new SongDAO();
    private final dk.easv.dal.ArtistDAO ArtistDAO = new ArtistDAO();



    public void setParentController(MainController MainController){
        this.MainController = MainController;
    }

    public void editSong(ActionEvent actionEvent) throws SQLException {
        int sID = s.getId();
        String title = titleField.getText();
        String artist = artistField.getText();
        int idArtist;
        HashMap<String, Integer> artists = ArtistDAO.getArtist();
        while(ArtistDAO.checkArtist(artist, artists) < 0){
            ArtistDAO.createArtist(artist);
            artists = ArtistDAO.getArtist();
        }
        idArtist = ArtistDAO.checkArtist(artist, artists);
        int category = getCategoryID(btnDrop.getText());
        String categoryName = btnDrop.getText();
        String time = timeField.getText();
        //Song s0 = SongDAO.getSong(sID);
        Song s = new Song(title,idArtist,category,time,sID);
        SongDAO.updateSong(s);
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
        Song sPath = SongDAO.getSong(sID);
        Song s1 = new Song(title,artist,categoryName, sID);
        MainController.setSongDataEdit(s1);
        System.out.println(s);
    }

    public void closeWin(ActionEvent actionEvent) throws SQLException {
        MainController.setSongDataEdit(s);
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getPop(ActionEvent actionEvent) {
        btnDrop.setText("Pop");
    }

    @FXML
    private void getHop(ActionEvent actionEvent) {
        btnDrop.setText("Hip Hop");
    }

    @FXML
    private void getRap(ActionEvent actionEvent) {
        btnDrop.setText("Rap");
    }

    @FXML
    private void getRock(ActionEvent actionEvent) {
        btnDrop.setText("Rock");
    }

    @FXML
    private int getCategoryID(String category){
        return switch (category) {
            case "Pop" -> 1;
            case "Hip Hop" -> 2;
            case "Rap" -> 3;
            case "Rock" -> 4;
            case null, default -> 0;
        };
    }

    public void songID(Song s){
        this.s = s;
    }
}
