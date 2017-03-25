package br.com.amorimgc.service;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.com.amorimgc.converter.AlunoConverter;
import br.com.amorimgc.dao.AlunoDAO;
import br.com.amorimgc.model.Aluno;

/**
 * Created by Gustavo Amorim on 24/03/2017.
 * Porpouse: Sync tasks to app don't let the app run first then others principals threads.
 * @author amorimgc
 */
public class EnviaAlunosTask extends AsyncTask<Void, Void, String>{

    private Context context;
    private ProgressDialog progress;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> listaAlunos = dao.obterAlunos();
        dao.close();

        String json = new AlunoConverter().converterParaJSON(listaAlunos);
        WebClient client = new WebClient();

        return client.post(json);
    }

    @Override
    protected void onPostExecute(String resposta) {
        progress.dismiss();
        AlertDialog.Builder msg = new AlertDialog.Builder(context);

        if (resposta == null){
            msg.setTitle("Ops...");
            msg.setMessage("Houve um erro na conexão com o WebService :/");
        } else {
            msg.setTitle("Olha só:");
            msg.setMessage(resposta);
        }

        msg.create();
        msg.show();
    }
}
