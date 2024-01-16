package ch.fabkli.gravitytictactoe;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeService extends Service {
    // Binder given to clients.
    private final IBinder binder = new TicTacToeBinder();
    public String currentPlayer = "X";
    public List<String> gameField;

    public boolean isActive = false;


    public void newGame() {
        gameField = new ArrayList<String>(List.of(" ", " ", " ", " ", " ", " ", " ", " ", " "));
        currentPlayer = "X";
        isActive = true;
    }

    public String checkWin() {
        if (gameField.get(0).equals(gameField.get(1)) && gameField.get(1).equals(gameField.get(2)) && !gameField.get(0).equals(" ")) {
            return gameField.get(0);
        } else if (gameField.get(3).equals(gameField.get(4)) && gameField.get(4).equals(gameField.get(5)) && !gameField.get(3).equals(" ")) {
            return gameField.get(3);
        } else if (gameField.get(6).equals(gameField.get(7)) && gameField.get(7).equals(gameField.get(8)) && !gameField.get(6).equals(" ")) {
            return gameField.get(6);
        } else if (gameField.get(0).equals(gameField.get(3)) && gameField.get(3).equals(gameField.get(6)) && !gameField.get(0).equals(" ")) {
            return gameField.get(0);
        } else if (gameField.get(1).equals(gameField.get(4)) && gameField.get(4).equals(gameField.get(7)) && !gameField.get(1).equals(" ")) {
            return gameField.get(1);
        } else if (gameField.get(2).equals(gameField.get(5)) && gameField.get(5).equals(gameField.get(8)) && !gameField.get(2).equals(" ")) {
            return gameField.get(2);
        } else if (gameField.get(0).equals(gameField.get(4)) && gameField.get(4).equals(gameField.get(8)) && !gameField.get(0).equals(" ")) {
            return gameField.get(0);
        } else if (gameField.get(2).equals(gameField.get(4)) && gameField.get(4).equals(gameField.get(6)) && !gameField.get(2).equals(" ")) {
            return gameField.get(2);
        } else if (!gameField.contains(" ")) {
            return "draw";
        } else {
            return " ";
        }
    }

    public boolean setField(int index) {

        if (gameField.get(index).equals(" ")) {
            gameField.set(index, currentPlayer);
            return true;
        } else {
            return false;
        }

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