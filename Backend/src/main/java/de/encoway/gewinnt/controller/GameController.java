package de.encoway.gewinnt.controller;

import de.encoway.gewinnt.exceptions.BoardFullException;
import de.encoway.gewinnt.exceptions.WinException;
import de.encoway.gewinnt.model.Game;
import de.encoway.gewinnt.model.Move;
import de.encoway.gewinnt.model.Player;
import de.encoway.gewinnt.service.GameService;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aleksandar Pantelic
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/game")
class GameController {

    private final GameService gameService;
    private final String BOARD_FULL = "The board is full!";
    private final String NOT_ALLOWED = "Not allowed!";

    /**
     * Creates a {@link GameController}
     *
     * @param gameService the game logic
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Initializes a game by sending an empty game
     *
     * @return the {@link ResponseEntity} which contains the initial game data
     */
    @GetMapping("/initGame")
    public ResponseEntity<String> initGame() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(fillGame(gameService.createNewGame(), "New game started!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JSONObject().put("message", "Could not initialize game!").toString());
        }
    }

    /**
     * Makes a move, which usually results in a token being placed on the board
     *
     * @param move The object containing the desired column at which the move should
     *             be made
     * @return the {@link ResponseEntity} which corresponds to the move's outcome
     */
    @PostMapping("/makeMove")
    public ResponseEntity<String> makeMove(@RequestBody Move move) {
        final int colIndex = move.column();
        final Game game = gameService.getGame(move.id());
        final Player cp = game.getCurrentPlayer();
        final String WIN_MSG = cp.getName() + " has won!";
        final String PLACEMENT = cp.getName() + " dropped the token at column no. " + (colIndex + 1);
        try {
            gameService.dropTokenIfPossible(game, colIndex);
            gameService.checkForWin(game);
            gameService.checkIfBoardFull(game);
            gameService.toggleCurrentPlayer(game);
            gameService.saveGame(game);
            return ResponseEntity.status(HttpStatus.OK).body(fillGame(game, PLACEMENT));
        } catch (WinException e) {
            return ResponseEntity.status(HttpStatus.OK).body(fillGame(game, WIN_MSG));
        } catch (BoardFullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fillGame(game, BOARD_FULL));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fillGame(game, NOT_ALLOWED));
        }
    }

    private String fillGame(Game game, String msg) {
        final JSONObject gameObject = new JSONObject();
        if (!(msg.equals(BOARD_FULL) || msg.equals(NOT_ALLOWED))) {
            fillWithAllData(gameObject, game);
        }
        gameObject.put("id", game.getId());
        gameObject.put("message", msg);
        return gameObject.toString();
    }

    private void fillWithAllData(JSONObject gameObject, Game game) {
        final JSONObject playerObject = new JSONObject();
        playerObject.put("token", (String.valueOf(game.getCurrentPlayer().getToken())));
        playerObject.put("name", game.getCurrentPlayer().getName());
        gameObject.put("board", game.getBoard().getBoardArr());
        gameObject.put("currentPlayer", playerObject);
    }
    @GetMapping
    public ResponseEntity<String> getGame() {
        // Ihre Logik hier
        try {
            return new ResponseEntity<>("Game data", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new JSONObject().put("message", "Could not initialize game!").toString());
        }
    }
}
