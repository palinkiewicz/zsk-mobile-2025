package pl.dakil.pralka;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private boolean vacuumOn = false;

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

        Button btn1 = findViewById(R.id.button);
        EditText edit1 = findViewById(R.id.editTextNumber);
        TextView text1 = findViewById(R.id.textView4);

        btn1.setOnClickListener(l -> {
            int number = Integer.parseInt(edit1.getText().toString());
            if (number >= 1 && number <= 12) {
                text1.setText(String.format(Locale.ENGLISH, "%s %d", getString(R.string.wash_number), number));
            }
        });

        Button btn2 = findViewById(R.id.button2);
        TextView text2 = findViewById(R.id.textView6);

        btn2.setOnClickListener(l -> {
            if (vacuumOn) {
                text2.setText(R.string.vacumm_off);
                btn2.setText(R.string.turn_on);
            } else {
                text2.setText(R.string.vacumm_on);
                btn2.setText(R.string.turn_off);
            }

            vacuumOn = !vacuumOn;
        });
    }
}