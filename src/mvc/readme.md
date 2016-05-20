=============================================================================================================

#	Model-View-Controller (MVC) Konzept für diverse Applikationen                           #
	(GUI Toolkit neutral)
	
	@Author 	hugo-lucca
	@Version	1.1 - 16.05.2016
=============================================================================================================

##	Inhalte:
>	- die Interfaces definieren die zwingend zu implementierenden Methoden (die Schnittstelle)
>	- die abstrakten Klassen sind "Halbfabrikate", d.h. vorbereitete Implementationen für eine einfache Beerbung, so
>	- dass nur noch der für die Applikation notwendige Code bei M, V und C implementiert werden muss 
	
>	- Controller-Klassen (C):
		- ContollerInterface	Interface für alle Controller Klassen
		- Controller			Abstrakte Klasse für den Controller (C).
								Hat evtl. nur eine erbende Klasse, der MainController in der Applikation.
		
>	- Model-Klassen (M):
		- ModelInterface	Interface für alle folgenden Model Klassen
		- DataModel			Abstrakte Klasse für ein komplettes Modell mit Datenstrukturen (D-M)
		- Model				Erweiterte Beispiel-Implementation vom DataModel und dient der Anwendung in ViewModel
		
>	- View-Klassen (V):
		- ViewInterface		Interface für alle folgenden View Klassen
		- View				Abstrakte Klasse für ein View (V)
							Ein neutrales ViewModel lohn sich nicht, denn die Implementation ist trivial, s.u.

##	Abhängigkeiten:
>	- debug-Package
>	- mvc.test-Package beinhaltet die Komponententests
>	- die hiervon abgeleiteten C, M und V werden zu besseren Übersicht in separate packages gehalten 

##	How to use:
>	Da es hier praktisch nur um Halfabrikate geht (abstarkte Klassen),sollten grundsätzlich zuerst 3 weitere Klassen für  
	View, ViewModel und Controller GUI-Toolkit spezifisch abgeleitet werden.
	Erst in diesem separaten package wird erklärt, wie das Ganze dann anzuwenden ist.
	
>	Die hier bereits implementierte Grunfunktionalität betrifft das Zusammenspiel 
	zwischen den 3 Basisklassen Model, View und Controller.
	
>	Die hier zur verfügte Grundfunktionalität ist:
	- der Controller hält und verwaltet die Liste der Model's und Views. Das erstellen dieser Komponenten und dem Starten
	  der Applikation soll in der abgeleiteten Klasse erledigt werden, hier wird lediglich die Reihenfolge festgelegt.
	  Dazu muss aber die Abgeleitete Klasse die methode start() bei ihrer Instazierung aufrufen!
	- der Controller bietet die Grundnavigation an. Damit sie funktioniert, muss in der abgeleiteten GUI-View bekannt 
	  sein, welcher GUI-Controller für die navigation zuständig ist, sonst kann man keine Methoden des Controllers aufrufen.
	- die abgeleiteten Views müssen zudem die Methode show() implementieren, damit zu einem View navigiert werden kann
	- die Model Klasse muss nicht abgeleitet werden und kann auch direkt verwendet werden.
	
>	Die refreshView() Methode vom View wird automatisch aufgerufen, wenn: 
	1. der Constructor des View's meldet sich beim entsprechenden Model an mit zum Bsp.:
			getController().getModel("model-name").registerView(this)
	2. die implementierte Model-Methode doAction(...) ruft intern die eigene refreshViews() Methode
	   nach Veränderung der eignen Daten auf.

>	Bemerkung: Die Anmeldung beim eigenen lokalen Model im ViewModel erfolgt automatisch und auch das refreshView();
	Es ist zudem kein Problem, wenn das View gleichzeitig bei mehreren Model's angemeldet ist.
	
>	Bei einem DataModel ist es zusätzlich Möglich einen direkten Datenaustausch zwischen Modell und View herzustellen. 
	Typischerweise wird in der View-Methode refreshView() den neuesten Datenstatus abgefragt und im eigenen View aktualisiert.
	Dafür ist es meist notwendig gewisse Daten-Anzeigeelemente als Attribut zu halten und zu verwalten.

=========================================================================================================================
 

 
 