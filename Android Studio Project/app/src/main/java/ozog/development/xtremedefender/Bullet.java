package ozog.development.xtremedefender;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Bullet {

    private Point origin, target;
    private Drawable d;
    private RelativeLayout rl;
    private ImageView iv;
    private static Random generator;
    private static int screenWidth;
    private static int screenHeight;

    static {
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        generator = new Random();
    }

    public Bullet( RelativeLayout rl, Context context, Drawable d ) {
        this.rl = rl;
        iv = new ImageView(context);

        // Create layout parameters for ImageView
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(lp);

        this.d = d;

        iv.setImageDrawable(d);

        origin = generateOrigin();
        target = generateTarget(getOriginAreaNumber(origin));

        // It would be better to make it relative instead of fixed.
        TranslateAnimation moveBullet = new TranslateAnimation( origin.x, target.x, origin.y, target.y );
        moveBullet.setDuration(2000);
        moveBullet.setFillAfter(true);

        iv.startAnimation(moveBullet);
        rl.addView(iv);


        // Destroy!
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
