package dk.easv.gui;

import dk.easv.bll.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class newSongController {

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
        DatabaseConnection DatabaseUtil = null;
        try (Connection connection = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO Songs (title, artist, category, time, file_path) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, titleField.getText());
                statement.setString(2, artistField.getText());
                statement.setString(3, categoryField.getText());
                statement.setString(4, timeField.getText());
                statement.setString(5, filePathField.getText());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}