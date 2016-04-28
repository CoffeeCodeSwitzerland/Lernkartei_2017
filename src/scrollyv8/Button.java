package scrollyv8;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Button extends Rectangle {

	private static final long serialVersionUID = 1L;
	private Font font, selectedFont;
	private Color color, selectedColor;
	private boolean selected;
	private String text;
	private int textY;

	public Button(String text, int textY, Font font, Font selectedFont, Color color, Color selectedColor) {
		this.text = text;
		this.textY = textY;
		this.font = font;
		this.selectedFont = selectedFont;
		this.color = color;
		this.selectedColor = selectedColor;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void render(Graphics g) {
		if (selected) {
			Fonts.drawString(g, selectedFont, selectedColor, text, textY);
		} else {
			Fonts.drawString(g, font, color, text, textY);
		}
		FontMetrics fm = g.getFontMetrics();
		this.x = (ScrollyV8.width - fm.stringWidth(text)) / 2;
		this.y = textY - fm.getHeight();
		this.width = fm.stringWidth(text);
		this.height = fm.getHeight();
		g.drawRect(x, y, width, height);
	}

}
