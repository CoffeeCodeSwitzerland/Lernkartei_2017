# ======================================================================================== #
#	Model-View-Controller (MVC) - Konzept Applikatiosneutral, aber FX spezifisch            #
	- nur Kernfunktionen für das MVC Zusammenspiel
	- für Applikationen mit relativ einfacher Navigation
	
>	@AUTHOR hugo-lucca
	@DATE	29. Mai 2016
# ======================================================================================== #

##	Content:
>	*FXController-Klasse*:
		- FXController			Abstrakte Klasse für den Controller (C).
								Hat mindestens eine erbende Klasse, der "MainController" im app-package (controls).
		
>	*FXModel-Klasse*:
		- FXModel				abstract fx specific model (to be inherited from application model in models-package)
								reason: in fx the arraylist's may be observable (form an other collection)
		
>	*FXView-Klassen*:
		- FXStage				to maipulate stages (experimantal)
		- FXSettings			to maipulate gui settings (experimental)
		- FXView				abstract fx specific view (to be inherited from application view in views-package)
		- FXViewModel			sam as FWView but has a local model
		
##	Dependencies:
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

### ===========================================================================================================================
 
 