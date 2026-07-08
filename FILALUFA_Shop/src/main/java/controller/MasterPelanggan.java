package controller;


import connection.DBConnect;

import javafx.scene.control.*;
import model.Pelanggan;
import java.sql.PreparedStatement;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

// ======================================================
// CLASS
// ======================================================

public class MasterPelanggan implements Initializable {

    // ======================================================
    // DATABASE
    // ======================================================

    private DBConnect db = new DBConnect();

    private Connection conn;
    private CallableStatement cs;
    private ResultSet rs;

    // ======================================================
    // OBSERVABLE LIST
    // ======================================================

    private ObservableList<Pelanggan> listPelanggan =
            FXCollections.observableArrayList();

    // ======================================================
    // TEXT FIELD
    // ======================================================

    @FXML
    private TextField txtIDPelanggan;

    @FXML
    private TextField txtNamaPelanggan;

    @FXML
    private TextField txtNoTelepon;

    @FXML
    private TextField txtPoinAktif;

    @FXML
    private TextField txtTotalPoin;

    @FXML
    private TextField txtCari;

    // ======================================================
    // CHECK BOX
    // ======================================================

    @FXML
    private CheckBox chkMember;

    // ======================================================
    // COMBO BOX
    // ======================================================

    @FXML
    private ComboBox<String> cmbJenisMember;

    // ======================================================
    // BUTTON
    // ======================================================

    @FXML
    private Button btnTambah;

    @FXML
    private Button btnSimpan;

    @FXML
    private Button btnUbah;

    @FXML
    private Button btnHapus;

    @FXML
    private Button btnBatal;

    @FXML
    private Button btnRefresh;

    @FXML
    private Label lblStatus;

    // ======================================================
    // TABLE VIEW
    // ======================================================

    @FXML
    private TableView<Pelanggan> tblPelanggan;

    // ======================================================
    // TABLE COLUMN
    // ======================================================

    @FXML
    private TableColumn<Pelanggan, String> colID;

    @FXML
    private TableColumn<Pelanggan, String> colNama;

    @FXML
    private TableColumn<Pelanggan, String> colTelepon;

    @FXML
    private TableColumn<Pelanggan, String> colStatus;

    @FXML
    private TableColumn<Pelanggan, String> colJenis;

    @FXML
    private TableColumn<Pelanggan, Integer> colPoinAktif;

    @FXML
    private TableColumn<Pelanggan, Integer> colTotalPoin;

    // ======================================================
    // INITIALIZE
    // ======================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Membuka koneksi database
        conn = db.conn;

        // Generate ID Pelanggan
        generateID();

        // Mengisi ComboBox Jenis Member
        loadJenisMember();

        // Mengatur kondisi awal Membership
        settingMembership();

        // Mengosongkan Form
        clearForm();

        // Menampilkan Data ke TableView
        loadTable();

        tblPelanggan.setOnMouseClicked(
                event -> pilihData());

        btnRefresh.setOnAction(
                event -> refreshData());

        btnTambah.setOnAction(
                event -> tambahData());

        btnBatal.setOnAction(
                event -> batalData());

        // Status
        lblStatus.setText("Ready");

    }

    private void generateID() {

        try {

            String sql =
                    "SELECT TOP 1 ID_Pelanggan " +
                            "FROM Pelanggan " +
                            "ORDER BY ID_Pelanggan DESC";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String id = rs.getString("ID_Pelanggan");

                int nomor = Integer.parseInt(id.substring(2));

                nomor++;

                txtIDPelanggan.setText(String.format("PL%03d", nomor));

            } else {

                txtIDPelanggan.setText("PL001");

            }

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Generate ID");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }

    }

// ======================================================
// LOAD JENIS MEMBER
// ======================================================

    private void loadJenisMember() {

        cmbJenisMember.getItems().clear();


        cmbJenisMember.getItems().add("Bronze");

    }

    // ======================================================
// SETTING MEMBERSHIP
// ======================================================

    private void settingMembership() {

        chkMember.selectedProperty().addListener(

                (observable, oldValue, newValue) -> {

                    if (newValue) {

                        cmbJenisMember.getSelectionModel()
                                .select("Bronze");

                        cmbJenisMember.setDisable(true);

                    }

                    else {

                        cmbJenisMember.getSelectionModel()
                                .clearSelection();

                        cmbJenisMember.setDisable(true);

                    }

                });

    }

    // ======================================================
// CLEAR FORM
// ======================================================

    private void clearForm() {

        txtNamaPelanggan.clear();

        txtNoTelepon.clear();

        txtPoinAktif.setText("0");

        txtTotalPoin.setText("0");

        chkMember.setSelected(false);

        cmbJenisMember.getSelectionModel().clearSelection();

        cmbJenisMember.setDisable(true);

        txtNamaPelanggan.requestFocus();

    }

    // ======================================================
// LOAD TABLE
// ======================================================

    private void loadTable() {

        listPelanggan.clear();

        try {

            cs = conn.prepareCall("{CALL sp_ShowPelanggan}");

            rs = cs.executeQuery();

            while (rs.next()) {

                Pelanggan pelanggan = new Pelanggan();

                pelanggan.setIdPelanggan(
                        rs.getString("ID_Pelanggan"));

                pelanggan.setIdJenisMember(
                        rs.getString("ID_JenisMember"));

                pelanggan.setNamaPelanggan(
                        rs.getString("Nama_Pelanggan"));

                pelanggan.setNoTelepon(
                        rs.getString("No_Telepon"));

                pelanggan.setPoinAktif(
                        rs.getInt("Poin_Aktif"));

                pelanggan.setTotalPoinTerkumpul(
                        rs.getInt("Total_Poin_Terkumpul"));

                pelanggan.setStatus(
                        rs.getString("Status"));

                pelanggan.setJenisMember(
                        rs.getString("JenisMember"));

                listPelanggan.add(pelanggan);

            }

            colID.setCellValueFactory(
                    new PropertyValueFactory<>("idPelanggan"));

            colNama.setCellValueFactory(
                    new PropertyValueFactory<>("namaPelanggan"));

            colTelepon.setCellValueFactory(
                    new PropertyValueFactory<>("noTelepon"));

            colStatus.setCellValueFactory(
                    new PropertyValueFactory<>("status"));

            colJenis.setCellValueFactory(
                    new PropertyValueFactory<>("jenisMember"));

            colPoinAktif.setCellValueFactory(
                    new PropertyValueFactory<>("poinAktif"));

            colTotalPoin.setCellValueFactory(
                    new PropertyValueFactory<>("totalPoinTerkumpul"));

            tblPelanggan.setItems(listPelanggan);

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    // ======================================================
// CLICK TABLE
// ======================================================

    @FXML
    private void pilihData() {

        Pelanggan pelanggan =
                tblPelanggan.getSelectionModel().getSelectedItem();

        if (pelanggan == null)
            return;

        txtIDPelanggan.setText(
                pelanggan.getIdPelanggan());

        txtNamaPelanggan.setText(
                pelanggan.getNamaPelanggan());

        txtNoTelepon.setText(
                pelanggan.getNoTelepon());

        txtPoinAktif.setText(
                String.valueOf(
                        pelanggan.getPoinAktif()));

        txtTotalPoin.setText(
                String.valueOf(
                        pelanggan.getTotalPoinTerkumpul()));

        if (pelanggan.getIdJenisMember() == null) {

            chkMember.setSelected(false);

            cmbJenisMember.getSelectionModel().clearSelection();

            cmbJenisMember.setDisable(true);

        }

        else {

            chkMember.setSelected(true);

            cmbJenisMember.getSelectionModel().select(
                    pelanggan.getJenisMember());

            cmbJenisMember.setDisable(true);

        }

    }

    // ======================================================
// REFRESH
// ======================================================

    @FXML
    private void refreshData() {

        clearForm();

        generateID();

        loadTable();

        lblStatus.setText("Data berhasil direfresh.");

    }

    // ======================================================
// TAMBAH
// ======================================================

    @FXML
    private void tambahData() {

        clearForm();

        generateID();

        txtNamaPelanggan.requestFocus();

        lblStatus.setText("Mode tambah data.");

    }

    // ======================================================
// BATAL
// ======================================================

    @FXML
    private void batalData() {

        clearForm();

        generateID();

        tblPelanggan.getSelectionModel().clearSelection();

        lblStatus.setText("Input dibatalkan.");

    }

    // ======================================================
// VALIDASI INPUT
// ======================================================

    private boolean validasiInput() {

        // Nama Pelanggan

        if (txtNamaPelanggan.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Validasi");

            alert.setHeaderText(null);

            alert.setContentText("Nama pelanggan masih kosong.");

            alert.showAndWait();

            txtNamaPelanggan.requestFocus();

            return false;

        }

        // Nomor Telepon

        if (txtNoTelepon.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Validasi");

            alert.setHeaderText(null);

            alert.setContentText("Nomor telepon masih kosong.");

            alert.showAndWait();

            txtNoTelepon.requestFocus();

            return false;

        }

        return true;

    }

    // ======================================================
// VALIDASI NOMOR TELEPON
// ======================================================

    private boolean validasiTelepon() {

        String telepon = txtNoTelepon.getText().trim();

        if (!telepon.matches("[0-9]+")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Validasi");

            alert.setHeaderText(null);

            alert.setContentText("Nomor telepon hanya boleh angka.");

            alert.showAndWait();

            txtNoTelepon.requestFocus();

            return false;

        }

        if (telepon.length() < 10) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Validasi");

            alert.setHeaderText(null);

            alert.setContentText("Nomor telepon minimal 10 digit.");

            alert.showAndWait();

            txtNoTelepon.requestFocus();

            return false;

        }

        return true;

    }

    // ======================================================
// ALERT
// ======================================================

    private void showAlert(Alert.AlertType type,
                           String title,
                           String message){

        Alert alert = new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

    // ======================================================
// KONFIRMASI
// ======================================================

    private boolean konfirmasi(String pesan){

        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Konfirmasi");

        alert.setHeaderText(null);

        alert.setContentText(pesan);

        return alert.showAndWait().get()
                == ButtonType.OK;

    }


}