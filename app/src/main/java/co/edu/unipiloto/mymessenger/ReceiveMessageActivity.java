package co.edu.unipiloto.mymessenger;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ReceiveMessageActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";
    private TextView tvMensaje2;
    private EditText etMensaje2;
    private List<String> chatHistory;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        tvMensaje2 = findViewById(R.id.tvMensaje2);
        etMensaje2 = findViewById(R.id.etMensaje2);
        Button btnEnviar2 = findViewById(R.id.btnEnviar2);

        preferences = getSharedPreferences("chat_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("chat_history", "[]");
        chatHistory = gson.fromJson(json, ArrayList.class);

        //Para extraer el mensaje declaramos un intent y con el get trae el contexto que es el intent que invoco
        Intent intent1 = getIntent();
        //Ahora extraemos el msg con un string y lo extraemos desde el intent usamos un getStringExtra que es un mensaje adicional que corresponde a la constante
        String textoMensaje2 = intent1.getStringExtra(EXTRA_MESSAGE);

        tvMensaje2.setText(getChatHistoryAsString());

        btnEnviar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoMensaje2 = etMensaje2.getText().toString();
                if (!textoMensaje2.isEmpty()) {
                    chatHistory.add("Chat 2: " + textoMensaje2);
                    updateChatHistory();

                    tvMensaje2.setText(getChatHistoryAsString());

                    Intent intent2 = new Intent(ReceiveMessageActivity.this, CreateMessageActivity.class);
                    intent2.putExtra(CreateMessageActivity.EXTRA_MESSAGE, textoMensaje2);
                    startActivity(intent2);
                }
            }
        });
    }

    private void updateChatHistory() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(chatHistory);
        editor.putString("chat_history", json);
        editor.apply();
    }

    private String getChatHistoryAsString() {
        StringBuilder historyBuilder = new StringBuilder();
        for (String message : chatHistory) {
            historyBuilder.append(message).append("\n");
        }
        return historyBuilder.toString();
    }
}













