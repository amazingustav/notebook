package br.com.amorimgc.model;

import java.io.Serializable;

/**
 * Created by Gustavo Amorim on 14/03/2017.
 * @author amorimgc
 */
public class Aluno implements Serializable{
    private long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String site;
    private double nota;
    private String caminhoFoto;

    // 123 - XYZ
    @Override
    public String toString() {
        return this.id + " - " + this.nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String enredeco) {
        this.endereco = enredeco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }
}
