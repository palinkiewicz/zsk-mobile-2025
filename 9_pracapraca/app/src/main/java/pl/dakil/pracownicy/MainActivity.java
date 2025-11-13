package pl.dakil.pracownicy;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText nameEdit, surnameEdit, numberEdit;
    private CheckBox checkLetters, checkDigits, checkSpecial;
    private Spinner spinner;

    private String generatedPassword = "";

    private String generatePassword() {
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String specials = "!@#$%^&*()_+-=";

        int length;
        try {
            length = Integer.parseInt(numberEdit.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Podaj liczbę znaków!", Toast.LENGTH_SHORT).show();
            return "";
        }

        if (length <= 0) {
            Toast.makeText(this, "Liczba znaków musi być > 0!", Toast.LENGTH_SHORT).show();
            return "";
        }

        Random rand = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            password.append(lowercase.charAt(rand.nextInt(lowercase.length())));
        }

        if (checkLetters.isChecked()) {
            int index = rand.nextInt(length);
            password.setCharAt(index, uppercase.charAt(rand.nextInt(uppercase.length())));
        }

        if (checkDigits.isChecked() && length > 1) {
            password.setCharAt(0, digits.charAt(rand.nextInt(digits.length())));
        }

        if (checkSpecial.isChecked() && length > 2) {
            password.setCharAt(1, specials.charAt(rand.nextInt(specials.length())));
        }

        return password.toString();
    }

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

        nameEdit = findViewById(R.id.name);
        surnameEdit = findViewById(R.id.surname);
        numberEdit = findViewById(R.id.number);
        checkLetters = findViewById(R.id.low_high);
        checkDigits = findViewById(R.id.digits);
        checkSpecial = findViewById(R.id.special);
        spinner = findViewById(R.id.spinner);
        Button generateBtn = findViewById(R.id.generate);
        Button submitBtn = findViewById(R.id.submit);

        String[] positions = {"Kierownik", "Starszy programista", "Młodszy programista", "Tester"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, positions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        generateBtn.setOnClickListener(l -> {
            generatedPassword = generatePassword();
            Toast.makeText(MainActivity.this, "Wygenerowane hasło: " + generatedPassword, Toast.LENGTH_LONG).show();
        });

        submitBtn.setOnClickListener(l -> {
            String imie = nameEdit.getText().toString().trim();
            String nazwisko = surnameEdit.getText().toString().trim();
            String stanowisko = spinner.getSelectedItem().toString();

            if (generatedPassword.isEmpty()) {
                Toast.makeText(MainActivity.this, "Najpierw wygeneruj hasło!", Toast.LENGTH_SHORT).show();
                return;
            }

            String msg = "Dane pracownika: " + imie + " " + nazwisko + " " + stanowisko + " Hasło: " + generatedPassword;

            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        });
    }
}