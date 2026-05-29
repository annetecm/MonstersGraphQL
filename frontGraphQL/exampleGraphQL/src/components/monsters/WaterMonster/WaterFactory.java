package components.monsters;

// Creador Concreto
public class WaterFactory extends MonsterFactory {
    @Override
    public Monster createMonster(String id, String name, String imageUrl) {
        return new Water(id, name, imageUrl);
    }
}