package br.com.amorimgc.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.amorimgc.agenda.FormularioActivity;
import br.com.amorimgc.model.Aluno;

/**
 * Created by Gustavo Amorim on 14/03/2017.
 * @author amorimgc
 */
public class FormularioHelper {

    private EditText nome;
    private EditText endereco;
    private EditText telefone;
    private EditText site;
    private RatingBar nota;
    private ImageView foto;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        this.nome = activity.binding.formularioNome;
        this.endereco = activity.binding.formularioEndereco;
        this.telefone = activity.binding.formularioTelefone;
        this.site = activity.binding.formularioSite;
        this.nota = activity.binding.formularioNota;
        this.foto = activity.binding.formularioFoto;
        this.aluno = new Aluno();
    }

    public Aluno obterAluno(){
        this.aluno.setNome(this.nome.getText().toString());
        this.aluno.setEndereco(this.endereco.getText().toString());
        this.aluno.setTelefone(this.telefone.getText().toString());
        this.aluno.setSite(this.site.getText().toString());
        this.aluno.setNota(Double.valueOf(this.nota.getProgress()));
        this.aluno.setCaminhoFoto(String.valueOf(this.foto.getTag()));
        return this.aluno;
    }

    public void preencherFormulario(Aluno aluno) {
        this.nome.setText(aluno.getNome());
        this.endereco.setText(aluno.getEndereco());
        this.telefone.setText(aluno.getTelefone());
        this.site.setText(aluno.getSite());
        this.nota.setProgress((int) aluno.getNota());
        this.carregarImagem(aluno.getCaminhoFoto());
        this.aluno = aluno;
    }

    // Preenche a imagem no ImageView.
    public void carregarImagem(String caminhoFoto) {
        if (!caminhoFoto.equals("null")) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
            foto.setTag(caminhoFoto);
        }
    }
}
