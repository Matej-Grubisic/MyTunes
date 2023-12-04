package dk.easv.gui;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static dk.easv.bll.DatabaseConnection.getConn;

public class MainController {




    public Label lblMain;
    public Button btnPlaylistN;
    public Button btnPlaylistE;
    public Button btnSongE;
    public ListView songList;
    public TableView tablePlaylist;
    public TableColumn title;
    public TableColumn artist;
    public TableColumn time;
    @FXML
    private Button btnSongN;

    @FXML
    private void initialize(){
        try(Connection con = getConn())
        {
            String sql = "SELECT * FROM Songs ORDER BY SongID";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("SongID");
                String Title = rs.getString("Title");
                String Artist = rs.getString("Artist");
                String Category = rs.getString("Category");
                String Time = rs.getString("Time");
                String File = rs.getString("file_path");
                lblMain.setText(Title + " " + "is now playing");
                System.out.println(id + ", "+ Title + ", " + Artist + ", " + Category + ", " + Time + ", " + File);
                listSong(Title, Artist, Category, Time, File);
                songList.setCellFactory(TextFieldListCell.forListView());
                songList.getItems().addAll(Title + " | " + Artist + " | " + Category + " | " + Time + " | " + File);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void newSong(ActionEvent actionEvent) throws IOException {

        System.out.println("Weeeeeeeee!");
        FXMLLoader loader1 = new FXMLLoader(
                getClass().getResource("newsong.fxml")
        );
        Parent root = loader1.load();
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

    @FXML
    private void listSong(String Title, String Artist, String Category, String Time, String File){
        //songList.setEditable(true);

    }


}
