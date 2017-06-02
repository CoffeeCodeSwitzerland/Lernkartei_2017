package views.components;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
/**
 * Um die Button Voreinstellungen zentral zu halten, 
 * die nicht im CSS vorgenommen werden sollen
 * 
 * TODO: die Werte sollten sich evtl. der Fenstergrösse anpassen können
 * 
 * @autor hugo-lucca
 */
public class AppButton extends Button {
	
	public final static int DEFAULT_BUTTON_WIDTH = 200;
	public final static int DEFAULT_BUTTON_MIN_WIDTH = 150;

	public AppButton (String value) {
		super(value);
		setMinWidth(DEFAULT_BUTTON_MIN_WIDTH);
		setMaxWidth(DEFAULT_BUTTON_WIDTH);
		this.setId("appbtn");
		this.setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.ENTER)
				this.fire();
		});
	}
}
