package model;

public class Produk {

    private String kodeProduk;
    private String status;

    private String idProduk;
    private String idDiskon;
    private String namaProduk;
    private String kategori;
    private double hargaBeli;
    private double hargaJual;
    private int stok;

    // Constructor kosong
    public Produk() {
    }

    // Constructor tanpa ID Produk
    public Produk(String idProduk,
                  String kodeProduk,
                  String namaProduk,
                  String kategori,
                  double hargaBeli,
                  double hargaJual,
                  int stok,
                  String idDiskon,
                  String status) {

        this.idProduk = idProduk;
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.kategori = kategori;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
        this.idDiskon = idDiskon;
        this.status = status;
    }

    // Constructor lengkap
    public Produk(String idProduk, String idDiskon,
                  String namaProduk, String kategori,
                  double hargaBeli, double hargaJual,
                  int stok) {
        this.idProduk = idProduk;
        this.idDiskon = idDiskon;
        this.namaProduk = namaProduk;
        this.kategori = kategori;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
    }

    // Getter dan Setter

    public String getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(String idProduk) {
        this.idProduk = idProduk;
    }

    public String getIdDiskon() {
        return idDiskon;
    }

    public void setIdDiskon(String idDiskon) {
        this.idDiskon = idDiskon;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(double hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public void setKodeProduk(String kodeProduk) {
        this.kodeProduk = kodeProduk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}