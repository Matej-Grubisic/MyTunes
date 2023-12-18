package dk.easv.gui.otherControllers;

import dk.easv.be.Playlist;
import dk.easv.be.Song;
import dk.easv.dal.PlaylistDAO;
import dk.easv.gui.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.HashMap;

public class EditPlaylistController {
    public TextField playlistNameField;
    public Button cancelButton;
    public Button saveButton;
    private MainController MainController;
    private Playlist p;

    private final dk.easv.dal.PlaylistDAO PlaylistDAO = new PlaylistDAO();

    @FXML
    private void editPlaylist() throws SQLException {
        String name = playlistNameField.getText();
        Playlist playlist = PlaylistDAO.getPlaylistfromName(p.getPlaylistName());
        System.out.println(playlist.getPlaylistName() + " " + playlist.getId());
        PlaylistDAO.updatePlaylist(name, playlist.getId());
        Playlist newPlaylist = PlaylistDAO.getPlaylistfromID(playlist.getId());
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
        MainController.setPlaylistData(newPlaylist);
        System.out.println(p);
    }

    public void setParentController(MainController MainController){
        this.MainController = MainController;
    }

    public void playlistId(Playlist p) {
        this.p = p;
    }

    public void closeWin(ActionEvent actionEvent) throws SQLException {
        MainController.setPlaylistDataEdit(p);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
