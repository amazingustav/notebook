package br.com.amorimgc.agenda;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import br.com.amorimgc.agenda.databinding.ActivityFormularioBinding;
import br.com.amorimgc.dao.AlunoDAO;
import br.com.amorimgc.helper.FormularioHelper;
import br.com.amorimgc.model.Aluno;

/**
 * Created by Gustavo Amorim on 14/03/2017.
 * @author amorimgc
 */
public class FormularioActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int CODIGO_CAMERA = 1;
    public ActivityFormularioBinding binding;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_formulario);
        helper = new FormularioHelper(this);
        Aluno aluno = (Aluno) getIntent().getSerializableExtra("objeto");
        this.binding.formularioBotaoFoto.setOnClickListener(this);

        if(aluno != null)
            helper.preencherFormulario(aluno);
    }

    // Cria o menu de salvar contato.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Ação do botão Câmera.
    @Override
    public void onClick(View v) {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/IMG_" + System.currentTimeMillis() + ".jpg";
        Uri uriFoto = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", new File(caminhoFoto));
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
        startActivityForResult(camera, CODIGO_CAMERA);
    }

    // Ação após tirar foto.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK)
            helper.carregarImagem(caminhoFoto);
    }

    // Ação do botão Salvar Contato
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:
                this.salvar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Valida dados obrigatórios.
    private boolean validarDados(Aluno aluno) {
        if (aluno.getNome().isEmpty()) {
            Toast.makeText(this, "Fala quem é você, né?!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (aluno.getCaminhoFoto().equals("null")) {
            Toast.makeText(this, "Tira uma foto, vai?!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (aluno.getTelefone().isEmpty()) {
            Toast.makeText(this, "Coloca seu número aí!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void salvar(){
        Aluno aluno = helper.obterAluno();
        if (validarDados(aluno)){
            AlunoDAO dao = new AlunoDAO(this);

            if (aluno.getId() != 0L)
                dao.atualizarAluno(aluno);
            else
                dao.inserirAluno(aluno);
            dao.close();

            Toast.makeText(this, aluno.getNome() + " foi salvo!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // FIXME: só funciona com 2 cliques no campo Site.
    public void prefixoSite(View v){
        if (binding.formularioSite.getText().toString().isEmpty())
            binding.formularioSite.setText("http://www.");
    }
}
