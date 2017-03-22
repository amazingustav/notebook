package br.com.amorimgc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.amorimgc.model.Aluno;

/**
 * Created by Gustavo Amorim on 14/03/2017.
 * @author amorimgc
 */
public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO(Context context) {
        super(context, "AgendaBD", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL," +
                "endereco TEXT, telefone TEXT, site TEXT, nota REAL, foto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "";
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN foto TEXT";
                db.execSQL(sql);
        }
    }

    public void inserirAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = this.obterValuesAluno(aluno);

        db.insert("Alunos", null, dados);
    }

    public void removerAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(aluno.getId())};

        db.delete("Alunos", "id = ?", params);
    }

    public List<Aluno> obterAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Aluno> lista = new ArrayList<>();

        while (cursor.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("foto")));

            lista.add(aluno);
        }
        cursor.close();

        return lista;
    }

    public void atualizarAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(aluno.getId())};
        ContentValues dados = this.obterValuesAluno(aluno);

        db.update("Alunos", dados, "id = ?", params);
    }

    @NonNull
    private ContentValues obterValuesAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();

        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("foto", aluno.getCaminhoFoto());

        return dados;
    }

    public boolean isAluno(String telefone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Alunos WHERE telefone = ?;", new String[]{telefone});
        boolean retorno = cursor.getCount() > 0;
        cursor.close();

        return retorno;
    }
}
