package tutto;


import processing.core.PApplet;

public class Button {
	PApplet parent;
	Wuerfel wuerfel;
	int xposRec1 = 390;
	int yposRec1 = 633;
	int hoeheRec = 30;
	int breiteRec = 60;
	boolean rectOver1 = false;
	boolean rectOver2 = false;
	boolean rectOver3 = false;

	int abstandHoehe = 60;
	int abstandBreite = 100;

	int yposRec2 = yposRec1+abstandHoehe;
	int yposRec3 = yposRec1;

	int xposRec2 = xposRec1 + 50;
	int xposRec3 = xposRec1 + abstandBreite;

	
	
	
	public Button(PApplet p){
		parent = p;
	}
	
	public void myDrawButton() {
		
		
		parent.fill(255,204,229);
		parent.rect(xposRec1, yposRec1, breiteRec, hoeheRec);	
		parent.rect(xposRec2, yposRec2, breiteRec, hoeheRec);
		parent.rect(xposRec3, yposRec3, breiteRec, hoeheRec);

		buttonUpdate(parent.mouseX, parent.mouseY);
		parent.fill(0);
		parent.textSize(12);
		parent.text("würfeln", 397, 652);
		parent.text("Hilfe", 454, 712);
		parent.text("beenden", 495, 652);
	}
	
	public void buttonUpdate(int x, int y){
		if(overButton(xposRec1, yposRec1, breiteRec, hoeheRec) == true ){
			rectOver1 = true;
		
	}else{
		rectOver1 = false;
	}
				if(overButton(xposRec2, yposRec2, breiteRec, hoeheRec) == true){
					rectOver2 = true;
					
				}else{
					rectOver2 = false;
				}
				if(overButton(xposRec3, yposRec3, breiteRec, hoeheRec) == true){
					rectOver3 = true;
					
				}else{
					rectOver3 = false;
				}
	}
	
	public boolean overButton(float x, float y, float width, float height){
		if(parent.mouseX >= x && parent.mouseX <= x + width &&
				parent.mouseY >= y && parent.mouseY <= y + height){
			
		return true;
		}else{
			return false;
		}
	}
	
}
