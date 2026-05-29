package components.monsters;

public class FlowerFactory extends MonsterFactory {
    @Override
    public Monster createMonster(String id, String name, String imageUrl) {
        return new Flower(id, name, imageUrl);
    }
}
