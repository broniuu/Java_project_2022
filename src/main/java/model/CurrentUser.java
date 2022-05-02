package model;

public class CurrentUser {
    private String login;
    private String password;
    private int id;
    public CurrentUser( String login, String password,int id) {
        this.login = login;
        this.password = password;
        this.id=id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }
}
