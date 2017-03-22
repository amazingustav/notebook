package br.com.amorimgc.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.amorimgc.agenda.ListaAlunosActivity;
import br.com.amorimgc.agenda.R;
import br.com.amorimgc.agenda.databinding.ListItemBinding;
import br.com.amorimgc.helper.FormularioHelper;
import br.com.amorimgc.model.Aluno;

/**
 * Created by Gustavo Amorim on 14/03/2017.
 * @author amorimgc
 */
public class AlunosAdapter extends BaseAdapter{
    private final List<Aluno> listaAlunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> lista) {
        this.context = context;
        this.listaAlunos = lista;
    }

    @Override
    public int getCount() {
        return this.listaAlunos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaAlunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaAlunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null)
            view = inflater.inflate(R.layout.list_item, parent, false);
        /*
        ListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
        */
        obterDadosView(position, view);

        return view;
    }

    // Preenche a view criada com os dados do Aluno.
    private void obterDadosView(int position, View view) {
        Aluno aluno = listaAlunos.get(position);

        TextView nome = (TextView) view.findViewById(R.id.item_nome);
        TextView telefone = (TextView) view.findViewById(R.id.item_telefone);
        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);

        nome.setText(aluno.getNome());
        telefone.setText(aluno.getTelefone());

        String caminhoFoto = aluno.getCaminhoFoto();
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            foto.setImageBitmap(bitmapReduzido);
            foto.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }
}
