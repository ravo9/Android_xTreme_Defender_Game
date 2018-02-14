package ozog.development.xtremedefender;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Bullet {

    private Point origin, target;
    private Drawable d;
    private RelativeLayout rl;
    private ImageView iv;
    private static float speed;
    private static Random generator;
    private static int screenWidth;
    private static int screenHeight;

    static {
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        generator = new Random();
        speed = 1;
    }

    public Bullet(RelativeLayout rl, final Context context, Drawable d ) {
        this.rl = rl;
        iv = new ImageView(context);

        // Create layout parameters for ImageView
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(lp);

        iv.setScaleX(3);
        iv.setScaleY(3);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setVisibility(View.GONE);
                xTremeDefender.updateScore();
            }
        });

        this.d = d;

        iv.setImageDrawable(d);

        origin = generateOrigin();
        iv.setX(origin.x);
        iv.setY(origin.y);

        target = generateTarget(getOriginAreaNumber(origin));

        rl.addView(iv);

        // Destroy!
    }

    public void updatePosition() {

        // Movement
        float x = this.iv.getX();
        float y = this.iv.getY();

        float targetX = this.target.x;
        float targetY = this.target.y;

        float ratio = Math.abs(target.x - x) / Math.abs(target.y - y);

        if ( this.target.x < x )
            this.iv.setX( x - speed * ratio );
        else if ( this.target.x > x )
            this.iv.setX( x + speed * ratio );

        if ( this.target.y < y )
            this.iv.setY( y - speed * 1/ratio );
        else if ( this.target.y > y )
            this.iv.setY( y + speed * 1/ratio );

        // Rotation
        this.iv.setRotation(this.iv.getRotation() + 8);

    }

    private Point generateOrigin() {

        int x = -100;
        int y = -100;

        // Four possible areas to appear in - over north, east, south or west border ( 0 to 3 ).
        int border = generator.nextInt( 4 );

        //Log.d("mytag", Integer.toString(border));

        switch( border ) {
            // I assume that picture width and height is 100.
            case 0:
                x = generator.nextInt(screenWidth);
                break;
            case 1:
                x = screenWidth + 200;
                y = generator.nextInt(screenHeight);
                break;
            case 2:
                x = generator.nextInt(screenWidth);
                y = screenHeight + 200;
                break;
            case 3:
                y = generator.nextInt(screenHeight);
                break;
        }
        return (new Point(x, y));
    }

    private int getOriginAreaNumber( Point o ) {
        if ( o.y == -100 )
            return 0;
        else if ( o.x == ( screenWidth + 200 ) )
            return 1;
        else if ( o.y == ( screenHeight + 200 ) )
            return 2;
        else
            return 3;
    }

    private Point generateTarget( int originArea ) {

        int x = -100;
        int y = -100;

        // Four possible areas to be targeted - over north, east, south or west border ( 0 to 3 ).
        // We need to exclude the origin area.
        int border = -1;
        while ( border == -1 || border == originArea )
            border = generator.nextInt( 4 );

        switch( border ) {
            // I assume that picture width and height is 100.
            case 0:
                x = generator.nextInt(screenWidth);
                break;
            case 1:
                x = screenWidth + 200;
                y = generator.nextInt(screenHeight);
                break;
            case 2:
                x = generator.nextInt(screenWidth);
                y = screenHeight + 200;
                break;
            case 3:
                y = generator.nextInt(screenHeight);
                break;
        }
        return (new Point(x, y));
    }

}
