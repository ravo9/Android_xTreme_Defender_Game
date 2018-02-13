package ozog.development.xtremedefender;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class xTremeDefender extends AppCompatActivity {

    Drawable d;
    RelativeLayout rl;
    Context c;

    int score = 0;

    //Button btn1 = findViewById(R.id.btn1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_treme_defender);

        try {
            InputStream stream = getAssets().open("bullet1.png");
            d = Drawable.createFromStream(stream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rl = findViewById(R.id.relativeLayout1);
        c = getApplicationContext();
    }

    public void newGame( View v) {

        score = 0;
        new Bullet( rl, c, d );
        //btn1.setVisibility(View.GONE);

        /*while ( score <= 5 ) {
            new Bullet( rl, c, d );
            try {
                Thread.sleep( 4000 );
            }
            catch( InterruptedException e ) {
                System.out.print(e.getMessage());
            }
        }*/

    }
}
