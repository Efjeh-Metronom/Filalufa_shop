package controller;

import connection.DBConnect;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.JenisMember;

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

public class MasterJenisMember implements Initializable {

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

    ObservableList<JenisMember> listJenisMember =
            FXCollections.observableArrayList();

    //=========================================================
    // TEXTFIELD
    //=========================================================

    @FXML
    private TextField txtIDJenisMember;

    @FXML
    private TextField txtNamaJenisMember;

    @FXML
    private TextField txtMinimumPoint;

    @FXML
    private TextField txtKelipatanPoint;

    //=========================================================
    // BUTTON
    //=========================================================

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
    private TableView<JenisMember> tblJenisMember;

    @FXML
    private TableColumn<JenisMember, String> colId;

    @FXML
    private TableColumn<JenisMember, String> colNama;

    @FXML
    private TableColumn<JenisMember, Integer> colMinimum;

    @FXML
    private TableColumn<JenisMember, Integer> colKelipatan;

    //=========================================================
    // INITIALIZE
    //=========================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn = db.conn;

        initializeTable();

        autoID();

        loadTable();

        tableClick();

        disableButton();

    }

    //=========================================================
    // INISIALISASI TABLE
    //=========================================================

    private void initializeTable() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("idJenisMember"));

        colNama.setCellValueFactory(
                new PropertyValueFactory<>("namaJenisMember"));

        colMinimum.setCellValueFactory(
                new PropertyValueFactory<>("minimumPoint"));

        colKelipatan.setCellValueFactory(
                new PropertyValueFactory<>("kelipatanPoint"));

    }

    //=========================================================
    // AUTO ID
    //=========================================================

    private void autoID() {

        try {

            String sql = "SELECT TOP 1 ID_JenisMember FROM JenisMember ORDER BY ID_JenisMember DESC";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            if (rs.next()) {

                String id = rs.getString("ID_JenisMember").substring(2);

                int nomor = Integer.parseInt(id) + 1;

                txtIDJenisMember.setText(String.format("JM%03d", nomor));

            } else {

                txtIDJenisMember.setText("JM001");

            }

        } catch (Exception e) {

            alertError("Gagal membuat ID Jenis Member : " + e.getMessage());

        }

    }

    //=========================================================
    // LOAD TABLE
    //=========================================================

    private void loadTable() {

        listJenisMember.clear();

        try {

            String sql = "SELECT * FROM JenisMember ORDER BY ID_JenisMember ASC";

            stat = conn.createStatement();

            rs = stat.executeQuery(sql);

            while (rs.next()) {

                JenisMember jenisMember = new JenisMember();

                jenisMember.setIdJenisMember(rs.getString("ID_JenisMember"));
                jenisMember.setNamaJenisMember(rs.getString("Nama_JenisMember"));
                jenisMember.setMinimumPoint(rs.getInt("Minimum_Point"));
                jenisMember.setKelipatanPoint(rs.getInt("Kelipatan_Point"));

                listJenisMember.add(jenisMember);

            }

            tblJenisMember.setItems(listJenisMember);

        } catch (Exception e) {

            alertError("Gagal menampilkan data : " + e.getMessage());

        }

    }

    //=========================================================
    // REFRESH TABLE
    //=========================================================

    private void refreshTable() {

        listJenisMember.clear();

        loadTable();

    }

    //=========================================================
    // KLIK DATA PADA TABLE
    //=========================================================

    private void tableClick() {

        tblJenisMember.setOnMouseClicked(event -> {

            JenisMember jenisMember =
                    tblJenisMember.getSelectionModel().getSelectedItem();

            if (jenisMember != null) {

                txtIDJenisMember.setText(jenisMember.getIdJenisMember());
                txtNamaJenisMember.setText(jenisMember.getNamaJenisMember());
                txtMinimumPoint.setText(String.valueOf(jenisMember.getMinimumPoint()));
                txtKelipatanPoint.setText(String.valueOf(jenisMember.getKelipatanPoint()));

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

        txtNamaJenisMember.clear();
        txtMinimumPoint.clear();
        txtKelipatanPoint.clear();

        tblJenisMember.getSelectionModel().clearSelection();

        autoID();

        disableButton();

        txtNamaJenisMember.requestFocus();

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
    private void saveJenisMember(ActionEvent event) {

        if (!validation()) {

            return;

        }

        try {

            CallableStatement cs =
                    conn.prepareCall("{call sp_InsertJenisMember(?,?,?,?)}");

            cs.setString(1, txtIDJenisMember.getText());
            cs.setString(2, txtNamaJenisMember.getText());
            cs.setInt(3, Integer.parseInt(txtMinimumPoint.getText().trim()));
            cs.setInt(4, Integer.parseInt(txtKelipatanPoint.getText().trim()));

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
    private void updateJenisMember(ActionEvent event) {

        if (!validation()) {

            return;

        }

        try {

            CallableStatement cs =
                    conn.prepareCall("{call sp_UpdateJenisMember(?,?,?,?)}");

            cs.setString(1, txtIDJenisMember.getText());
            cs.setString(2, txtNamaJenisMember.getText());
            cs.setInt(3, Integer.parseInt(txtMinimumPoint.getText().trim()));
            cs.setInt(4, Integer.parseInt(txtKelipatanPoint.getText().trim()));

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
    private void deleteJenisMember(ActionEvent event) {

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

                CallableStatement cs =
                        conn.prepareCall("{call sp_DeleteJenisMember(?)}");

                cs.setString(1, txtIDJenisMember.getText());

                cs.execute();

                alertInformation("Data berhasil dihapus.");

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

        if (txtNamaJenisMember.getText().trim().isEmpty()) {

            alertWarning("Nama Jenis Member tidak boleh kosong.");

            txtNamaJenisMember.requestFocus();

            return false;

        }

        if (txtMinimumPoint.getText().trim().isEmpty()) {

            alertWarning("Minimum Point tidak boleh kosong.");

            txtMinimumPoint.requestFocus();

            return false;

        }

        if (txtKelipatanPoint.getText().trim().isEmpty()) {

            alertWarning("Kelipatan Point tidak boleh kosong.");

            txtKelipatanPoint.requestFocus();

            return false;

        }

        try {

            int minimum = Integer.parseInt(txtMinimumPoint.getText().trim());

            if (minimum < 0) {

                alertWarning("Minimum Point tidak boleh bernilai negatif.");

                txtMinimumPoint.requestFocus();

                return false;

            }

        } catch (NumberFormatException e) {

            alertWarning("Minimum Point harus berupa angka.");

            txtMinimumPoint.requestFocus();

            return false;

        }

        try {

            int kelipatan = Integer.parseInt(txtKelipatanPoint.getText().trim());

            if (kelipatan <= 0) {

                alertWarning("Kelipatan Point harus lebih besar dari 0.");

                txtKelipatanPoint.requestFocus();

                return false;

            }

        } catch (NumberFormatException e) {

            alertWarning("Kelipatan Point harus berupa angka.");

            txtKelipatanPoint.requestFocus();

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
