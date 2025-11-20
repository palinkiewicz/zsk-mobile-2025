package pl.dakil.poczta;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
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

        Button check = findViewById(R.id.check_price);

        check.setOnClickListener(l -> {
            RadioButton r1 = findViewById(R.id.type1);
            RadioButton r2 = findViewById(R.id.type2);
            RadioButton r3 = findViewById(R.id.type3);
            TextView t = findViewById(R.id.textView3);
            ImageView img = findViewById(R.id.imageView);

            if (r1.isChecked()) {
                img.setImageResource(R.drawable.pocztowka);
                t.setText("Cena: 1 zł");
            } else if (r2.isChecked()) {
                img.setImageResource(R.drawable.list);
                t.setText("Cena: 1,5 zł");
            } else {
                img.setImageResource(R.drawable.paczka);
                t.setText("Cena: 10 zł");
            }
        });

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(l -> {
            TextView postal_code = findViewById(R.id.postal_code);
            String postal = postal_code.getText().toString();
            String notification = "";

            if (postal.length() != 5) {
                notification = "Nieprawidłowa liczba cyfr w kodzie pocztowym";
            } else if (!postal.matches("\\d+")) {
                notification = "Kod pocztowy powinien składać się z samych cyfr";
            } else {
                notification = "Dane przesyłki zostały wprowadzone";
            }

            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        });
    }
}