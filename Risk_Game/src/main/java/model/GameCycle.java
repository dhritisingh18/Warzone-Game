package model;

import controller.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum managing different phases of Warzone Game.
 * <p>Contains list 0f states and list of possible states to move
 * from current state.</p>
 * Also provides respective {@code controller object} for each state.
 *
 * @author Mohammad Ehtesham Arif
 * @author Dhriti Singh
 * @author Rabia Tahir
 * @author Simran Simran
 * @author Ritik Gulati
 * @author Ritika Dhamija
 * @version 1.0.0
 */
public enum GameCycle {
    /**
     * MapBuilder state handling map creation and validation operations
     */

    MapBuilder {
        /**
         * Overrides possibleStates() method as it is abstract in nature which in turn returns the list
         * of allowed next states from MapBuilder state
         * @return List of allowed states from {@code MapEditor phase}
         */
        @Override
        public List<GameCycle> possibleStates() {
            return Collections.singletonList(GameLoad);
        }

        /**
         * Overrides getController() method  as it is abstract in nature which in turn returns the controller
         * for map editor game phase.
         *
         * @return MapBuilder Object
         */
        @Override
        public GameController getController() {
            return new MapEditor();
        }
    },

    /**
     * GameLoad state handling load map, player creation and countries
     * allocation operations
     */
    GameLoad {
        /**
         * Overrides possibleStates() method as it is abstract in nature which in turn returns the list
         * of allowed next states from LoadGame state
         *
         * @return List of allowed states from {@code LoadGame phase}
         */
        @Override
        public List<GameCycle> possibleStates() {
            return Collections.singletonList(Reinforcement);
        }

        /**
         * Overrides getController() method as it is abstract in nature which in turn the controller
         * for game play or load game phase.
         *
         * @return GamePlay Object
         */
        @Override
        public GameController getController() {
            return new GamePlay();
        }
    },

    /**
     * Reinforcement state handling allocation of reinforcement
     * armies to each player after completing execute orders phase
     */
    Reinforcement {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from Reinforcement state
         *
         * @return List of allowed states from {@code Reinforcement phase}
         */
        @Override
        public List<GameCycle> possibleStates() {
            return Collections.singletonList(OrderIssuance);
        }

        /**
         * Overrides getController() method which returns the controller
         * for reinforcement phase.
         *
         * @return Reinforcement Object
         */
        @Override
        public GameController getController() {
            return new Reinforcement();
        }
    },

    /**
     * OrderIssuance state provides list of orders to allow all the players
     */
    OrderIssuance {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from IssueOrder state
         *
         * @return List of allowed states from {@code IssueOrder phase}
         */
        @Override
        public List<GameCycle> possibleStates() {
            return Collections.singletonList(OrderExecution);
        }

        /**
         * Overrides getController() method which returns the controller
         * for issue order phase.
         *
         * @return IssueOrder Object
         */
        @Override
        public GameController getController() {
            return new IssueOrder();
        }
    },

    /**
     * OrderExecution state allows game engine to execute provided orders
     */
    OrderExecution {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from ExecuteOrder state
         *
         * @return List of allowed states from {@code ExecuteOrder phase}
         */
        @Override
        public List<GameCycle> possibleStates() {
            return Arrays.asList(Reinforcement, GameExit);
        }

        /**
         * Overrides getController() method which returns the controller
         * for execute order phase.
         *
         * @return ExecuteOrder Object
         */
        @Override
        public GameController getController() {
            return new ExecuteOrder();
        }
    },

    /**
     * In GameExit state  once all countries are conquered, ends the game.
     */
    GameExit {
        /**
         * Overrides possibleStates() method which returns the list
         * of allowed next states from ExitGame state
         *
         * @return List of allowed states from {@code ExitGame phase}
         */
        @Override
        public List<GameCycle> possibleStates() {
            return null;
        }

        /**
         * Overrides getController() method which returns the controller
         * for exit game phase.
         *
         * @return null
         */
        @Override
        public GameController getController() {
            return null;
        }
    };

    /**
     * Checks if the next game phase returned from current
     * controller is present in its possible states list
     *
     * @param p_GameCycle Next phase of game
     * @return Next game phase if valid else Current game phase
     */
    public GameCycle nextState(GameCycle p_GameCycle) {
        if (this.possibleStates().contains(p_GameCycle)) {
            return p_GameCycle;
        } else return this;
    }

    /**
     * Abstract method to get list of next phases from current phase
     *
     * @return list of next possible states
     */
    public abstract List<GameCycle> possibleStates();

    /**
     * Returns controller for each phase
     *
     * @return Respective Controller object
     */
    public abstract GameController getController();
}
