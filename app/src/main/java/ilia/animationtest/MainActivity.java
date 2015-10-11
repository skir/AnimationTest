package ilia.animationtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Elastic rectangle = (Elastic) findViewById(R.id.view);

        rectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Path path1 = new Path();
                Path path2 = new Path();

                Point size = new Point();
                getWindowManager().getDefaultDisplay().getSize(size);
                int height = size.y;

                if(rectangle.getY() > 0) {
                    path1.moveTo(rectangle.getX(), rectangle.getY());
                    path1.lineTo(0, height * 3 / 4);

                    path2.moveTo(0, height * 3 / 4);
                    path2.lineTo(0,0);
                }else{ //go back
                    path1.lineTo(0, height / 4);

                    path2.moveTo(0, height / 4);
                    path2.lineTo(0,height - rectangle.getHeight());
                }

                ObjectAnimator animatorAcceleration = ObjectAnimator.ofFloat(rectangle, View.X, View.Y, path1);
                animatorAcceleration.setDuration(300)
                        .setInterpolator(new AccelerateInterpolator((float) 1.5));

                ObjectAnimator animatorDeceleration = ObjectAnimator.ofFloat(rectangle, View.X, View.Y, path2);
                animatorDeceleration.setDuration(400)
                        .setInterpolator(new DecelerateInterpolator((float) 0.8));

                ObjectAnimator animatorScaleBounce = ObjectAnimator.ofFloat(rectangle, View.SCALE_Y, 1, 5, 1);
                animatorScaleBounce.setDuration(2223)
                        .setInterpolator(new BounceInterpolator());

                ValueAnimator animatorElastic = ValueAnimator.ofFloat(0, 1);
                animatorElastic.setDuration(700)
                        .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                rectangle.onMotion((Float) valueAnimator.getAnimatedValue());
                            }
                        });

                ValueAnimator animatorElasticBounce = ValueAnimator.ofFloat(0,1);
                animatorElasticBounce.setDuration(500)
                        .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                rectangle.onBounceMotion((Float) valueAnimator.getAnimatedValue());
                            }
                        });
                animatorElasticBounce.setInterpolator(new DecelerateInterpolator());

                AnimatorSet animatorSet = new AnimatorSet();

                animatorSet.play(animatorAcceleration).with(animatorScaleBounce).with(animatorElastic);
                animatorSet.play(animatorDeceleration).after(animatorAcceleration);
                animatorSet.play(animatorElasticBounce).after(animatorElastic);

                animatorSet.start();
            }
        });

    }
}
