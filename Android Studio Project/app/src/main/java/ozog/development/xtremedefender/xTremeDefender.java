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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class xTremeDefender extends AppCompatActivity {

    Drawable d;
    RelativeLayout rl;
    Context c;

    static TextView textView1;
    static TextView textView2;

    int centiseconds = 0;
    int seconds = 0;
    int minutes = 0;

    ArrayList<Bullet> bullets;

    Timer timer;
    Timer timer2;
    Timer timer3;

    Handler handler = new Handler();
    static int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_treme_defender);

        timer = new Timer();
        timer2 = new Timer();
        timer3 = new Timer();

        try {
            InputStream stream = getAssets().open("bullet1.png");
            d = Drawable.createFromStream(stream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rl = findViewById(R.id.relativeLayout1);
        c = getApplicationContext();
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        bullets = new ArrayList<>();
    }

    public void newGame( View v) {

        score = 0;

        Button btn1 = findViewById(R.id.btn1);
        btn1.setVisibility(View.GONE);


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
                        bullets.add(new Bullet( rl, c, d ));
                    }
                });
            }
        }, 0, 3500);

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

        if ( centiseconds < 99)
            centiseconds++;
        else {
            centiseconds = 0;

            if ( seconds < 59)
                seconds++;
            else {
                seconds = 0;

                if ( minutes < 59)
                    minutes++;
                else
                    minutes = 0;
            }
        }

        String min = Integer.toString(minutes);
        if ( minutes < 10 )
            min = "0" + min;

        String sec = Integer.toString(seconds);
        if ( seconds < 10 )
            sec = "0" + sec;

        String centsec = Integer.toString(centiseconds);
        if ( centiseconds < 10 )
            centsec = "0" + centsec;

        textView2.setText( ( ( ( min + ":" ) + sec ) + ":" ) + centsec );
    }

    public static void updateScore() {
        score++;
        textView1.setText( "Score: " + score );
    }
}
