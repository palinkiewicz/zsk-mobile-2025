package pl.dakil.kosci;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final ArrayList<Integer> rolled = new ArrayList<>();
    private int all = 0;

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

        Button roll = findViewById(R.id.roll);

        TextView scoreS = findViewById(R.id.score);
        TextView allS = findViewById(R.id.all);

        ArrayList<ImageView> imgs = new ArrayList<>();
        imgs.add(findViewById(R.id.imageView1));
        imgs.add(findViewById(R.id.imageView2));
        imgs.add(findViewById(R.id.imageView3));
        imgs.add(findViewById(R.id.imageView4));
        imgs.add(findViewById(R.id.imageView5));

        roll.setOnClickListener(l -> {
            rolled.clear();

            for (int i = 0; i < 5; i++) {
                int rand = getRandom();
                rolled.add(rand);

                int resId;

                switch (rand) {
                    case 1: resId = R.drawable.k1; break;
                    case 2: resId = R.drawable.k2; break;
                    case 3: resId = R.drawable.k3; break;
                    case 4: resId = R.drawable.k4; break;
                    case 5: resId = R.drawable.k5; break;
                    case 6: resId = R.drawable.k6; break;
                    default: resId = R.drawable.k1;
                }

                imgs.get(i).setImageResource(resId);
            }

            int score = sumPoints();
            all += score;

            scoreS.setText(getString(R.string.score) + " " + score);
            allS.setText(getString(R.string.all) + " " + all);
        });

        Button reset = findViewById(R.id.reset);

        reset.setOnClickListener(l -> {
            all = 0;
            rolled.clear();

            for (int i = 0; i < 5; i++) {
                imgs.get(i).setImageResource(R.drawable.question);
            }

            scoreS.setText(getString(R.string.score) + " 0");
            allS.setText(getString(R.string.all) + " 0");
        });
    }

    private int getRandom() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    private int sumPoints() {
        int a = 0;
        for (int i = 0; i < rolled.size(); i++) {
            a += rolled.get(i);
        }
        return a;
    }
}