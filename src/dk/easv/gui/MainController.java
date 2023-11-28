package dk.easv.gui;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {


    public Label lblMain;
    @FXML
    private Button btnNew;

    @FXML
    private void click(ActionEvent actionEvent) {
        System.out.println("Weeeeeeeee!");
        btnNew.setText("Works");
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("mytunes_groupg");
        ds.setUser("CSe2023b_e_6");
        ds.setPassword("CSe2023bE6#23");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.34");
        ds.setTrustServerCertificate(true);
        System.out.println("Please work!");
        try(Connection con = ds.getConnection())
        {
            String sql = "SELECT * FROM Songs ORDER BY SongID";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int id          = rs.getInt("SongID");
                String Title     = rs.getString("Title");
                String Artist    = rs.getString("Artist");
                String Category = rs.getString("Category");
                String Time = rs.getString("Time");
                lblMain.setText(Title + " " + "is now playing");
                System.out.println(id + ", "+ Title + ", " + Artist);
            }
        }
        catch (SQLServerException sqlse)
        {
            System.out.println(sqlse);
        }
        catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
