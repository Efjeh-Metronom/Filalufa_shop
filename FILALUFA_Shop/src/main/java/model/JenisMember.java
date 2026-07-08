package model;

public class JenisMember {

    private String idJenisMember;
    private String namaJenisMember;
    private int minimumPoint;
    private int kelipatanPoint;

    public JenisMember() {

    }

    public JenisMember(String idJenisMember,
                       String namaJenisMember,
                       int minimumPoint,
                       int kelipatanPoint) {

        this.idJenisMember = idJenisMember;
        this.namaJenisMember = namaJenisMember;
        this.minimumPoint = minimumPoint;
        this.kelipatanPoint = kelipatanPoint;

    }

    public String getIdJenisMember() {
        return idJenisMember;
    }

    public void setIdJenisMember(String idJenisMember) {
        this.idJenisMember = idJenisMember;
    }

    public String getNamaJenisMember() {
        return namaJenisMember;
    }

    public void setNamaJenisMember(String namaJenisMember) {
        this.namaJenisMember = namaJenisMember;
    }

    public int getMinimumPoint() {
        return minimumPoint;
    }

    public void setMinimumPoint(int minimumPoint) {
        this.minimumPoint = minimumPoint;
    }

    public int getKelipatanPoint() {
        return kelipatanPoint;
    }

    public void setKelipatanPoint(int kelipatanPoint) {
        this.kelipatanPoint = kelipatanPoint;
    }

}
