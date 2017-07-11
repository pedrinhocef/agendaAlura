package br.com.pedrodroid.agendaalura;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.pedrodroid.agendaalura.converter.AlunoConverter;
import br.com.pedrodroid.agendaalura.dao.AlunoDAO;
import br.com.pedrodroid.agendaalura.modelo.Aluno;

/**
 * Created by pedrodroid on 06/07/17.
 */

public class EnviarAlunosTask extends AsyncTask<Void, String, String> {
    private Context context;
    private ProgressDialog alertDialog;

    public EnviarAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        alertDialog = ProgressDialog.show(context,"Aguarde", "Enviando Alunos...", true, true);
        alertDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {

        WebClient client = new WebClient();
        AlunoConverter conversor = new AlunoConverter();
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        String json = conversor.converteParaJSON(alunos);
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        alertDialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
    }
}
