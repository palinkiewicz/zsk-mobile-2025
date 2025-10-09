package pl.dakil.lista;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<String> arr = new ArrayList<>();

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

        arr.add("Zakupy: chleb, masło, ser");
        arr.add("Do zrobienia: obiad, umyć podłogi");
        arr.add("weekend: kino, spacer z psem");

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, R.layout.list_element, R.id.textView_1, arr);

        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(aa);

        Button btn = findViewById(R.id.button);
        EditText et = findViewById(R.id.editTextText);

        btn.setOnClickListener(l -> {
            if (!et.getText().toString().isEmpty()) {
                arr.add(et.getText().toString());
                aa.notifyDataSetChanged();
            }
        });
    }
}