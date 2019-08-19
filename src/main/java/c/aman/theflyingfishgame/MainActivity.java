package c.aman.theflyingfishgame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
private  FlyingFishView GameView;

private Handler handler = new Handler();
private final static  long Interval = 10 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameView = new FlyingFishView(this);
        setContentView(GameView);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                 GameView.invalidate();
                    }
                });
            }
        },0, Interval);

    }
}
