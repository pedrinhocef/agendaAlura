package br.com.pedrodroid.agendaalura;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.pedrodroid.agendaalura.dao.AlunoDAO;
import br.com.pedrodroid.agendaalura.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private String caminhoFoto;
    private final int CODIGO_CAMERA = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        final Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno != null){
            helper.preencheFormulario(aluno);
        }


        FloatingActionButton fab_foto = (FloatingActionButton) findViewById(R.id.fab_foto);
        fab_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiraFoto();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAluno();
                AlunoDAO dao = new AlunoDAO(this);

                if(aluno.getId() != null){
                    dao.altera(aluno);
                } else{
                    dao.insere(aluno);
                }
                dao.close();

                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " Salvo", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tiraFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        // essa parte muda no Android 7, estamos construindo uma URI
        // para acessar a foto utilizando o FileProvider
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider", arquivoFoto));

        startActivityForResult(intent, CODIGO_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            helper.carregaImagem(caminhoFoto);
        }

    }
}
