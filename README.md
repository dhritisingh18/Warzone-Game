# Risk Game Implementation


## Problem Statement

The project involves building a simple "Risk" computer game compatible with the rules and map files of the "Warzone" version of Risk, which can be downloaded from [Warzone](https://www.warzone.com/). Warzone's game setup includes a connected graph map representing a world map, with each node as a country and each edge representing adjacency between countries. Players place armies on countries they own and attack adjacent countries to conquer them, aiming to conquer all countries on the map.

![image](https://github.com/dhritisingh18/Warzone-Game/assets/56754119/c13cb06b-6a62-4204-bb49-ec5f417a4373)

## Map

The game map is a connected graph where each node represents a territory owned by a player, with edges representing adjacency between territories. Territories are grouped into continents, each with a control value determining the number of armies per turn given to a player controlling the continent. The game allows playing on any connected graph defined by the user, saved as a text file representation, and loaded during play.

## Game

The game consists of a startup phase where the number of players is determined, territories are randomly assigned, and a turn-based main play phase begins. Players issue deployment, advance, and special orders during their turn. Orders include deploying armies, advancing armies to adjacent territories, and using cards for special actions like bombing or diplomacy. The game continues until one player owns all territories on the map.

For detailed rules and order execution, refer to the provided project description.

## Design Patterns

This project employs various design patterns to maintain code flexibility and scalability:

- **State Pattern**: Used to manage different states of the game, such as startup, main play, and end game phases.
- **Singleton Pattern**: Utilized for classes that should only have one instance, such as the game engine or map generator.
- **Command Pattern**: Implemented to encapsulate game actions as objects, allowing for easy undo/redo functionality and extensibility.
- **Observer Pattern**: Employed to notify players and other game components of state changes, such as territory ownership or card acquisitions.
- **Builder Pattern**: Used to construct complex objects such as the game map or player configurations, providing a fluent interface for easy construction.
- **Strategy Pattern**: Employed to define different strategies for handling player turns, allowing for dynamic behavior based on game state.
- **Adapter Pattern**: Utilized to adapt external map files or input/output formats to the game's internal representation, ensuring compatibility and seamless integration.

## Usage

To use this Risk game implementation, follow these steps:

1. Clone the repository to your local machine.
2. Install the required dependencies.
3. Run the program and follow the on-screen instructions to play the game.



## JavaDoc and Testing

- Comprehensive JavaDoc comments are provided throughout the codebase to document classes, methods, and their parameters.
- Unit tests using JUnit are implemented to ensure the correctness of critical game functionalities. Tests cover various scenarios, including player turns, card interactions, and map generation.


## Screenshots of Outputs

![image](https://github.com/dhritisingh18/Warzone-Game/assets/56754119/2cd30235-07a4-4b5b-9d02-5949442f6e05)

![image](https://github.com/dhritisingh18/Warzone-Game/assets/56754119/2baf50fb-1b49-44ef-a9e8-618ce463d873)

![image](https://github.com/dhritisingh18/Warzone-Game/assets/56754119/b885873c-5975-4f37-9e7e-c62103166f09)

![image](https://github.com/dhritisingh18/Warzone-Game/assets/56754119/f9b64e03-c218-43f2-bd4a-1cd56e2c733b)

![image](https://github.com/dhritisingh18/Warzone-Game/assets/56754119/1cae9a76-f7d9-456b-b46f-5862a402794e)

![image](https://github.com/dhritisingh18/Warzone-Game/assets/56754119/1a61959c-f42c-4e54-bacc-5025da274699)






