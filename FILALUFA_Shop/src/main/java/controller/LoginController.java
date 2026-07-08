package controller;

import connection.DBConnect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    DBConnect db = new DBConnect();

    CallableStatement cs;

    ResultSet rs;
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnKeluar;

    //==================================================
    // INITIALIZE
    //==================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtUsername.requestFocus();

    }

    //==================================================
    // LOGIN
    //==================================================

    @FXML
    private void login(ActionEvent event) {

        if (!validation()) {
            return;
        }

        try {

            cs = db.conn.prepareCall("{call sp_Login(?,?)}");

            cs.setString(1, txtUsername.getText());
            cs.setString(2, txtPassword.getText());

            rs = cs.executeQuery();

            if (rs.next()) {

                String nama = rs.getString("Nama_User");
                String status = rs.getString("Status");
                String role = rs.getString("Role");

                if (!status.equalsIgnoreCase("Aktif")) {

                    alertWarning("Akun Anda tidak aktif.\nSilakan hubungi Administrator.");
                    return;

                }

                alertInformation("Selamat Datang " + nama);

                if (role.equalsIgnoreCase("Admin")) {

                    openDashboardAdmin();

                } else if (role.equalsIgnoreCase("Kasir")) {

                    openDashboardKasir();

                } else {

                    alertError("Role tidak dikenali.");

                }

            } else {

                alertError("Username atau Password salah!");

            }

        } catch (Exception e) {

            alertError(e.getMessage());

        }

    }

    //==================================================
    // VALIDASI INPUT
    //==================================================

    private boolean validation() {

        if (txtUsername.getText().trim().isEmpty()) {

            alertWarning("Username tidak boleh kosong.");

            txtUsername.requestFocus();

            return false;

        }

        if (txtPassword.getText().trim().isEmpty()) {

            alertWarning("Password tidak boleh kosong.");

            txtPassword.requestFocus();

            return false;

        }

        return true;

    }

    //==================================================
    // KELUAR APLIKASI
    //==================================================

    @FXML
    private void keluar(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText(null);

        alert.setContentText("Apakah Anda yakin ingin keluar dari aplikasi?");

        Optional<ButtonType> pilih = alert.showAndWait();

        if (pilih.isPresent() && pilih.get() == ButtonType.OK) {

            System.exit(0);

        }

    }

    //==================================================
    // DASHBOARD ADMIN
    //==================================================

    private void openDashboardAdmin() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/view/DashboardAdmin.fxml"));

            Stage stage = (Stage) btnLogin.getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.setTitle("FILALUFA Shop - Admin");

            stage.centerOnScreen();

            stage.show();

        } catch (IOException e) {

            alertError(e.getMessage());

        }

    }

    //==================================================
    // DASHBOARD KASIR
    //==================================================

    private void openDashboardKasir() {

        try {

            Parent root = FXMLLoader.load(
                    getClass().getResource("/view/DashboardKasir.fxml"));

            Stage stage = (Stage) btnLogin.getScene().getWindow();

            Scene scene = new Scene(root);

            stage.setScene(scene);

            stage.setTitle("FILALUFA Shop - Kasir");

            stage.centerOnScreen();

            stage.show();

        } catch (IOException e) {

            alertError(e.getMessage());

        }

    }

    //==================================================
    // ALERT INFORMATION
    //==================================================

    private void alertInformation(String pesan) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

    //==================================================
    // ALERT WARNING
    //==================================================

    private void alertWarning(String pesan) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Peringatan");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

    //==================================================
    // ALERT ERROR
    //==================================================

    private void alertError(String pesan) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        alert.showAndWait();

    }

}