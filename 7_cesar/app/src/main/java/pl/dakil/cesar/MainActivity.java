package pl.dakil.cesar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText keyField = findViewById(R.id.key);
        EditText valueField = findViewById(R.id.value);
        EditText ciferField = findViewById(R.id.cifer);

        Button encrypt = findViewById(R.id.button);
        Button save = findViewById(R.id.button2);

        encrypt.setOnClickListener(l -> {
            String keyString = keyField.getText().toString();
            int key = Integer.parseInt(keyString.isEmpty() ? "0" : keyString);
            String value = valueField.getText().toString();

            StringBuilder encryptedBuilder = new StringBuilder();

            for (int i = 0; i < value.length(); i++) {
                char newChar = (int) value.charAt(i) >= 97 && (int) value.charAt(i) <= 122
                        ? (char) (((int) value.charAt(i) - 97 + key) % 26 + 97)
                        : value.charAt(i);
                encryptedBuilder.append(newChar);
            }

            ciferField.setText(encryptedBuilder);
        });

        save.setOnClickListener(l -> {
            Toast.makeText(this, "Sorry, Android.", Toast.LENGTH_SHORT).show();
        });
    }
}