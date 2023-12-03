package com.example.projetobancodados.model;

import java.io.Serializable;

public class Veiculo implements Serializable {

    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String email;
    private String placa;
    private String modeloAno;
    private String observacao;

    // Construtor vazio
    public Veiculo() {
    }

    // Construtor com todos os atributos
    public Veiculo(Long id, String nome, String placa, String modeloAno, String telefone, String endereco, String email, String observacao) {
        this.id = id;
        this.nome = nome;
        this.placa = placa;
        this.modeloAno = modeloAno;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + "\nPlaca: " + placa;
    }
    // Métodos getter e setter...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModeloAno() {
        return modeloAno;
    }

    public void setModeloAno(String modeloAno) {
        this.modeloAno = modeloAno;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


}
