package components.monsters;

public class LavaFactory extends MonsterFactory {
    @Override
    public Monster createMonster(String id, String name, String imageUrl) {
        return new Lava(id, name, imageUrl);
    }
}
