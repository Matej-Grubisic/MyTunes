package dk.easv.gui;

import dk.easv.be.Song;
import dk.easv.bll.DatabaseConnection;
import dk.easv.dal.ArtistDAO;
import dk.easv.dal.SongDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class newSongController {
    private final Desktop desktop = Desktop.getDesktop();

    public MenuButton btnDrop;

    @FXML
    private Button btnSave;

    @FXML
    private TextField titleField;

    @FXML
    private TextField artistField;



    @FXML
    private TextField timeField;

    @FXML
    private TextField filePathField;

    private final SongDAO SongDAO = new SongDAO();
    private final ArtistDAO ArtistDAO = new ArtistDAO();

    @FXML
    private void saveSong() {
        String title = titleField.getText();
        String artist = artistField.getText();
        int idArtist;
        HashMap<String, Integer> artists = ArtistDAO.getArtist();
        while(ArtistDAO.checkArtist(artist, artists) < 0){
            ArtistDAO.createArtist(artist);
            artists = ArtistDAO.getArtist();
        }
        idArtist = ArtistDAO.checkArtist(artist, artists);
        int category = getCategory(btnDrop.getText());
        String time = timeField.getText();
        String filepath = filePathField.getText();
        Song s = new Song(title,idArtist,category,time,filepath);
        SongDAO.createSong(s);
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
        System.out.println(s);
    }

    @FXML
    private void closeWin(ActionEvent actionEvent) {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    private int getCategory(String category){
        return switch (category) {
            case "Pop" -> 1;
            case "Hip Hop" -> 2;
            case "Rap" -> 3;
            case "Rock" -> 4;
            case null, default -> 0;
        };
    }

    @FXML
    private void chooseFile(ActionEvent actionEvent) throws UnsupportedAudioFileException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Window stage = new Stage();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println();
            filePathField.setText(String.valueOf(file));
        }

    }

    private static void configureFileChooser(final FileChooser fileChooser){
        fileChooser.setTitle("View songs");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("mp3", "*.mp3")
        );
    }

    public void getPop(ActionEvent actionEvent) {
        btnDrop.setText("Pop");
    }

    public void getHop(ActionEvent actionEvent) {
        btnDrop.setText("Hip Hop");
    }

    public void getRap(ActionEvent actionEvent) {
        btnDrop.setText("Rap");
    }

    public void getRock(ActionEvent actionEvent) {
        btnDrop.setText("Rock");
    }
}