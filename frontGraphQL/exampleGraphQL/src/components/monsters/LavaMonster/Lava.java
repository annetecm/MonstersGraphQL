package components.monsters;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

// Producto Concreto
public class Lava implements Monster {
    private final String id;
    private final String name;
    private final String imageUrl;

    public Lava(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String renderHtml() {
        return "<html>" +
                "<div style='text-align:center; font-family:sans-serif;'>" +
                "<h2>" + name + "</h2>" +
                "<p><strong>ID:</strong> " + id + "</p>" +
                "<img src='" + imageUrl + "' width='250' height='250' alt='" + name + "'/>" +
                "</div>" +
                "</html>";
    }

    @Override
    public void showMonster(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);
        label.setText(renderHtml());
        label.setIcon(null);
    }
}
