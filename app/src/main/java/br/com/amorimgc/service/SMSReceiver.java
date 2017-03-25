package br.com.amorimgc.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.amorimgc.agenda.R;
import br.com.amorimgc.dao.AlunoDAO;

/**
 * Created by Gustavo Amorim on 17/03/2017.
 * Pourpose: Alert specificly when receive a student's sms message.
 * @author amorimgc
 */
public class SMSReceiver extends BroadcastReceiver{

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        SmsMessage sms = SmsMessage.createFromPdu(pdu, intent.getSerializableExtra("format").toString());
        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);

        if (dao.isAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS de um Aluno", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

        dao.close();
    }
}
