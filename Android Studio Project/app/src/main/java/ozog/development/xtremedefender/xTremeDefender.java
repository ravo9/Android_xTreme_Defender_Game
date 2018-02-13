package ozog.development.xtremedefender;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class xTremeDefender extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_treme_defender);

        generateBullet();
    }

    public void generateBullet() {

        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout1);

        Drawable d;
        ImageView iv = new ImageView(getApplicationContext());

        // Create layout parameters for ImageView
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        iv.setLayoutParams(lp);

        try {
            InputStream stream = getAssets().open("bullet1.png");
            d = Drawable.createFromStream(stream, null);
            iv.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // It would be better to make it relative instead of fixed.
        TranslateAnimation moveBullet = new TranslateAnimation(0, -1500, 0, 1500);
        moveBullet.setDuration(1000);
        moveBullet.setFillAfter(true);

        iv.startAnimation(moveBullet);

        rl.addView(iv);
    }

    public void generateOrigin() {

        Random generator = new Random();


    }
}
