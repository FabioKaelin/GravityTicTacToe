package ch.fabkli.gravitytictactoe;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TicTacToeServiceTest{
    TicTacToeService service;
    boolean isBound = false;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            TicTacToeService.TicTacToeBinder binder = (TicTacToeService.TicTacToeBinder) service;
            TicTacToeServiceTest.this.service = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };

    @Before
    public void setUp() {
        Intent intent = new Intent(getApplicationContext(), TicTacToeService.class);
        getApplicationContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serviceBoundTest() {
        assertTrue(isBound);
    }

     @Test
    public void testNewGame() {
        service.newGame();
        assertEquals("X", service.currentPlayer);
        assertEquals(9, service.gameField.size());
        assertEquals(" ", service.gameField.get(0));
        assertEquals(" ", service.gameField.get(1));
        assertEquals(" ", service.gameField.get(2));
        assertEquals(" ", service.gameField.get(3));
        assertEquals(" ", service.gameField.get(4));
        assertEquals(" ", service.gameField.get(5));
        assertEquals(" ", service.gameField.get(6));
        assertEquals(" ", service.gameField.get(7));
        assertEquals(" ", service.gameField.get(8));
        assertTrue(service.isActive);
    }

    @Test
    public void testCheckWin() {
        service.newGame();
        assertEquals(" ", service.checkWin());
        service.gameField = new ArrayList<String>(List.of("X", "X", " ", " ", " ", " ", " ", " ", " "));
        assertEquals(" ", service.checkWin());
        service.gameField = new ArrayList<String>(List.of("X", "X", "X", " ", " ", " ", " ", " ", " "));
        assertEquals("X", service.checkWin());
        service.gameField = new ArrayList<String>(List.of(" ", " ", " ", "O", "O", "O", " ", " ", " "));
        assertEquals("O", service.checkWin());
        service.gameField = new ArrayList<String>(List.of("O", "X", "X", "X", "O", "O", "O", "X", "X"));
        assertEquals("draw", service.checkWin());
    }
}