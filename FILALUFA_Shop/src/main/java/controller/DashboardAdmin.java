package controller;
import connection.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.control.ButtonType;

public class DashboardAdmin implements Initializable {

    //=========================================================
    // DATABASE
    //=========================================================

    DBConnect db = new DBConnect();

    //=========================================================
    // COMPONENT
    //=========================================================

    @FXML
    private StackPane contentPane;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnMasterUser;

    @FXML
    private Button btnMasterSupplier;

    @FXML
    private Button btnMasterProduk;

    @FXML
    private Button btnMasterPelanggan;

    @FXML
    private Button btnMasterJenisMember;

    @FXML
    private Button btnMasterDiskon;

    @FXML
    private Button btnPembelian;

    @FXML
    private Button btnLogout;

    //=========================================================
    // INITIALIZE
    //=========================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        dashboard(null);

    }

    //=========================================================
    // LOAD PAGE
    //=========================================================

    private void loadPage(String page) {

        try {

            URL url = getClass().getResource(page);

            if (url == null) {
                alertError("FXML tidak ditemukan : " + page);
                return;
            }

            FXMLLoader loader = new FXMLLoader(url);

            Parent root = loader.load();

            contentPane.getChildren().setAll(root);

        } catch (Exception e) {
            e.printStackTrace();
            alertError(e.toString());

        }

    }
    //=========================================================
    // DASHBOARD
    //=========================================================

    @FXML
    private void dashboard(ActionEvent event) {

        contentPane.getChildren().clear();

    }

    @FXML
    private void masterUser(ActionEvent event) {

        loadPage("/view/MasterUser.fxml");

    }

    @FXML
    private void masterSupplier(ActionEvent event) {

        loadPage("/view/MasterSupplier.fxml");

    }

    @FXML
    private void masterProduk(ActionEvent event) {

        loadPage("/view/MasterProduk.fxml");

    }

    @FXML
    private void masterPelanggan(ActionEvent event) {

        loadPage("/view/MasterPelanggan.fxml");

    }

    @FXML
    private void masterJenisMember(ActionEvent event) {

        loadPage("/view/MasterJenisMember.fxml");

    }

    @FXML
    private void masterDiskon(ActionEvent event) {

        alertInformation("Master Diskon belum dibuat.");

    }

    @FXML
    private void transaksiPembelian(ActionEvent event) {

        alertInformation("Transaksi Pembelian belum dibuat.");

    }

    //=========================================================
    // LOGOUT
    //=========================================================

    @FXML
    private void logout(ActionEvent event) {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Konfirmasi");

            alert.setHeaderText(null);

            alert.setContentText("Apakah Anda yakin ingin Logout ?");

            Optional<ButtonType> pilih = alert.showAndWait();

            if (pilih.isPresent() && pilih.get() == ButtonType.OK) {

                Parent root = FXMLLoader.load(
                        getClass().getResource("/view/Login.fxml"));

                Stage stage =
                        (Stage) btnLogout.getScene().getWindow();

                Scene scene = new Scene(root);

                stage.setScene(scene);

                stage.setTitle("Login FILALUFA SHOP");

                stage.centerOnScreen();

                stage.show();

            }

        }

        catch (Exception e) {

            alertError(e.getMessage());

        }

    }

    //=========================================================
    // ALERT INFORMATION
    //=========================================================

    private void alertInformation(String pesan) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

    //=========================================================
    // ALERT WARNING
    //=========================================================

    private void alertWarning(String pesan) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Peringatan");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

    //=========================================================
    // ALERT ERROR
    //=========================================================

    private void alertError(String pesan) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }



}
