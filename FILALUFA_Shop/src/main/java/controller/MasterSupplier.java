package controller;

import connection.DBConnect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.sql.*;

import java.util.Optional;
import java.util.ResourceBundle;

public class MasterSupplier implements Initializable {

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

    ObservableList<Supplier> listSupplier =
            FXCollections.observableArrayList();

    //=========================================================
    // TEXTFIELD
    //=========================================================

    @FXML
    private TextField txtIDSupplier;

    @FXML
    private TextField txtNamaSupplier;

    @FXML
    private TextField txtAlamat;

    @FXML
    private TextField txtTelepon;

    //=========================================================
    // STATUS (OTOMATIS)
    //=========================================================

    @FXML
    private TextField txtStatus;

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnUbah;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnAktifkan;

    @FXML
    private Button btnKembali;

    //=========================================================
    // TABLE VIEW
    //=========================================================

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TableColumn<Supplier, String> colId;

    @FXML
    private TableColumn<Supplier, String> colNama;

    @FXML
    private TableColumn<Supplier, String> colAlamat;

    @FXML
    private TableColumn<Supplier, String> colTelepon;

    @FXML
    private TableColumn<Supplier, String> colStatus;

    //=========================================================
    // INITIALIZE
    //=========================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = db.conn;

        initializeTable();

        autoID();

        txtStatus.setText("Tersedia");

        loadTable();

        tableClick();

        btnUbah.setDisable(true);
        btnHapus.setDisable(true);
        btnAktifkan.setDisable(true);

    }

    //=========================================================
    // INISIALISASI TABLE
    //=========================================================

    private void initializeTable() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idSupplier"));

        colNama.setCellValueFactory(
                new PropertyValueFactory<>("namaSupplier"));

        colAlamat.setCellValueFactory(
                new PropertyValueFactory<>("alamat"));

        colTelepon.setCellValueFactory(
                new PropertyValueFactory<>("telepon"));

        colStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));

    }

    //=========================================================
    // AUTO ID
    //=========================================================

    private void autoID() {

        try {

            String sql = "SELECT TOP 1 ID_Supplier FROM Supplier ORDER BY ID_Supplier DESC";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {

                String id = rs.getString("ID_Supplier").substring(2);

                int nomor = Integer.parseInt(id) + 1;

                txtIDSupplier.setText(String.format("SP%03d", nomor));

            } else {

                txtIDSupplier.setText("SP001");

            }

        } catch (Exception e) {

            alertError("Gagal membuat ID Supplier : " + e.getMessage());

        }

    }

    //=========================================================
    // LOAD TABLE
    //=========================================================

    private void loadTable() {

        listSupplier.clear();

        try {

            String sql = "SELECT * FROM Supplier ORDER BY ID_Supplier ASC";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            while (rs.next()) {

                Supplier supplier = new Supplier();

                supplier.setIdSupplier(rs.getString("ID_Supplier"));
                supplier.setNamaSupplier(rs.getString("Nama_Supplier"));
                supplier.setAlamat(rs.getString("Alamat"));
                supplier.setTelepon(rs.getString("No_Telepon"));
                supplier.setStatus(rs.getString("Status"));

                listSupplier.add(supplier);

            }

            tblSupplier.setItems(listSupplier);

        } catch (Exception e) {

            alertError("Gagal menampilkan data : " + e.getMessage());

        }

    }

    //=========================================================
    // REFRESH TABLE
    //=========================================================

    private void refreshTable() {

        listSupplier.clear();

        loadTable();

    }

    //=========================================================
    // KLIK DATA PADA TABLE
    //=========================================================

    private void tableClick() {

        tblSupplier.setOnMouseClicked(event -> {

            Supplier supplier = tblSupplier.getSelectionModel().getSelectedItem();

            if (supplier != null) {

                txtIDSupplier.setText(supplier.getIdSupplier());
                txtNamaSupplier.setText(supplier.getNamaSupplier());
                txtAlamat.setText(supplier.getAlamat());
                txtTelepon.setText(supplier.getTelepon());

                txtStatus.setText(supplier.getStatus());

                btnSimpan.setDisable(true);

                boolean nonAktif = "Tidak Tersedia".equalsIgnoreCase(supplier.getStatus());

                btnUbah.setDisable(nonAktif);
                btnHapus.setDisable(nonAktif);
                btnAktifkan.setDisable(!nonAktif);

            }

        });

    }

    //=========================================================
    // BERSIHKAN FORM
    //=========================================================

    private void clearForm() {

        txtIDSupplier.clear();
        txtNamaSupplier.clear();
        txtAlamat.clear();
        txtTelepon.clear();

        txtStatus.setText("Tersedia");

        tblSupplier.getSelectionModel().clearSelection();

        autoID();

        btnSimpan.setDisable(false);
        btnUbah.setDisable(true);
        btnHapus.setDisable(true);
        btnAktifkan.setDisable(true);

        txtNamaSupplier.requestFocus();

    }

    //=========================================================
    // SIMPAN DATA
    //=========================================================

    @FXML
    private void saveSupplier(ActionEvent event) {

        if (!validation()) {

            return;

        }

        try {

            txtStatus.setText("Tersedia");

            CallableStatement cs =
                    conn.prepareCall("{call sp_InsertSupplier(?,?,?,?,?)}");

            cs.setString(1, txtIDSupplier.getText());
            cs.setString(2, txtNamaSupplier.getText());
            cs.setString(3, txtAlamat.getText());
            cs.setString(4, txtTelepon.getText());
            cs.setString(5, txtStatus.getText());

            cs.execute();

            alertInformation("Data berhasil disimpan.");

            refreshTable();

            clearForm();

        } catch (Exception e) {

            alertError(e.getMessage());

        }

    }

    //=========================================================
    // UPDATE DATA
    //=========================================================

    @FXML
    private void updateSupplier(ActionEvent event) {

        if (!validation()) {

            return;

        }

        try {

            CallableStatement cs =
                    conn.prepareCall("{call sp_UpdateSupplier(?,?,?,?,?)}");

            cs.setString(1, txtIDSupplier.getText());
            cs.setString(2, txtNamaSupplier.getText());
            cs.setString(3, txtAlamat.getText());
            cs.setString(4, txtTelepon.getText());
            cs.setString(5, txtStatus.getText());

            cs.execute();

            alertInformation("Data berhasil diubah.");

            refreshTable();

            clearForm();

        } catch (Exception e) {

            alertError(e.getMessage());

        }

    }

    //=========================================================
    // HAPUS DATA
    //=========================================================

    @FXML
    private void deleteSupplier(ActionEvent event) {

        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText(null);

        alert.setContentText("Yakin ingin menghapus data?");

        Optional<ButtonType> pilih =
                alert.showAndWait();

        if (pilih.isPresent() &&
                pilih.get() == ButtonType.OK) {

            try {

                pst = conn.prepareStatement(
                        "UPDATE Supplier SET Status = ? WHERE ID_Supplier = ?");

                pst.setString(1, "Tidak Tersedia");
                pst.setString(2, txtIDSupplier.getText());

                pst.executeUpdate();

                alertInformation("Data berhasil dihapus.");

                refreshTable();

                clearForm();

            } catch (Exception e) {

                alertError(e.getMessage());

            }

        }

    }

    //=========================================================
    // AKTIFKAN KEMBALI (RESTORE SOFT DELETE)
    //=========================================================

    @FXML
    private void activateSupplier(ActionEvent event) {

        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText(null);

        alert.setContentText("Aktifkan kembali data supplier ini?");

        Optional<ButtonType> pilih =
                alert.showAndWait();

        if (pilih.isPresent() &&
                pilih.get() == ButtonType.OK) {

            try {

                pst = conn.prepareStatement(
                        "UPDATE Supplier SET Status = ? WHERE ID_Supplier = ?");

                pst.setString(1, "Tersedia");
                pst.setString(2, txtIDSupplier.getText());

                pst.executeUpdate();

                alertInformation("Data berhasil diaktifkan kembali.");

                refreshTable();

                clearForm();

            } catch (Exception e) {

                alertError(e.getMessage());

            }

        }

    }

    //=========================================================
    // VALIDASI INPUT
    //=========================================================

    private boolean validation() {

        if (txtNamaSupplier.getText().trim().isEmpty()) {

            alertWarning("Nama Supplier tidak boleh kosong.");

            txtNamaSupplier.requestFocus();

            return false;

        }

        if (txtAlamat.getText().trim().isEmpty()) {

            alertWarning("Alamat tidak boleh kosong.");

            txtAlamat.requestFocus();

            return false;

        }

        if (txtTelepon.getText().trim().isEmpty()) {
            alertWarning("Nomor Telepon tidak boleh kosong.");
            txtTelepon.requestFocus();
            return false;
        }

        String telepon = txtTelepon.getText().trim();

        if (!telepon.matches("\\d{13}")) {
            alertWarning("Nomor Telepon harus terdiri dari 13 digit angka.");
            txtTelepon.requestFocus();
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

    //=========================================================
    // KEMBALI KE DASHBOARD
    //=========================================================

    @FXML
    private void backDashboard(ActionEvent event) {

        try {

            Parent root =
                    FXMLLoader.load(getClass().getResource("/view/DashboardAdmin.fxml"));

            Stage stage =
                    (Stage) btnKembali.getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.setTitle("Dashboard Admin");

            stage.centerOnScreen();

            stage.show();

        } catch (Exception e) {

            alertError(e.getMessage());

        }

    }
}