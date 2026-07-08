package controller;

import connection.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Produk;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MasterProduk implements Initializable {

    DBConnect connect = new DBConnect();

    @FXML
    private TextField txtIdProduk;

    @FXML
    private TextField txtKodeProduk;

    @FXML
    private TextField txtNamaProduk;

    @FXML
    private TextField txtKategori;

    @FXML
    private TextField txtHargaBeli;

    @FXML
    private TextField txtHargaJual;

    @FXML
    private TextField txtStok;

    @FXML
    private ComboBox<String> cmbDiskon;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableView<Produk> tblProduk;

    @FXML
    private TableColumn<Produk, String> colIdProduk;

    @FXML
    private TableColumn<Produk, String> colKodeProduk;

    @FXML
    private TableColumn<Produk, String> colNamaProduk;

    @FXML
    private TableColumn<Produk, String> colKategori;

    @FXML
    private TableColumn<Produk, Double> colHargaBeli;

    @FXML
    private TableColumn<Produk, Double> colHargaJual;

    @FXML
    private TableColumn<Produk, Integer> colStok;

    @FXML
    private TableColumn<Produk, String> colDiskon;

    ObservableList<Produk> listProduk =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url,
                           ResourceBundle resourceBundle) {

        colIdProduk.setCellValueFactory(
                new PropertyValueFactory<>("idProduk"));

        colKodeProduk.setCellValueFactory(
                new PropertyValueFactory<>("kodeProduk"));

        colNamaProduk.setCellValueFactory(
                new PropertyValueFactory<>("namaProduk"));

        colKategori.setCellValueFactory(
                new PropertyValueFactory<>("kategori"));

        colHargaBeli.setCellValueFactory(
                new PropertyValueFactory<>("hargaBeli"));

        colHargaJual.setCellValueFactory(
                new PropertyValueFactory<>("hargaJual"));

        colStok.setCellValueFactory(
                new PropertyValueFactory<>("stok"));

        colDiskon.setCellValueFactory(
                new PropertyValueFactory<>("idDiskon"));

        cmbStatus.getItems().addAll(
                "Aktif",
                "Nonaktif"
        );

        loadDiskon();
        tampilData();
        generateID();
        pilihData();
    }

    private void loadDiskon() {

        try {

            ResultSet rs =
                    connect.stat.executeQuery(
                            "SELECT ID_Diskon FROM Diskon");

            while (rs.next()) {
                cmbDiskon.getItems()
                        .add(rs.getString("ID_Diskon"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateID() {

        try {

            ResultSet rs =
                    connect.stat.executeQuery(
                            "SELECT TOP 1 ID_Produk " +
                                    "FROM Produk " +
                                    "ORDER BY ID_Produk DESC");

            if (rs.next()) {

                int nomor =
                        Integer.parseInt(
                                rs.getString("ID_Produk")
                                        .substring(3))
                                + 1;

                txtIdProduk.setText(
                        String.format(
                                "PRD%03d",
                                nomor));
            } else {
                txtIdProduk.setText("PRD001");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tampilData() {

        listProduk.clear();

        try {

            ResultSet rs =
                    connect.stat.executeQuery(
                            "SELECT * FROM Produk");

            while (rs.next()) {

                listProduk.add(
                        new Produk(
                                rs.getString("ID_Produk"),
                                rs.getString("Kode_Produk"),
                                rs.getString("Nama_Produk"),
                                rs.getString("Kategori"),
                                rs.getDouble("Harga_Beli"),
                                rs.getDouble("Harga_Jual"),
                                rs.getInt("Stok"),
                                rs.getString("ID_Diskon"),
                                rs.getString("Status")
                        )
                );
            }

            tblProduk.setItems(listProduk);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveProduk() {

        try {

            String query =
                    "INSERT INTO Produk " +
                            "VALUES (?,?,?,?,?,?,?,?,?)";

            connect.pstet =
                    connect.conn.prepareStatement(query);

            connect.pstet.setString(
                    1,
                    txtIdProduk.getText());

            connect.pstet.setString(
                    2,
                    txtKodeProduk.getText());

            connect.pstet.setString(
                    3,
                    txtNamaProduk.getText());

            connect.pstet.setString(
                    4,
                    txtKategori.getText());

            connect.pstet.setDouble(
                    5,
                    Double.parseDouble(
                            txtHargaBeli.getText()));

            connect.pstet.setDouble(
                    6,
                    Double.parseDouble(
                            txtHargaJual.getText()));

            connect.pstet.setInt(
                    7,
                    Integer.parseInt(
                            txtStok.getText()));

            connect.pstet.setString(
                    8,
                    cmbDiskon.getValue());

            connect.pstet.setString(
                    9,
                    cmbStatus.getValue());

            connect.pstet.executeUpdate();

            tampilData();
            resetForm();

            Alert alert =
                    new Alert(
                            Alert.AlertType.INFORMATION);

            alert.setContentText(
                    "Data berhasil disimpan");

            alert.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateProduk() {

        try {

            String query =
                    "UPDATE Produk SET " +
                            "Kode_Produk=?, " +
                            "Nama_Produk=?, " +
                            "Kategori=?, " +
                            "Harga_Beli=?, " +
                            "Harga_Jual=?, " +
                            "Stok=?, " +
                            "ID_Diskon=?, " +
                            "Status=? " +
                            "WHERE ID_Produk=?";

            connect.pstet =
                    connect.conn.prepareStatement(query);

            connect.pstet.setString(
                    1,
                    txtKodeProduk.getText());

            connect.pstet.setString(
                    2,
                    txtNamaProduk.getText());

            connect.pstet.setString(
                    3,
                    txtKategori.getText());

            connect.pstet.setDouble(
                    4,
                    Double.parseDouble(
                            txtHargaBeli.getText()));

            connect.pstet.setDouble(
                    5,
                    Double.parseDouble(
                            txtHargaJual.getText()));

            connect.pstet.setInt(
                    6,
                    Integer.parseInt(
                            txtStok.getText()));

            connect.pstet.setString(
                    7,
                    cmbDiskon.getValue());

            connect.pstet.setString(
                    8,
                    cmbStatus.getValue());

            connect.pstet.setString(
                    9,
                    txtIdProduk.getText());

            connect.pstet.executeUpdate();

            tampilData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteProduk() {

        try {

            String query =
                    "DELETE FROM Produk " +
                            "WHERE ID_Produk=?";

            connect.pstet =
                    connect.conn.prepareStatement(query);

            connect.pstet.setString(
                    1,
                    txtIdProduk.getText());

            connect.pstet.executeUpdate();

            tampilData();
            resetForm();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void resetForm() {

        txtKodeProduk.clear();
        txtNamaProduk.clear();
        txtKategori.clear();
        txtHargaBeli.clear();
        txtHargaJual.clear();
        txtStok.clear();

        cmbDiskon.setValue(null);
        cmbStatus.setValue(null);

        generateID();
    }

    private void pilihData() {

        tblProduk.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (obs, oldData, newData) -> {

                            if (newData != null) {

                                txtIdProduk.setText(
                                        newData.getIdProduk());

                                txtKodeProduk.setText(
                                        newData.getKodeProduk());

                                txtNamaProduk.setText(
                                        newData.getNamaProduk());

                                txtKategori.setText(
                                        newData.getKategori());

                                txtHargaBeli.setText(
                                        String.valueOf(
                                                newData.getHargaBeli()));

                                txtHargaJual.setText(
                                        String.valueOf(
                                                newData.getHargaJual()));

                                txtStok.setText(
                                        String.valueOf(
                                                newData.getStok()));

                                cmbDiskon.setValue(
                                        newData.getIdDiskon());

                                cmbStatus.setValue(
                                        newData.getStatus());
                            }
                        });
    }
}