package dk.easv.gui;
import dk.easv.be.Playlist;
import dk.easv.bll.DatabaseConnection;
import dk.easv.dal.PlaylistDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
public class newPlaylistController {
    private final Desktop desktop = Desktop.getDesktop();
    @FXML
    private Button saveButton;
    @FXML
    private TextField playlistNameField;
    private final PlaylistDAO PlaylistDAO = new PlaylistDAO();
    @FXML
    private void savePlaylist() {
        String playlistName = playlistNameField.getText();
        Playlist playlist = new Playlist(playlistName);
        PlaylistDAO.createPlaylist(playlist);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        System.out.println(playlist);
    }

    @FXML
    private void closeWin(ActionEvent actionEvent) {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
