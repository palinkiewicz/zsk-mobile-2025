package edu.zsk.vet;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private int lastClicked = 0;
    private static final String CHANNEL_ID = "vet_notifs";

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

        createNotificationChannel();

        TextView nameSurname = findViewById(R.id.name_and_surname);
        TextView ageValue = findViewById(R.id.age_value);
        TextView goal = findViewById(R.id.goal);
        TextView time = findViewById(R.id.time);

        Button btn = findViewById(R.id.button);

        SeekBar ageSlider = findViewById(R.id.age_slider);
        ListView list = findViewById(R.id.type_list);

        String[] animals = {"Pies", "Kot", "Świnka morska"};

        ArrayList<String> animalsL = new ArrayList<>(Arrays.asList(animals));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, animalsL) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                if (position == lastClicked) {
                    view.setBackgroundColor(Color.parseColor("#9999ff"));
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }

                return view;
            }
        };

        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            lastClicked = position;
            adapter.notifyDataSetChanged();
            ageSlider.setMax(position == 0 ? 18 : (position == 1 ? 20 : 9));
        });

        ageSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btn.setOnClickListener(l -> {
            String message = nameSurname.getText().toString() + ", "
                    + animalsL.get(lastClicked) + ", "
                    + ageValue.getText().toString() + ", "
                    + goal.getText().toString() + ", "
                    + time.getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.form_sent));
            builder.setMessage(message);
            AlertDialog dialog = builder.create();
            dialog.show();

            NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(getString(R.string.form_sent))
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.notify(0, notiBuilder.build());  // "0" is the notification ID
            }
        });
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription(getString(R.string.header));

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }
}