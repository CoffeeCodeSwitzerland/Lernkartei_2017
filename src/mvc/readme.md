=============================================================================================================

#	Model-View-Controller (MVC) - Konzept Applikatiosneutral                                #
	- nur Kernfunktionen für das MVC Zusammenspiel
	- GUI Toolkit neutral
	- für Applikationen mit relativ einfacher Navigation
	
>	@Author 	hugo-lucca
>	@Version	1.1 - 16.05.2016
=============================================================================================================

##	Inhalte:
>	- die **Interfaces** definieren die zwingend zu implementierenden Methoden (die Schnittstelle) für dieses MVC Konzept
	- die **abstrakten** Klassen sind "Halbfabrikate", d.h. vorbereitete Implementationen für eine einfache Beerbung
	
>	*Controller-Klassen (C)*:
		- ContollerInterface	Interface für alle **Controller** Klassen
		- Controller			Abstrakte Klasse für den Controller (C).
								Hat mindestens eine erbende Klasse, der "MainController" im app-package (controls).
		
>	*Model-Klassen (M)*:
		- ModelInterface	Interface für alle erbenden **Model** Klassen
		- DataModel			Abstrakte Klasse für ein komplettes Modell mit Datenstrukturen (D-M)
		- Model				Erweiterte Beispiel-Implementation vom DataModel und wird zum bsp. im FXViewModel
		
>	*View-Klassen (V)*:
		- ViewInterface		Interface für alle folgenden **View** Klassen
		- View				Abstrakte Klasse für ein View (V)
		- ViewModel			Ist nur eine Biespielimplementation, sollte nicht direkt beerbt werden, da die
							Toolkit Implementation Vorrang hat und multiple inheritance in Java nicht geht.

##	Abhängigkeiten ##
>	- globals package
	- debug package

##	How to use ##
>	Da es hier nur Halfabrikate (abstarkte Klassen) gibt, sollten zuerst Model, View, ViewModel und Controller GUI-Toolkit in einem separaten package spezifisch abgeleitet werden.
	
>	Die hier implementiert Grundfunktionalität beinhaltet:
	- der Controller hält und verwaltet die Liste der Model's und Views. Das erstellen dieser Komponenten und dem Starten
	  der Applikation soll in der abgeleiteten Klasse erledigt werden, hier wird lediglich die Reihenfolge festgelegt.
	  Dazu muss aber die Abgeleitete Klasse die methode start() bei ihrer Instazierung aufrufen!
	- der Controller bietet die Grundnavigation an. Damit sie funktioniert, muss in der abgeleiteten GUI-View bekannt 
	  sein, welcher GUI-Controller für die navigation zuständig ist, sonst kann man keine Methoden des Controllers aufrufen.
	- die abgeleiteten Views müssen zudem die Methode show() implementieren, damit zu einem View navigiert werden kann
	
>	Die refreshView() Methode vom View wird automatisch aufgerufen, wenn: 
	1. der sich Constructor des View's beim entsprechenden Model angemeldet hat, mit zum Bsp.:
			getController().getModel("model-name").registerView(this)
	2. die implementierte Model-Methode doAction(...) ruft intern die eigene refreshViews() Methode
	   nach Veränderung der eignen Daten auf.

>	Bemerkung: Die Anmeldung beim eigenen lokalen Model im ViewModel erfolgt automatisch und auch das refreshView();
	Es ist zudem kein Problem, wenn das View gleichzeitig bei mehreren Model's angemeldet ist.
	
>	Bei einem DataModel ist es zusätzlich möglich, einen direkten Datenaustausch zwischen Modell und View herzustellen. 
	Typischerweise wird in der View-Methode refreshView() den neuesten Datenstatus abgefragt und im eigenen View aktualisiert.

=========================================================================================================================
 

 
 