package ch.fabkli.gravitytictactoe;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    int oldPreviewX = 2;
    int oldPreviewY = 2;
    private SensorManager sensorManager;
    private Sensor accelerometer, magnetometer;
    private float[] gravity, geomagnetic;
    private TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        TextView title = findViewById(R.id.title);
        output = findViewById(R.id.output);
        title.setText("Gravity Tic Tac Toe");

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 1; i < 30; i++) {
            for (int j = 1; j < 30; j++) {
                View view = new View(this);
                if (i % 10 == 0 || j % 10 == 0) {
                    view.setBackgroundColor(Color.WHITE);
                } else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                gridLayout.addView(view);
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
                params.rowSpec = GridLayout.spec(i, 1f);
                params.columnSpec = GridLayout.spec(j, 1f);
                params.width = 0;
                params.height = 0;
                view.setLayoutParams(params);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView x = findViewById(R.id.x);
        TextView z = findViewById(R.id.z);

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values;
        if (gravity != null && geomagnetic != null) {
            if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
                SensorManager.getOrientation(rotationMatrix, orientationAngles);
                long xOrientation = Math.round(Math.toDegrees(orientationAngles[1]));
                long zOrientation = Math.round(Math.toDegrees(orientationAngles[2]));
                x.setText("X: " + xOrientation);
                z.setText("Z: " + zOrientation);

                int XPosition = 2;
                int ZPosition = 2;

                if (xOrientation > 10) {
                    XPosition = 1;
                } else if (xOrientation < -10) {
                    XPosition = 3;
                }
                if (zOrientation > 10) {
                    ZPosition = 3;
                } else if (zOrientation < -10) {
                    ZPosition = 1;
                }

                drawPreview(XPosition, ZPosition, "red");

            }
        }
    }

    public void drawPreview(int XPosition, int ZPosition, String color) {

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        View view = new View(this);
        view.setBackgroundColor(Color.BLACK);
        gridLayout.addView(view);
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.rowSpec = GridLayout.spec(oldPreviewX * 10 - 5-1, 3,1f);
        params.columnSpec = GridLayout.spec(oldPreviewY * 10 - 5-1,3, 1f);
        params.width = 0;
        params.height = 0;
        view.setLayoutParams(params);


        XPosition = XPosition * 10 - 5;
        ZPosition = ZPosition * 10 - 5;
        View view2 = new View(this);
        if (color.equals("red")) {
            view2.setBackgroundColor(Color.argb(255, 92, 3, 3));
        } else if (color.equals("blue")) {
            view2.setBackgroundColor(Color.argb(255,3, 40, 92));
        } else {
            view2.setBackgroundColor(Color.GRAY);
        }
        gridLayout.addView(view2);
        GridLayout.LayoutParams params2 = (GridLayout.LayoutParams) view2.getLayoutParams();
        params2.rowSpec = GridLayout.spec(XPosition-1, 3,1f);
        params2.columnSpec = GridLayout.spec(ZPosition-1, 3,1f);
        params2.width = 0;
        params2.height = 0;
        view2.setLayoutParams(params2);

        oldPreviewX = (XPosition+ 5)/ 10 ;
        oldPreviewY =( ZPosition +5 )/10;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can be left empty for this example
    }
}