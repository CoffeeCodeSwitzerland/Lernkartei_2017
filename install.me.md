====================================
Installationsanweisungen für Eclipse
====================================
>	@AUTHOR hugo-lucca
	@DATE	29. Mai 2016

Zu Java FX:
==========
- ab Java V1.6 sind alle Java-Lib's dafür dabei
- viel bessere Darstellungsmöglichkeiten, wegen CSS Unterstützung
- aber nicht geeignet für multi-threading view-applications
- ...dennoch benötigt man ein Eclipse-Plugin dazu:
	Help->New SW->Work with: Mars - http://download.eclipse.org/releases/mars/201602261000
	-> general Purpose -> e(fx)clipse - IDE ->Next->Finish
- Falls mit FXML gearbeitet werden soll, statt nur mit dem FX Toolkit und CSS-Datei (swing like + CSS!)
  dann Scene Builder installieren (das Ganze um die FXML ist jedoch nicht einfach zu beherrschen)
- Neues Projekt direkt auch mit New Java FX Project (fügt alle benötigten Jar's und Dateien gleich richtig dazu)

Zu JUnit:
========
- auch schon in Eclipse Mars und Java 1.8 integriert
- einfach rechts-klick auf zu testende Klasse und New->New JUnit testCase
- dann Testprozeduren wie assertEquals()-Methode hinzuprogrammieren
- um Integrationstests oder Gesamttests (wie zum Bsp. TestAll()) durchführen zu können, sollten Testsequenzen 
  in einer statischen Methode (siehe Bsp. myTest()) ausgelagert werden, damit man sie von TestAll() aufrufen kann.

Zu GitHub:
=========
- benötigt die Installation von 2 Plug-Ins aus:
   Eclipse EGit Mylyn EGit Repository - http://download.eclipse.org/egit/updates
   Eclipse EGit Mylyn GitHub Repository - http://download.eclipse.org/egit/github/updates
- Show View ->Git Stageing  aktivieren
- Am einfachsten: Import Project from Gihub (Github) auf ein lokales Verzeichnis (lokales Git-Verz.), 
  dann neues Projekt erstellen und in Eclips importen
- Modifikatuionen in diesem Projekt werden autom. in "unstaged changes" gelistet
- Vor dem Commit:
	- Alle Unstaged element Drag&Drop to Staged Changes
	- Enter Commit Msg
	- Actualize Author and Committer
	- Commit & Push (Commit geht nur auf Lokales Git Verzeichnis)
	- kontrolle auf GitHub

Zum GitHub Konto
================
Voraussetzungen:
- Konto erstellen
- Zugang zum Projekt bekommen
