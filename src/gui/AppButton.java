package gui;

import javafx.scene.control.Button;

/**
 * Gamestartfenster
 * 
 * @author hugo-lucca
 *
 */
public class AppButton extends Button {
	
	final static int DEFAULT_BUTTON_WIDTH = 200;
	final static int DEFAULT_BUTTON_MIN_WIDTH = 150;
	/**
	 * Wir leiten eine Zwischenklasse ab, damit alle Buttons gleich initialisert werden können
	 * 
	 * Die Idee dahinter:
	 * -----------------
	 * - varaible Werte wie Grösse, die evtl. später noch zu berechnen sind, hier in FX lösen, 
	 * 	 da dies ja in CSSn (ohne Web-Kit) nicht geht (keine Variablen)
	 * - den Rest der Darstellung in der CSS vornehmen, dort ist viel einfacher
	 * 
	 * @autor hugo-lucca
	 */
	public AppButton (String value) {
		super(value);
		setMinWidth(DEFAULT_BUTTON_MIN_WIDTH);
		setMaxWidth(DEFAULT_BUTTON_WIDTH);	// alle Buttons sollen gleich gross sein
											// bestimme ihr aussehen in des CSS Datei
		
		// TODO: der Wert von defaultButtonWidth sollte sich dem Geräte-BS anpassen können
	}
}
