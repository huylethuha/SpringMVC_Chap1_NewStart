package stackjava.com.springmvcjdbc.entities;

public class Ksp {
    private int id;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "Ksp{" +
                "id=" + id +
                ", email=" + email +
                ", password=" + password +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ksp(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Ksp(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Ksp() {
    }
}
