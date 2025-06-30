package de.encoway.gewinnt.logic;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.encoway.gewinnt.exceptions.WinException;
import de.encoway.gewinnt.model.DatabaseObject;
import de.encoway.gewinnt.model.Game;
import de.encoway.gewinnt.persistence.GameRepository;
import de.encoway.gewinnt.service.GameService;
import de.encoway.gewinnt.utils.WinChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.SerializationUtils;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService classUnderTest;
    @Mock
    private GameRepository gameRepository;

    @Test
    void testSaveNewGame() {
        Game game = mock(Game.class);
        classUnderTest.createNewGame();
        verify(gameRepository).save(any());
    }

    @Test
    void testWin() {
        Game game = mock(Game.class);
        WinException thrown = Assertions.assertThrows(WinException.class, () -> {
            try (MockedStatic<WinChecker> winCheckerMockedStatic = mockStatic(WinChecker.class)) {
                winCheckerMockedStatic.when(() -> WinChecker.checkForWin(game))
                        .thenThrow(WinException.class);
                classUnderTest.checkForWin(game);
            }
        });
        Assertions.assertEquals(WinException.class, thrown.getClass());
    }

    @Test
    void testNoWin() throws WinException {
        Game game = mock(Game.class);
        try (MockedStatic<WinChecker> winCheckerMockedStatic = mockStatic(WinChecker.class)) {
            classUnderTest.checkForWin(game);
            winCheckerMockedStatic.verify(() -> WinChecker.checkForWin(game));
        }
    }

    /*
     * @Test
     * void testTogglePlayer() {
     * Game game = mock(Game.class);
     * System.out.println(game.getCurrentPlayer());
     * classUnderTest.toggleCurrentPlayer(game);
     * //Assertions.assertEquals(play);
     * }
     */

    @Test
    void testSaveGamePlayer() {
        Game game = mock(Game.class);
        classUnderTest.saveGame(game);
        verify(gameRepository).save(any());
    }

    // @Test
    // void isColIndexValidInvalidNegativeIndex() {
    // assertThrows(ArrayIndexOutOfBoundsException.class, () ->
    // classUnderTest.isColIndexValid(-1, board.getCols()));
    // }
    //
    // @Test
    // void isColIndexValidInvalidPositiveIndex() {
    // assertThrows(ArrayIndexOutOfBoundsException.class, () ->
    // classUnderTest.isColIndexValid(7, board.getCols()));
    // }
    //
    // @Test
    // void spaceAvailableInColSpaceAvailable() {
    //
    // boardArr[4][0] = 'X';
    // boardArr[5][0] = 'X';
    // board.setBoardArr(boardArr);
    // assertEquals(classUnderTest.spaceAvailableInColAtRow(0, board.getRows(),
    // boardArr), 3);
    // }
    //
    // @Test
    // void spaceAvailableInColNoSpace() {
    //
    // boardArr[0][3] = 'X';
    // boardArr[1][3] = 'X';
    // boardArr[2][3] = 'X';
    // boardArr[3][3] = 'X';
    // boardArr[4][3] = 'X';
    // boardArr[5][3] = 'X';
    // board.setBoardArr(boardArr);
    // assertThrows(ArrayIndexOutOfBoundsException.class, () ->
    // classUnderTest.spaceAvailableInColAtRow(3, board.getRows(), boardArr));
    //
    // @Test
    // void checkForWinNoWinHorizontal () {
    //
    // boardArr[5][0] = 'X';
    // boardArr[5][1] = 'X';
    // boardArr[5][2] = 'X';
    // board.setBoardArr(boardArr);
    // assertFalse(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinNoWinVertical () {
    //
    // boardArr[5][0] = 'O';
    // boardArr[4][0] = 'O';
    // boardArr[3][0] = 'O';
    // board.setBoardArr(boardArr);
    // assertFalse(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinNoWinDiagonalAscending () {
    //
    // boardArr[4][1] = 'X';
    // boardArr[3][2] = 'X';
    // boardArr[2][3] = 'X';
    // board.setBoardArr(boardArr);
    // assertFalse(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinNoWinDiagonalDescending () {
    //
    // boardArr[5][6] = 'X';
    // boardArr[4][5] = 'X';
    // boardArr[3][4] = 'X';
    // board.setBoardArr(boardArr);
    // assertFalse(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinHorizontal () {
    //
    // boardArr[5][3] = 'X';
    // boardArr[5][4] = 'X';
    // boardArr[5][5] = 'X';
    // boardArr[5][6] = 'X';
    // board.setBoardArr(boardArr);
    // assertTrue(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinVertical () {
    //
    // boardArr[5][6] = 'X';
    // boardArr[4][6] = 'X';
    // boardArr[3][6] = 'X';
    // boardArr[2][6] = 'X';
    // board.setBoardArr(boardArr);
    // assertTrue(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinDiagonalAscendingTop () {
    //
    // boardArr[5][0] = 'X';
    // boardArr[4][1] = 'X';
    // boardArr[3][2] = 'X';
    // boardArr[2][3] = 'X';
    // board.setBoardArr(boardArr);
    // assertTrue(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinDiagonalAscendingBottom () {
    //
    // boardArr[3][2] = 'X';
    // boardArr[2][3] = 'X';
    // boardArr[1][4] = 'X';
    // boardArr[0][5] = 'X';
    // board.setBoardArr(boardArr);
    // assertTrue(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinDiagonalDescendingTop () {
    //
    // boardArr[0][0] = 'X';
    // boardArr[1][1] = 'X';
    // boardArr[2][2] = 'X';
    // boardArr[3][3] = 'X';
    // board.setBoardArr(boardArr);
    // assertTrue(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkForWinDiagonalDescendingBottom () {
    //
    // boardArr[2][3] = 'X';
    // boardArr[3][4] = 'X';
    // boardArr[4][5] = 'X';
    // boardArr[5][6] = 'X';
    // board.setBoardArr(boardArr);
    // assertTrue(classUnderTest.checkForWin(board, player1));
    // }
    //
    // @Test
    // void checkIfBoardFullNotFull () {
    //
    // boardArr[2][3] = 'X';
    // board.setBoardArr(boardArr);
    // assertFalse(classUnderTest.checkIfBoardFull(board));
    // }
    //
    // @Test
    // void checkIfBoardFullFullBoard () {
    //
    // for (char[] row : boardArr) {
    // Arrays.fill(row, 'X');
    // }
    // board.setBoardArr(boardArr);
    // assertTrue(classUnderTest.checkIfBoardFull(board));
    // }
    //
    // @Test
    // void checkIfBoardFullEmptyBoard () {
    //
    // board.setBoardArr(boardArr);
    // assertFalse(classUnderTest.checkIfBoardFull(board));
    // }
    //
    // @Test
    // void checkIfBoardFullForNoBoard () {
    // board = null;
    // assertThrows(IllegalArgumentException.class, () ->
    // classUnderTest.checkIfBoardFull(null));
    // }
}