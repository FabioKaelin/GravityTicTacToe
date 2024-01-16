package ch.fabkli.gravitytictactoe;

import junit.framework.TestCase;

import java.util.List;

public class TicTacToeServiceTest extends TestCase {

    public void testNewGame() {
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        assertEquals(ticTacToeService.gameField, List.of(" ", " ", " ", " ", " ", " ", " ", " ", " "));
        assertEquals(ticTacToeService.currentPlayer, "X");
        assertTrue(ticTacToeService.isActive);
    }

    public void testCheckWinX(){
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        ticTacToeService.gameField = List.of("X", "X", "X", " ", " ", " ", " ", " ", " ");
        assertEquals(ticTacToeService.checkWin(), "X");
    }

    public void testCheckWinDraw(){
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        ticTacToeService.gameField = List.of("X", "O", "X", "O", "X", "O", "O", "X", "O");
        assertEquals(ticTacToeService.checkWin(), "draw");
    }

    public void testCheckWinO(){
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        ticTacToeService.gameField = List.of("O", "O", "O", " ", " ", " ", " ", " ", " ");
        assertEquals(ticTacToeService.checkWin(), "O");
    }

    public void testCheckWinNobody(){
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        ticTacToeService.gameField = List.of(" ", " ", " ", " ", " ", " ", " ", " ", " ");
        assertEquals(ticTacToeService.checkWin(), " ");
    }

    public void testCheckWin() {
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        assertEquals(ticTacToeService.checkWin(), " ");
        ticTacToeService.gameField = List.of("X", "X", "X", " ", " ", " ", " ", " ", " ");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of(" ", " ", " ", "X", "X", "X", " ", " ", " ");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of(" ", " ", " ", " ", " ", " ", "X", "X", "X");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of("X", " ", " ", "X", " ", " ", "X", " ", " ");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of(" ", "X", " ", " ", "X", " ", " ", "X", " ");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of(" ", " ", "X", " ", " ", "X", " ", " ", "X");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of("X", " ", " ", " ", "X", " ", " ", " ", "X");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of(" ", " ", "X", " ", "X", " ", "X", " ", " ");
        assertEquals(ticTacToeService.checkWin(), "X");
        ticTacToeService.gameField = List.of(" ", " ", " ", " ", " ", " ", " ", " ", " ");
        assertEquals(ticTacToeService.checkWin(), " ");
        ticTacToeService.gameField = List.of("O", "O", "O", " ", " ", " ", " ", " ", " ");
        assertEquals(ticTacToeService.checkWin(), "O");
        ticTacToeService.gameField = List.of("X", "O", "X", "O", "X", "O", "O", "X", "O");
        assertEquals(ticTacToeService.checkWin(), "draw");
    }

    public void testSetFieldAlreadySet() {
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        assertTrue(ticTacToeService.setField(0));
        ticTacToeService.currentPlayer = "O";
        assertFalse(ticTacToeService.setField(0));
        assertEquals(ticTacToeService.gameField, List.of("X", " ", " ", " ", " ", " ", " ", " ", " "));
    }

    public void testSetField() {
        TicTacToeService ticTacToeService = new TicTacToeService();
        ticTacToeService.newGame();
        assertTrue(ticTacToeService.setField(0));
        assertFalse(ticTacToeService.setField(0));
        assertEquals(ticTacToeService.gameField, List.of("X", " ", " ", " ", " ", " ", " ", " ", " "));
        assertTrue(ticTacToeService.setField(1));
        assertEquals(ticTacToeService.gameField, List.of("X", "X", " ", " ", " ", " ", " ", " ", " "));
        assertTrue(ticTacToeService.setField(2));
        assertEquals(ticTacToeService.gameField, List.of("X", "X", "X", " ", " ", " ", " ", " ", " "));
        assertTrue(ticTacToeService.setField(3));
        assertEquals(ticTacToeService.gameField, List.of("X", "X", "X", "X", " ", " ", " ", " ", " "));

        ticTacToeService.currentPlayer = "O";
        assertTrue(ticTacToeService.setField(4));
        assertEquals(ticTacToeService.gameField, List.of("X", "X", "X", "X", "O", " ", " ", " ", " "));
        assertTrue(ticTacToeService.setField(5));
        assertEquals(ticTacToeService.gameField, List.of("X", "X", "X", "X", "O", "O", " ", " ", " "));
        assertFalse(ticTacToeService.setField(3));
    }
}