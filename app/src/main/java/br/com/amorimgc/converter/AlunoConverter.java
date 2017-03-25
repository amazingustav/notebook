package br.com.amorimgc.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.amorimgc.model.Aluno;

/**
 * Created by Gustavo Amorim on 24/03/2017.
 * Porpouse: Get data from database and convert to JSON string.
 * @author amorimgc
 */
public class AlunoConverter {

    public String converterParaJSON(List<Aluno> listaAlunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("List").array().object().key("Aluno").array();

            for (Aluno aluno : listaAlunos) {
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
