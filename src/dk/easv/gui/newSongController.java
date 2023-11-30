package dk.easv.gui;

import dk.easv.bll.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class newSongController {

    public Button btnClose;
    public Button btnSave;
    @FXML
    private TextField titleField;

    @FXML
    private TextField artistField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField timeField;

    @FXML
    private TextField filePathField;

    @FXML
    private void saveSong() {
        System.out.println("save song: start");
        try (Connection connection = DatabaseConnection.getConn()) {
            String sql = "INSERT INTO Songs (Title, Artist, Category, Time, file_path) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, titleField.getText());
                statement.setString(2, artistField.getText());
                statement.setString(3, categoryField.getText());
                statement.setString(4, timeField.getText());
                statement.setString(5, filePathField.getText());

                statement.executeUpdate();
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeWin(ActionEvent actionEvent) {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}