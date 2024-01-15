package ch.fabkli.gravitytictactoe;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    int oldX = 2;
    int oldY = 2;
    TicTacToeService tttService;
    boolean tttServiceBound = false;
    private final ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            TicTacToeService.TicTacToeBinder binder = (TicTacToeService.TicTacToeBinder) service;
            tttService = binder.getService();
            tttServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            tttServiceBound = false;
        }
    };
    private SensorManager sensorManager;
    private Sensor accelerometer, magnetometer;
    private float[] gravity, geomagnetic;
    private TextView output;

    public void drawPreview(int XPosition, int ZPosition) {

        if (!tttServiceBound || !tttService.isActive) {
            return;
        }


        GridLayout gridLayout = findViewById(R.id.gridLayout);
        View view = new View(this);
        view.setBackgroundColor(Color.BLACK);
        gridLayout.addView(view);
        GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
        params.rowSpec = GridLayout.spec(oldX * 10 - 5 - 1, 3, 1f);
        params.columnSpec = GridLayout.spec(oldY * 10 - 5 - 1, 3, 1f);
        params.width = 0;
        params.height = 0;
        view.setLayoutParams(params);


        View view2 = new View(this);
        String color = tttService.currentPlayer.equals("X") ? "red" : "blue";
        if (color.equals("red")) {
            view2.setBackgroundColor(Color.argb(255, 92, 3, 3));
        } else if (color.equals("blue")) {
            view2.setBackgroundColor(Color.argb(255, 3, 40, 92));
        } else {
            view2.setBackgroundColor(Color.GRAY);
        }
        gridLayout.addView(view2);
        GridLayout.LayoutParams params2 = (GridLayout.LayoutParams) view2.getLayoutParams();
        params2.rowSpec = GridLayout.spec(XPosition * 10 - 5- 1, 3, 1f);
        params2.columnSpec = GridLayout.spec(ZPosition * 10 - 5- 1, 3, 1f);
        params2.width = 0;
        params2.height = 0;
        view2.setLayoutParams(params2);

        oldX = XPosition ;
        oldY = ZPosition ;
    }

    public void drawField() {
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

        GridLayout signs = findViewById(R.id.signs);
        for (int i = 1; i < 30; i++) {
            for (int j = 1; j < 30; j++) {
                View view = new View(this);
                view.setBackgroundColor(Color.TRANSPARENT);
                signs.addView(view);
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
                params.rowSpec = GridLayout.spec(i, 1f);
                params.columnSpec = GridLayout.spec(j, 1f);
                params.width = 0;
                params.height = 0;
                view.setLayoutParams(params);
            }
        }
    }

    public void drawX(int x, int y) {

        List<List<Integer>> viewDefinitions = List.of(
                List.of(-2, -2),
                List.of(-1, -1),
                List.of(0, 0),
                List.of(1, 1),
                List.of(2, 2),
                List.of(-2, 2),
                List.of(-1, 1),
                List.of(1, -1),
                List.of(2, -2)
        );

        for (List<Integer> viewDefinition : viewDefinitions) {
            GridLayout gridLayout = findViewById(R.id.signs);
            View view = new View(this);
            view.setBackgroundColor(Color.argb(255, 255, 0, 0));
            gridLayout.addView(view);
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
            params.rowSpec = GridLayout.spec(x * 10 - 5 + viewDefinition.get(0), 1, 1f);
            params.columnSpec = GridLayout.spec(y * 10 - 5 + viewDefinition.get(1), 1, 1f);
            params.width = 0;
            params.height = 0;
            view.setLayoutParams(params);
        }
    }

    public void drawO(int x, int y) {
        GridLayout gridLayout = findViewById(R.id.signs);

        View viewTop = new View(this);
        viewTop.setBackgroundColor(Color.argb(255, 0, 0, 255));
        gridLayout.addView(viewTop);
        GridLayout.LayoutParams paramsTop = (GridLayout.LayoutParams) viewTop.getLayoutParams();
        paramsTop.rowSpec = GridLayout.spec(x * 10 - 5 - 2, 1, 1f);
        paramsTop.columnSpec = GridLayout.spec(y * 10 - 5 - 1, 3, 1f);
        paramsTop.width = 0;
        paramsTop.height = 0;
        viewTop.setLayoutParams(paramsTop);

        View viewBottom = new View(this);
        viewBottom.setBackgroundColor(Color.argb(255, 0, 0, 255));
        gridLayout.addView(viewBottom);
        GridLayout.LayoutParams paramsBottom = (GridLayout.LayoutParams) viewBottom.getLayoutParams();
        paramsBottom.rowSpec = GridLayout.spec(x * 10 - 5 + 2, 1, 1f);
        paramsBottom.columnSpec = GridLayout.spec(y * 10 - 5 - 1, 3, 1f);
        paramsBottom.width = 0;
        paramsBottom.height = 0;
        viewBottom.setLayoutParams(paramsBottom);

        View viewLeft = new View(this);
        viewLeft.setBackgroundColor(Color.argb(255, 0, 0, 255));
        gridLayout.addView(viewLeft);
        GridLayout.LayoutParams paramsLeft = (GridLayout.LayoutParams) viewLeft.getLayoutParams();
        paramsLeft.rowSpec = GridLayout.spec(x * 10 - 5 - 1, 3, 1f);
        paramsLeft.columnSpec = GridLayout.spec(y * 10 - 5 - 2, 1, 1f);
        paramsLeft.width = 0;
        paramsLeft.height = 0;
        viewLeft.setLayoutParams(paramsLeft);

        View viewRight = new View(this);
        viewRight.setBackgroundColor(Color.argb(255, 0, 0, 255));
        gridLayout.addView(viewRight);
        GridLayout.LayoutParams paramsRight = (GridLayout.LayoutParams) viewRight.getLayoutParams();
        paramsRight.rowSpec = GridLayout.spec(x * 10 - 5 - 1, 3, 1f);
        paramsRight.columnSpec = GridLayout.spec(y * 10 - 5 + 2, 1, 1f);
        paramsRight.width = 0;
        paramsRight.height = 0;
        viewRight.setLayoutParams(paramsRight);
    }


    public void countDown() {
        final int[] leftTime = {10};
        TextView countdownView = findViewById(R.id.countdown);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                leftTime[0]--;
                countdownView.setText(String.valueOf(leftTime[0]));

                if (leftTime[0] == 0) {
                    countdownView.setText("Time's up!");

                    if (tttService.currentPlayer.equals("X")) {
                        drawX(oldX, oldY);
                    } else {
                        drawO(oldX, oldY);
                    }

//                    int fieldIndex = (oldX - 1) * 3 + oldY - 1;
//                    Toast.makeText(MainActivity.this, fieldIndex, Toast.LENGTH_SHORT).show();
//                    tttService.gameField.set(fieldIndex, tttService.currentPlayer);

//                    changePlayer();


//                    countDown();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    public void changePlayer() {
        if (tttService.currentPlayer.equals("X")) {
            tttService.currentPlayer = "O";
        } else {
            tttService.currentPlayer = "X";
        }
    }

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

        drawField();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, TicTacToeService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output.setText("Click");
                if (tttServiceBound && !tttService.isActive) {
                    tttService.newGame();
                    output.setText("New Game");
//                    wait 2 seconds
                    drawO(1, 1);
                    drawX(1, 2);
                    drawPreview(oldX, oldY);
                    countDown();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (tttServiceBound) {
            unbindService(connection);
            tttServiceBound = false;
        }

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

                if (XPosition != oldX || ZPosition != oldY){
                    drawPreview(XPosition, ZPosition);
                }


            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Can be left empty for this example
    }
}