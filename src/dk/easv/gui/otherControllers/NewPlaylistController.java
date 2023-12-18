package dk.easv.gui.otherControllers;
import dk.easv.be.Playlist;
import dk.easv.dal.PlaylistDAO;
import dk.easv.gui.sharedClasses.PlaylistTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

public class NewPlaylistController {
    private PlaylistTable playlistTableSingle = PlaylistTable.getInstance();

    private final Desktop desktop = Desktop.getDesktop();
    public TextField playlistNameField;
    public Button cancelButton;
    @FXML
    private Button saveButton;
    
    private final PlaylistDAO PlaylistDAO = new PlaylistDAO();

    @FXML
    private void savePlaylist() {
        String playlistName = playlistNameField.getText();
        int playlistID = PlaylistDAO.createPlaylist(playlistName);
        Playlist playlist = new Playlist(playlistID, playlistName);

        playlistTableSingle.getPlaylistTable().getItems().add(playlist);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeWin(ActionEvent actionEvent) {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
