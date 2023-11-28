import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        ds.setDatabaseName("MyTunesGroupG");
        ds.setUser("CSe2023b_e_17");
        ds.setPassword("CSe2023bE17#23");
        //ds.setPortNumber(1433);
        ds.setServerName("10.176.111.31");
        //ds.setTrustServerCertificate(true);
        System.out.println("Please work!");
        try(Connection con = ds.getConnection())
        {
            String sql = "CREATE TABLE Songs (SongID int, Title varchar(255), Artist varchar(255), Category varchar(255))";
            Statement stmt = con.createStatement();
            stmt.executeQuery(sql);
            lblMain.setText("works");
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
