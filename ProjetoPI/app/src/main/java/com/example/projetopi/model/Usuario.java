package com.example.projetopi.model;

public class Usuario {
    private long id;
    private String login;
    private String senha;

    public Usuario() {
        // Construtor padrão necessário para SQLite
    }

    public Usuario(long id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

