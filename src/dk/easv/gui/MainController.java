package dk.easv.gui;
import dk.easv.be.Song;
import dk.easv.dal.ArtistDAO;
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
    public TableColumn<Song, String> colTime;
    public TableColumn<Song, String> colCategory;
    public TableColumn<Song, String> colFile;

    @FXML
    private Button btnSongN;

    private final ArtistDAO ArtistDAO = new ArtistDAO();

    @FXML
    private void initialize(){
        try(Connection con = getConn())
        {
            String sql = "SELECT * FROM Songs1 ORDER BY IDSong";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("IDSong");
                String Title = rs.getString("Name");
                int Artist = rs.getInt("IDArtist");
                int Category = rs.getInt("IDCategory");
                String Time = rs.getString("Time");
                String File = rs.getString("FilePath");
                System.out.print(ArtistDAO.getArtist1(Artist) + ", " + Artist+ '\n');
                String artist5 = ArtistDAO.getArtist1(Artist);
                String categoryName = getCategoryName(Category);
                System.out.println("Category:" + Category);
                System.out.println("Category name:"+categoryName);
                Song s = new Song(Title,artist5,categoryName);
                colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
                colArtist.setCellValueFactory(new PropertyValueFactory<>("ArtistString"));
                colCategory.setCellValueFactory(new PropertyValueFactory<>("CategoryName"));
                tableSong.getItems().add(s);
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

    private String getCategoryName(int categoryID){
        return switch (categoryID) {
            case 1 -> "Pop";
            case 2 -> "Hip Hop";
            case 3 -> "Rap";
            case 4 -> "Rock";
            default -> "None";
        };
    }
}
