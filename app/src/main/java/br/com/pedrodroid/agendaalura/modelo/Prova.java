package br.com.pedrodroid.agendaalura.modelo;

import java.util.List;

/**
 * Created by pedrodroid on 12/07/17.
 */

public class Prova {

    private String materia;
    private String data;
    private List<String> topicos;

    public Prova(String materia, String data, List<String> topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = topicos;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<String> topicos) {
        this.topicos = topicos;
    }

    @Override
    public String toString() {
        return this.materia;
    }
}