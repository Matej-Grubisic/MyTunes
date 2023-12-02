package dk.easv.gui;

import dk.easv.bll.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static jdk.jfr.consumer.EventStream.openFile;

public class newSongController {
    private final Desktop desktop = Desktop.getDesktop();


    public MenuButton btnDrop;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnChoose;
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
                statement.setString(3, btnDrop.getText());
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

    @FXML
    private void closeWin(ActionEvent actionEvent) {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getPop(ActionEvent actionEvent) {
        System.out.println("Pop");
        btnDrop.setText("Pop");
    }

    @FXML
    private void getHop(ActionEvent actionEvent) {
        System.out.println("Hop");
        btnDrop.setText("Hip Hop");
    }

    @FXML
    private void getRap(ActionEvent actionEvent) {
        System.out.println("Rap");
        btnDrop.setText("Rap");
    }

    @FXML
    private void getRock(ActionEvent actionEvent) {
        System.out.println("Rock");
        btnDrop.setText("Rock");

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


}