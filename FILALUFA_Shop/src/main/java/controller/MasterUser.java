package controller;

import connection.DBConnect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.sql.*;

import java.util.Optional;
import java.util.ResourceBundle;

public class MasterUser implements Initializable {

    //=========================================================
    // DATABASE
    //=========================================================

    DBConnect db = new DBConnect();

    Connection conn;

    Statement stat;

    PreparedStatement pst;

    ResultSet rs;

    //=========================================================
    // OBSERVABLE LIST
    //=========================================================

    ObservableList<User> listUser =
            FXCollections.observableArrayList();

    //=========================================================
    // TEXTFIELD
    //=========================================================

    @FXML
    private TextField txtIDUser;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtNamaUser;

    @FXML
    private TextField txtTelepon;

    @FXML
    private TextField txtEmail;

    //=========================================================
    // COMBOBOX
    //=========================================================

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnUbah;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnKembali;

    //=========================================================
    // TABLE VIEW
    //=========================================================

    @FXML
    private TableView<User> tblUser;

    @FXML
    private TableColumn<User, String> colId;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colNama;

    @FXML
    private TableColumn<User, String> colTelepon;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colStatus;

    @FXML
    private TableColumn<User, String> colRole;

    //=========================================================
    // INITIALIZE
    //=========================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = db.conn;

        initializeTable();

        initializeCombo();

        autoID();

        loadTable();

        tableClick();

    }

    //=========================================================
    // INISIALISASI TABLE
    //=========================================================

    private void initializeTable() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idUser"));

        colUsername.setCellValueFactory(
                new PropertyValueFactory<>("username"));

        colNama.setCellValueFactory(
                new PropertyValueFactory<>("namaUser"));

        colTelepon.setCellValueFactory(
                new PropertyValueFactory<>("telepon"));

        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        colRole.setCellValueFactory(
                new PropertyValueFactory<>("role"));

    }

    //=========================================================
    // COMBOBOX
    //=========================================================

    private void initializeCombo() {

        cmbStatus.getItems().addAll(

                "Aktif",
                "Tidak Aktif"

        );

        cmbRole.getItems().addAll(

                "Admin",
                "Kasir"

        );

    }
    //=========================================================
    // AUTO ID
    //=========================================================

    private void autoID() {

        try {

            String sql = "SELECT TOP 1 ID_User FROM Karyawan ORDER BY ID_User DESC";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {

                String id = rs.getString("ID_User").substring(2);

                int nomor = Integer.parseInt(id) + 1;

                txtIDUser.setText(String.format("US%03d", nomor));

            } else {

                txtIDUser.setText("US001");

            }

        } catch (Exception e) {

            alertError("Gagal membuat ID User : " + e.getMessage());

        }

    }

    //=========================================================
    // LOAD TABLE
    //=========================================================

    private void loadTable() {

        listUser.clear();

        try {

            String sql = "SELECT * FROM Karyawan ORDER BY ID_User ASC";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            while (rs.next()) {

                User user = new User();

                user.setIdUser(rs.getString("ID_User"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setNamaUser(rs.getString("Nama_User"));
                user.setTelepon(rs.getString("No_Telepon"));
                user.setEmail(rs.getString("Email"));
                user.setStatus(rs.getString("Status"));
                user.setRole(rs.getString("Role"));

                listUser.add(user);

            }

            tblUser.setItems(listUser);

        } catch (Exception e) {

            alertError("Gagal menampilkan data : " + e.getMessage());

        }

    }

    //=========================================================
    // REFRESH TABLE
    //=========================================================

    private void refreshTable() {

        listUser.clear();

        loadTable();

    }
    //=========================================================
    // KLIK DATA PADA TABLE
    //=========================================================

    private void tableClick() {

        tblUser.setOnMouseClicked(event -> {

            User user = tblUser.getSelectionModel().getSelectedItem();

            if (user != null) {

                txtIDUser.setText(user.getIdUser());
                txtUsername.setText(user.getUsername());
                txtPassword.setText(user.getPassword());
                txtNamaUser.setText(user.getNamaUser());
                txtTelepon.setText(user.getTelepon());
                txtEmail.setText(user.getEmail());

                cmbStatus.setValue(user.getStatus());
                cmbRole.setValue(user.getRole());

                btnSimpan.setDisable(true);
                btnUbah.setDisable(false);
                btnHapus.setDisable(false);

            }

        });

    }

    //=========================================================
    // BERSIHKAN FORM
    //=========================================================

    private void clearForm() {

        txtIDUser.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtNamaUser.clear();
        txtTelepon.clear();
        txtEmail.clear();

        cmbStatus.getSelectionModel().clearSelection();
        cmbRole.getSelectionModel().clearSelection();

        tblUser.getSelectionModel().clearSelection();

        autoID();

        btnSimpan.setDisable(false);
        btnUbah.setDisable(true);
        btnHapus.setDisable(true);

        txtUsername.requestFocus();

    }

    //=========================================================
    // AKTIFKAN TOMBOL CRUD
    //=========================================================

    private void enableButton() {

        btnSimpan.setDisable(false);
        btnUbah.setDisable(false);
        btnHapus.setDisable(false);

    }

    //=========================================================
    // KONDISI AWAL TOMBOL
    //=========================================================

    private void disableButton() {

        btnSimpan.setDisable(false);
        btnUbah.setDisable(true);
        btnHapus.setDisable(true);

    }
    //=========================================================
// SIMPAN DATA
//=========================================================

    @FXML
    private void saveUser(ActionEvent event){

        if(!validation()){

            return;

        }

        try{

            CallableStatement cs =
                    conn.prepareCall("{call sp_InsertKaryawan(?,?,?,?,?,?,?,?)}");

            cs.setString(1, txtIDUser.getText());
            cs.setString(2, txtUsername.getText());
            cs.setString(3, txtPassword.getText());
            cs.setString(4, txtNamaUser.getText());
            cs.setString(5, txtTelepon.getText());
            cs.setString(6, txtEmail.getText());
            cs.setString(7, cmbStatus.getValue());
            cs.setString(8, cmbRole.getValue());

            cs.execute();

            alertInformation("Data berhasil disimpan.");

            refreshTable();

            clearForm();

        }

        catch(Exception e){

            alertError(e.getMessage());

        }

    }
    //=========================================================
// UPDATE DATA
//=========================================================

    @FXML
    private void updateUser(ActionEvent event){

        if(!validation()){

            return;

        }

        try{

            CallableStatement cs =
                    conn.prepareCall("{call sp_UpdateKaryawan(?,?,?,?,?,?,?,?)}");

            cs.setString(1, txtIDUser.getText());
            cs.setString(2, txtUsername.getText());
            cs.setString(3, txtPassword.getText());
            cs.setString(4, txtNamaUser.getText());
            cs.setString(5, txtTelepon.getText());
            cs.setString(6, txtEmail.getText());
            cs.setString(7, cmbStatus.getValue());
            cs.setString(8, cmbRole.getValue());

            cs.execute();

            alertInformation("Data berhasil diubah.");

            refreshTable();

            clearForm();

        }

        catch(Exception e){

            alertError(e.getMessage());

        }

    }
    //=========================================================
// HAPUS DATA
//=========================================================

    @FXML
    private void deleteUser(ActionEvent event){

        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText(null);

        alert.setContentText("Yakin ingin menghapus data?");

        Optional<ButtonType> pilih =
                alert.showAndWait();

        if(pilih.isPresent() &&
                pilih.get()==ButtonType.OK){

            try{

                CallableStatement cs =
                        conn.prepareCall("{call sp_DeleteKaryawan(?)}");

                cs.setString(1, txtIDUser.getText());

                cs.execute();

                alertInformation("Data berhasil dihapus.");

                refreshTable();

                clearForm();

            }

            catch(Exception e){

                alertError(e.getMessage());

            }

        }

    }

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

        if (txtNamaUser.getText().trim().isEmpty()) {

            alertWarning("Nama User tidak boleh kosong.");

            txtNamaUser.requestFocus();

            return false;

        }

        if (txtTelepon.getText().trim().isEmpty()) {

            alertWarning("Nomor Telepon tidak boleh kosong.");

            txtTelepon.requestFocus();

            return false;

        }

        if (txtEmail.getText().trim().isEmpty()) {

            alertWarning("Email tidak boleh kosong.");

            txtEmail.requestFocus();

            return false;

        }

        if (cmbStatus.getValue() == null) {

            alertWarning("Status belum dipilih.");

            cmbStatus.requestFocus();

            return false;

        }

        if (cmbRole.getValue() == null) {

            alertWarning("Role belum dipilih.");

            cmbRole.requestFocus();

            return false;

        }

        return true;

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

        Alert alert = new Alert(Alert.AlertType.WARNING);

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

    //=========================================================
// KEMBALI KE DASHBOARD
//=========================================================

    @FXML
    private void backDashboard(ActionEvent event){

        try{

            Parent root =
                    FXMLLoader.load(getClass().getResource("/view/DashboardAdmin.fxml"));

            Stage stage =
                    (Stage) btnKembali.getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.setTitle("Dashboard Admin");

            stage.centerOnScreen();

            stage.show();

        }

        catch(Exception e){

            alertError(e.getMessage());

        }

    }
}
