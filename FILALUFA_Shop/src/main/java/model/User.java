package model;

public class User {

    private String idUser;
    private String username;
    private String password;
    private String namaUser;
    private String telepon;
    private String email;
    private String status;
    private String role;

    public User() {

    }

    public User(String idUser,
                String username,
                String password,
                String namaUser,
                String telepon,
                String email,
                String status,
                String role) {

        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.namaUser = namaUser;
        this.telepon = telepon;
        this.email = email;
        this.status = status;
        this.role = role;

    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}