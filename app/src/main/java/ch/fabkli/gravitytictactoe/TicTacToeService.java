package ch.fabkli.gravitytictactoe;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TicTacToeService extends Service {
    // Binder given to clients.
    private final IBinder binder = new TicTacToeBinder();
    public String currentPlayer = "X";
    public List<String> gameField;

    public boolean isActive = false;


    public void newGame() {
        gameField = List.of(" ", " ", " ", " ", " ", " ", " ", " ", " ");
        currentPlayer = "X";
        isActive = true;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public class TicTacToeBinder extends Binder {
        TicTacToeService getService() {
            // Return this instance of LocalService so clients can call public methods.
            return TicTacToeService.this;
        }
    }
}