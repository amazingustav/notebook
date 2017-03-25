package br.com.amorimgc.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import br.com.amorimgc.adapter.AlunosAdapter;
import br.com.amorimgc.converter.AlunoConverter;
import br.com.amorimgc.agenda.databinding.ActivityListaAlunosBinding;
import br.com.amorimgc.dao.AlunoDAO;
import br.com.amorimgc.model.Aluno;
import br.com.amorimgc.service.EnviaAlunosTask;
import br.com.amorimgc.service.WebClient;

/**
 * Created by Gustavo Amorim on 14/03/2017.
 * @author amorimgc
 */
public class ListaAlunosActivity extends AppCompatActivity
        implements MenuItem.OnMenuItemClickListener, AdapterView.OnItemClickListener {
    private ActivityListaAlunosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lista_alunos);

        binding.listaAlunos.setOnItemClickListener(this);
        this.popularLista();
        registerForContextMenu(this.binding.listaAlunos);
    }

    private void popularLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> lista = dao.obterAlunos();
        dao.close();

        AlunosAdapter adapter = new AlunosAdapter(this, lista);
        this.binding.listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.popularLista();
    }

    // Cria as opções do menu "Settings"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Cria as opções do menu de contexto.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, R.string.lbl_call, 0, getString(R.string.lbl_call)).setOnMenuItemClickListener(this);
        menu.add(0, R.string.lbl_send_sms, 1, getString(R.string.lbl_send_sms)).setOnMenuItemClickListener(this);
        menu.add(0, R.string.lbl_view_map, 2, getString(R.string.lbl_view_map)).setOnMenuItemClickListener(this);
        menu.add(0, R.string.lbl_site, 3, getString(R.string.lbl_site)).setOnMenuItemClickListener(this);
        menu.add(0, R.string.lbl_delete, 4, getString(R.string.lbl_delete)).setOnMenuItemClickListener(this);
    }

    // Ação do botão + (adicionar).
    @Override
    public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
        Aluno aluno = (Aluno) binding.listaAlunos.getItemAtPosition(position);
        Intent formulario = new Intent(this, FormularioActivity.class);
        formulario.putExtra("objeto", aluno);
        startActivity(formulario);
    }

    // Ação do menu de contexto.
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno aluno = (Aluno) binding.listaAlunos.getItemAtPosition(info.position);

        switch (item.getItemId()) {
            case R.string.lbl_call:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                else {
                    Intent ligar = new Intent(Intent.ACTION_CALL);
                    ligar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(ligar);
                }
                break;
            case R.string.lbl_sms:
                Intent sms = new Intent(Intent.ACTION_VIEW);
                sms.setData(Uri.parse("sms:" + aluno.getTelefone()));
                item.setIntent(sms);
                break;
            case R.string.lbl_view_map:
                Intent mapa = new Intent(Intent.ACTION_VIEW);
                mapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
                item.setIntent(mapa);
                break;
            case R.string.lbl_site:
                Intent navegador = new Intent(Intent.ACTION_VIEW);
                String site = aluno.getSite();
                if (!site.startsWith("http://"))
                    aluno.setSite("http://" + site);
                navegador.setData(Uri.parse(aluno.getSite()));
                item.setIntent(navegador);
                break;
            case R.string.lbl_delete:
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.removerAluno(aluno);
                dao.close();
                Toast.makeText(ListaAlunosActivity.this, "Aluno removido!", Toast.LENGTH_LONG).show();
                popularLista();
                break;
        }


        return false;
    }

    // Chamada do formulário.
    public void adicionar(View v){
        Intent formulario = new Intent(this, FormularioActivity.class);
        startActivity(formulario);
    }
}