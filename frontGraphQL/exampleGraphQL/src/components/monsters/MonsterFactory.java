package components.monsters;

// Creator (Creador)
public abstract class MonsterFactory {
    // Método abstracto que las subclases deben implementar para crear un objeto Monster.
    public abstract Monster createMonster(String id, String name, String imageUrl);
}