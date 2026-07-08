package model;

public class Pelanggan {

    // =====================================================
    // ATTRIBUTE
    // =====================================================

    private String idPelanggan;
    private String idJenisMember;
    private String namaPelanggan;
    private String noTelepon;

    private int poinAktif;
    private int totalPoinTerkumpul;

    // Untuk ditampilkan di TableView
    private String status;
    private String jenisMember;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public Pelanggan() {

    }

    public Pelanggan(String idPelanggan,
                     String idJenisMember,
                     String namaPelanggan,
                     String noTelepon,
                     int poinAktif,
                     int totalPoinTerkumpul,
                     String status,
                     String jenisMember) {

        this.idPelanggan = idPelanggan;
        this.idJenisMember = idJenisMember;
        this.namaPelanggan = namaPelanggan;
        this.noTelepon = noTelepon;
        this.poinAktif = poinAktif;
        this.totalPoinTerkumpul = totalPoinTerkumpul;
        this.status = status;
        this.jenisMember = jenisMember;

    }

    // =====================================================
    // GETTER
    // =====================================================

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getIdJenisMember() {
        return idJenisMember;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public int getPoinAktif() {
        return poinAktif;
    }

    public int getTotalPoinTerkumpul() {
        return totalPoinTerkumpul;
    }

    public String getStatus() {
        return status;
    }

    public String getJenisMember() {
        return jenisMember;
    }

    // =====================================================
    // SETTER
    // =====================================================

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public void setIdJenisMember(String idJenisMember) {
        this.idJenisMember = idJenisMember;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public void setPoinAktif(int poinAktif) {
        this.poinAktif = poinAktif;
    }

    public void setTotalPoinTerkumpul(int totalPoinTerkumpul) {
        this.totalPoinTerkumpul = totalPoinTerkumpul;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJenisMember(String jenisMember) {
        this.jenisMember = jenisMember;
    }

}