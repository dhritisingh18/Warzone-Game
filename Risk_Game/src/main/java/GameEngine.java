import model.GameController;
import model.GameCycle;
import utils.InvalidExecutionException;
import utils.ValidationException;
import java.util.Objects;

/**
 * This class is used to start the game with the Map Editor Phase
 *
 * @author Dhriti Singh
 * @version 1.0.0
 */
public class GameEngine {
    GameCycle d_GameCycle = GameCycle.MapEditor;

    /**
     * Main method to run the game
     * 
     * @param args passed to main if used in command line
     */
    public static void main(String[] args) {
        new GameEngine().start();
    }

    /**
     * The function which runs the whole game in phases
     *
     */
    public void start() {
        try {
            if (Objects.isNull(d_GameCycle)) {
                throw new Exception("Game Phase is Null");
            }
            if (Objects.nonNull(d_GameCycle) && !d_GameCycle.equals(GameCycle.ExitGame)) {
                GameController l_GameController = d_GameCycle.getController();
                if (Objects.isNull(l_GameController)) {
                    throw new Exception("There is no controller");
                }
                d_GameCycle = l_GameController.start(d_GameCycle);
                System.out.println(d_GameCycle + " Phase has been Entered");
                System.out.println("-----------------------------------------------------------------------------------------");
                start();
            }
        } catch (ValidationException | InvalidExecutionException p_Exception) {
            System.err.println(p_Exception.getMessage());
            start();
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }
    }

}
