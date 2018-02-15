package ozog.development.xtremedefender;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class xTremeDefender extends AppCompatActivity {

    RelativeLayout rl;
    Context c;

    boolean gameFinished;

    static TextView textView1;
    static TextView textView2;
    static ArrayList<Drawable> bulletImages;
    static Random generator;
    static int centiseconds;
    static int seconds;
    static int minutes;
    static ImageView logo;

    ArrayList<Bullet> bullets;

    Timer timer;
    Timer timer2;
    Timer timer3;

    Handler handler = new Handler();
    static int score;
    static int respawnTime;
    static int addedTime;
    static float speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_treme_defender);

        logo = findViewById(R.id.logo);
        // Animation code taken from StackOverflow
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        logo.setAnimation(rotate);

        bulletImages = new ArrayList<>();

        try {
            InputStream stream = getAssets().open("bullet1.png");
            bulletImages.add(Drawable.createFromStream(stream, null));
            stream = getAssets().open("bullet2.png");
            bulletImages.add(Drawable.createFromStream(stream, null));
            stream = getAssets().open("bullet3.png");
            bulletImages.add(Drawable.createFromStream(stream, null));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rl = findViewById(R.id.relativeLayout1);
        c = getApplicationContext();

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        generator = new Random();
    }

    public void newGame( View v) {

        textView1.setText( "Score: " + score );

        centiseconds = 0;
        seconds = 30;
        minutes = 0;

        respawnTime = 1000;
        addedTime = 1000;
        speed = 1;

        timer = new Timer();
        timer2 = new Timer();
        timer3 = new Timer();

        bullets = new ArrayList<>();

        gameFinished = false;
        score = 0;

        Button btn1 = findViewById(R.id.btn1);
        btn1.setVisibility(View.GONE);
        logo.clearAnimation();
        logo.setVisibility(View.GONE);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run () {
                        update();
                    }
                });
            }
        }, 0, 10);

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run () {
                        Drawable d = bulletImages.get(getBulletImageNumber());
                        bullets.add(new Bullet( rl, c, d ));
                    }
                });
            }
        }, 0, respawnTime);

        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run () {
                        updateTime();
                    }
                });
            }
        }, 0, 10);

    }

    public void update() {
        for ( Bullet b: bullets ) {
            b.updatePosition();
        }
    }

    public void updateTime() {

        // Level change
        // Level 2.
        if ( score == 5 ) {
            respawnTime = 800;
            addedTime = 600;
            speed = 2;
        }

        if ( score == 10 ) {
            respawnTime = 600;
            addedTime = 300;
            speed = 3;
        }

        if ( score == 15 ) {
            respawnTime = 400;
            addedTime = 200;
            speed = 4;
        }

        // Check if the game is not finished
        if ( gameFinished != true ){

            if ( centiseconds > 0)
                centiseconds--;
            else if ( seconds > 0 ) {
                seconds--;
                centiseconds = 99;
            } else if ( minutes > 0 ) {
                minutes--;
                seconds = 59;
                centiseconds = 99;
            } else
                gameFinished = true;

            String min = Integer.toString(minutes);
            if ( minutes < 10 )
                min = "0" + min;

            String sec = Integer.toString(seconds);
            if ( seconds < 10 )
                sec = "0" + sec;

            String centsec = Integer.toString(centiseconds);
            if ( centiseconds < 10 )
                centsec = "0" + centsec;

            // A trick that temporary solves problem of added time (when the centsec has three characters).
            if ( centiseconds > 99 )
                centsec = centsec.substring(1, 3);

            textView2.setText( ( ( ( min + ":" ) + sec ) + ":" ) + centsec );
        }
        else {
            // If the game is over.
            timer.cancel();
            timer2.cancel();
            timer3.cancel();

            textView2.setText("Game over!");
            Button btn1 = findViewById(R.id.btn1);
            btn1.setText("Start again");


            // Bullets removing
            for ( Bullet b: bullets) {
                b.remove();
            }
            bullets.clear();


            btn1.setVisibility(View.VISIBLE);
        }
    }

    public static void updateScore() {
        score++;
        textView1.setText( "Score: " + score );
    }

    public static int getBulletImageNumber() {
        return generator.nextInt(bulletImages.size());
    }
}
