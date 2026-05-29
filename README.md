# MonstersGraphQL
## Observer Pattern 
The Observer Pattern is a design pattern where an object keeps track of changes and another object called listener is checking or 'listening' for those changes, when the subject changes, the observer is notified automatically, it reacts to the notification and updates themselves.
In our code the observer pattern is found on our React Frontend, we implemented it using react hooks:
We used useState(), we with two state variables monsters, which holds all the monster data and error which holds any error messages.Those are the objects being observed; on rhe other hand, we used useEffect() as the observer mechanism: the useEffect posts the GraphQL getAllMonsters query and sets monsters or error, and because React re-renders whenever state changes, the UI automatically updates. The component takes the monsters data and groups them by category, then creates a section and a list for each group. When the monsters state changes, those groups and the shown cards update automatically.

# Factory Method Pattern

The monster factory implementation is located in `src/components/monsters`.
It is organized as follows:

- `Monster.java`
  - Defines the `Monster` interface for all monster objects.
  - Declares methods like `getName()`, `getId()`, `getImageUrl()`, `renderHtml()`, and `showMonster(...)`.

- `MonsterFactory.java`
  - Defines the abstract creator class.
  - Declares the factory method:

    ```java
    public abstract Monster createMonster(String id, String name, String imageUrl);
    ```

- `FlowerMonster/`
  - `Flower.java`: concrete product that implements `Monster`.
  - `FlowerFactory.java`: concrete factory that creates `Flower` instances.

- `LavaMonster/`
  - `Lava.java`: concrete product that implements `Monster`.
  - `LavaFactory.java`: concrete factory that creates `Lava` instances.

- `WaterMonster/`
  - `Water.java`: concrete product that implements `Monster`.
  - `WaterFactory.java`: concrete factory that creates `Water` instances.

## What each file does

### `Monster.java`

Defines the common behavior for all monsters.
Every concrete monster class must implement this interface.

### `MonsterFactory.java`

Defines the abstract factory method signature.
Concrete factories override this method to return a specific `Monster` subtype.

### `FlowerFactory.java`, `LavaFactory.java`, `WaterFactory.java`

Each of these classes extends `MonsterFactory`.
They implement `createMonster(...)` to instantiate their specific monster type.
For example, `FlowerFactory` returns a new `Flower` object.

### `Flower.java`, `Lava.java`, `Water.java`

These classes are concrete products.
They each store `id`, `name`, and `imageUrl`, and provide monster-specific rendering logic.
The shared `showMonster(JLabel label)` method displays the monster details in a Swing label.

## How it creates monsters

The process is:

1. A client chooses which factory to use.
2. The client calls the factory method `createMonster(id, name, imageUrl)`.
3. The concrete factory constructs and returns the right monster type.
