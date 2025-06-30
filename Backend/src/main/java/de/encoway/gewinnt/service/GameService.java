package de.encoway.gewinnt.service;

import java.util.Optional;
import java.util.UUID;

import de.encoway.gewinnt.exceptions.BoardFullException;
import de.encoway.gewinnt.exceptions.WinException;
import de.encoway.gewinnt.model.Board;
import de.encoway.gewinnt.model.DatabaseObject;
import de.encoway.gewinnt.model.Game;
import de.encoway.gewinnt.persistence.GameRepository;
import de.encoway.gewinnt.utils.WinChecker;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

/**
 * @author Aleksandar Pantelic
 */
@Service
public class GameService {

    private final GameRepository gameRepository;

    /**
     * Creates a {@link GameService}
     *
     * @param gameRepository - the repository for the game
     */
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * If its id is found, a game
     * @param id the id of the game
     * @return Returns the game
     */
    public Game getGame(String id) {
        if (gameRepository.findById(id).isPresent()) {
            return deserialize(gameRepository.findById(id).get());
        } else {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private Game deserialize(DatabaseObject game) {
        return (Game) SerializationUtils.deserialize(game.getData());
    }

    public void saveGame(Game game) {
        gameRepository.save(serialize(game));
    }

    private DatabaseObject serialize(Game game) {
        return new DatabaseObject(game.getId(), SerializationUtils.serialize(game));
    }

    /**
     * Toggles the game object's current player
     */
    public void toggleCurrentPlayer(Game game) {
        if (game.getCurrentPlayer().equals(game.getPlayer1())) {
            game.setCurrentPlayer(game.getPlayer2());
        } else {
            game.setCurrentPlayer(game.getPlayer1());
        }
    }

    /**
     * Creates a new game
     *
     * @return the created game
     */
    public Game createNewGame() {
        Game game = new Game(generateId());
        saveGame(game);
        return game;
    }

    private String generateId() {
        String preliminaryId = null;
        boolean isDuplicate = true;
        while (isDuplicate) {
            preliminaryId = UUID.randomUUID().toString().replace("-", "");
            Optional<DatabaseObject> dbo = gameRepository.findById(preliminaryId);
            if (dbo.isEmpty()) {
                isDuplicate = false;
            }
        }
        return preliminaryId;
    }

    /**
     * Checks whether there is sufficient space in the given column in order to drop a token into it
     *
     * @param colIndexInput The index of the column where the token should be dropped into
     * @throws IllegalArgumentException if it was not possible to drop a token into the given column
     */
    public void dropTokenIfPossible(Game game, int colIndexInput) {
        try {
            if (isColIndexValid(colIndexInput)) {
                final Board board = game.getBoard();
                board.setBoardArr(updateBoardWithToken(game, colIndexInput, spaceAvailableInColAtRow(game, colIndexInput)));
                saveGame(game);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not drop token!", e.getCause());
        }
    }

    private boolean isColIndexValid(int colIndexInput) {
        if ((colIndexInput >= 0) && (colIndexInput <= (Board.NUM_OF_COLS - 1))) {
            return true;
        } else {
            throw new ArrayIndexOutOfBoundsException("Invalid column index!");
        }
    }

    private char[][] updateBoardWithToken(Game game, int colIndexInput, int i) {
        final char[][] boardArr = game.getBoard().getBoardArr();
        boardArr[i][colIndexInput] = game.getCurrentPlayer().getToken();
        return boardArr;
    }

    private int spaceAvailableInColAtRow(Game game, int colIndexInput) {
        final char[][] boardArr = game.getBoard().getBoardArr();
        for (int i = (Board.NUM_OF_ROWS - 1); i >= 0; i--) {
            if (boardArr[i][colIndexInput] == 0) {
                return i;
            }
        }
        throw new ArrayIndexOutOfBoundsException("No more space in column!");
    }

    /**
     * Checks whether the board is full, i.e. it cannot take any further tokens
     *
     * @throws IllegalArgumentException if there is no board
     */
    public void checkIfBoardFull(Game game) throws BoardFullException {
        if (game.getBoard() == null) {
            throw new IllegalArgumentException("No board available!");
        }
        for (char[] arr : game.getBoard().getBoardArr()) {
            for (char item : arr) {
                if (item == '\u0000') {
                    return;
                }
            }
        }
        throw new BoardFullException();
    }

    /**
     * Checks whether the current player has achieved a win, i.e. 4 of his tokens are placed consecutively in vertical, horizontal or diagonal order. In order
     * to do this, {@link WinChecker#checkForWin(Game)} gets called.
     */
    public void checkForWin(Game game) throws WinException {
        WinChecker.checkForWin(game);
    }
}
