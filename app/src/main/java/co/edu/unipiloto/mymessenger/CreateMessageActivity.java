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

public class CreateMessageActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "mensaje";
    private TextView tvMensaje1;
    private EditText etMensaje1;
    private List<String> chatHistory;
    private SharedPreferences preferences;

    //Comienza la definición del método onCreate, que es un método de ciclo de vida de Android que se llama cuando se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        etMensaje1 = findViewById(R.id.etMensaje1);
        Button btnEnviar1 = findViewById(R.id.btnEnviar1);
        tvMensaje1 = findViewById(R.id.tvMensaje1);

        preferences = getSharedPreferences("chat_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("chat_history", "[]");
        chatHistory = gson.fromJson(json, ArrayList.class);

        updateChatHistory();

        //Agrega un OnClickListener al botón "Enviar" (btnEnviar1) para responder a los clicKs.
        btnEnviar1.setOnClickListener(new View.OnClickListener() {
            @Override
            //Define la acción que se realizará cuando se haga clicK en el botón.
            public void onClick(View v) {
                String textoMensaje1 = etMensaje1.getText().toString();
                if (!textoMensaje1.isEmpty()) {
                    chatHistory.add("Chat 1: " + textoMensaje1);
                    updateChatHistory();

                    Intent intent1 = new Intent(CreateMessageActivity.this, ReceiveMessageActivity.class);
                    //Agrega el mensaje ingresado como un extra al intent, utilizando la constante EXTRA_MESSAGE definida anteriormente.
                    intent1.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, textoMensaje1);
                    startActivity(intent1);
                }
            }
        });

        // Modifica el texto del TextView (tvMensaje1) con el historial de chat completo convertido en una cadena de texto
        tvMensaje1.setText(getChatHistoryAsString());
    }

    // Comienza la definición del método updateChatHistory(), que actualiza el historial de chat en las preferencias.
    private void updateChatHistory() {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(chatHistory);
        editor.putString("chat_history", json);
        editor.apply();
    }

    // Comienza la definición del método getChatHistoryAsString(), que convierte el historial de chat en una cadena.
    private String getChatHistoryAsString() {
        StringBuilder historyBuilder = new StringBuilder();
        for (String message : chatHistory) {
            historyBuilder.append(message).append("\n");
        }
        return historyBuilder.toString();
    }
}













