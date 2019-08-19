package c.aman.theflyingfishgame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.GpsStatus;
import android.provider.ContactsContract;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.function.ToDoubleBiFunction;

public class FlyingFishView extends View
{
    InterstitialAd mInterstitialAd;
    private int fishX = 10;
    private int fishY ;
    private int fishSpeed ;

    private int score , lifeCounter ;

    private int yellowX , yellowY , yellowSpeed=6 ;
    private Paint yellowPaint = new Paint();

    private int greenX , greenY , greenSpeed = 8 ;
    private Paint greenPaint = new Paint();

    private int redX , redY , redSpeed = 10 ;
    private Paint redPaint = new Paint();

    private int canvasWidth , canvasHeight ;

    private Boolean touch = false ;

   private Bitmap fish[] = new Bitmap[2];
   private Bitmap BackgroundImage;
   private Paint ScorePaint = new Paint();
   private Bitmap life[] = new Bitmap[2];

    public FlyingFishView(Context context) {
        super(context);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-1223827385642400/7609902027");
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);



        fish[0] = BitmapFactory.decodeResource(getResources() , R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources() , R.drawable.fish2);

        BackgroundImage = BitmapFactory.decodeResource(getResources() , R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        ScorePaint.setColor(Color.WHITE);
        ScorePaint.setTextSize(46);
        ScorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        ScorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources() , R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources() , R.drawable.heart_grey);

        fishY  = 550 ;
        score = 0 ;
        lifeCounter = 3 ;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        canvas.drawBitmap(BackgroundImage ,0 ,0 , null);

        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        int minimumFishY = fish[0].getHeight();
        int maximumFishY = canvasHeight - fish[0].getHeight() * 3 ;
        fishY = fishY + fishSpeed;
        if(fishY < minimumFishY)
        {
            fishY = minimumFishY;
        }

        if(fishY > maximumFishY)
        {
            fishY = maximumFishY;
        }

        fishSpeed = fishSpeed + 2 ;

        if(touch)
        {
            canvas.drawBitmap(fish[1] , fishX , fishY , null);
            touch = false ;
        }
        else
        {
            canvas.drawBitmap(fish[0] , fishX , fishY , null);
        }

        yellowX = yellowX - yellowSpeed;

        if(HitBallChecker(yellowX , yellowY))
        {
            score = score +10 ;
            yellowX = - 100 ;

        }
        if(yellowX < 0)
        {
            yellowX = canvasWidth +21 ;
            yellowY = (int) Math.floor(Math.random() * (maximumFishY - minimumFishY)) + minimumFishY ;
        }
        canvas.drawCircle(yellowX , yellowY , 25 , yellowPaint);


        redX = redX - redSpeed;

        if(HitBallChecker(redX , redY))
        {
            redX = - 100 ;
            lifeCounter -- ;

            if(lifeCounter == 0)
            {
                Toast.makeText(getContext() , "Game Over" , Toast.LENGTH_SHORT).show();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            super.onAdClosed();
                        }
                    });
                }
                    Intent GameIntent= new Intent(getContext() , GameOverActivity.class);
                GameIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                GameIntent.putExtra("Score" , score);
                getContext().startActivity(GameIntent);

            }
        }
        if(redX < 0)
        {
            redX = canvasWidth +21 ;
            redY = (int) Math.floor(Math.random() * (maximumFishY - minimumFishY)) + minimumFishY ;
        }
        canvas.drawCircle(redX , redY , 30 , redPaint);



        greenX = greenX - greenSpeed;

        if(HitBallChecker(greenX , greenY))
        {
            score = score + 15 ;
            greenX = - 100 ;

        }
        if(greenX < 0)
        {
            greenX = canvasWidth +21 ;
            greenY = (int) Math.floor(Math.random() * (maximumFishY - minimumFishY)) + minimumFishY ;
        }
        canvas.drawCircle(greenX , greenY , 25 , greenPaint);



        canvas.drawText("Score : " + score,20,60, ScorePaint);


        for(int i = 0 ; i<3 ; i++ )
        {
            int x = (int) (450 + life[0].getWidth() * 1.5 * i);
            int y = 30;
            if(i < lifeCounter)
            {
                canvas.drawBitmap(life[0] , x  ,y , null);
            }
            else
            {
                canvas.drawBitmap(life[1] , x  ,y , null);
            }
        }

    }

    public boolean HitBallChecker(int x , int y )
    {
        if(fishX < x && x <(fishX + fish[0].getWidth())  && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return true;
        }
        return  false ;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
           touch = true ;
           fishSpeed = -22 ;
        }
        return true ;
    }
}
