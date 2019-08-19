package c.aman.theflyingfishgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class GameOverActivity extends AppCompatActivity {

    private Button PlayAgainButton ;
    private TextView ScoreTextView ;
    private String score ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);



        ScoreTextView = (TextView)findViewById(R.id.score_textview);
        PlayAgainButton = (Button)findViewById(R.id.play_again_button);

        score = getIntent().getExtras().get("Score").toString();


        PlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent MainIntent = new Intent(GameOverActivity.this , MainActivity.class);
                startActivity(MainIntent);
            }
        });
    ScoreTextView.setText("Score : " + score);
    }
}
