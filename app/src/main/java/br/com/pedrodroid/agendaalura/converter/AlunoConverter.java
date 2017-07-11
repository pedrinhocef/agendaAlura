package br.com.pedrodroid.agendaalura.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.pedrodroid.agendaalura.modelo.Aluno;

/**
 * Created by pedrodroid on 06/07/17.
 */

public class AlunoConverter {

    public String converteParaJSON(List<Aluno> alunos) {

        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos) {
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return js.toString();
    }
}
