package components.monsters;

import javax.swing.JLabel;

// Product (Producto)
// Interfaz base para los enemigos
public interface Monster {
    String getName();
    String getId();
    String getImageUrl();
    String renderHtml();
    void showMonster(JLabel label); // Método para mostrar el enemigo en la GUI
}