# ===================================================== #
#   Installationsanweisungen und Nutzung von GitHub unter Eclipse   #
# ===================================================== #
>	@AUTHOR hugo-lucca
	@DATE	16. June 2016

### ================================= ###
### GitHub für Eclipse installieren   ###
### ================================= ###
- man benötigt die Installation von 2 Plug-Ins aus:
   Eclipse EGit Mylyn EGit Repository - http://download.eclipse.org/egit/updates
   Eclipse EGit Mylyn GitHub Repository - http://download.eclipse.org/egit/github/updates
- Window->Show View ->Git Stageing  aktivieren
- Am einfachsten: Import Project from Gihub (Github) auf ein lokales Verzeichnis (lokales Git-Verz.), 
  dann neues Projekt erstellen und in Eclips importen

### ======================= ###
###   Mit GitHub arbeiten   ###
### ======================= ###
- Modifikationen in diesem Projekt werden autom. in "unstaged/staged changes" gelistet
- Vor dem Commit:
	- Alle Unstaged element Drag&Drop to Staged Changes
	- Enter Commit Msg
	- Actualize Author and Committer (falls nich autom.)
	- Team->Pull
	- Commit & Push (Commit geht nur auf Lokales Git Verzeichnis)
	- evtl. Kontrolle auf GitHub
- Bei Konflikten (im Titel der Git Staging Anzeige in [master] sind Pfeile):
	- Pfeil nach oben, zeigt Problem beim Commit (ging nur bis local repository)
	- Pfeil nach unten zeigt, dass Dateien nicht heruntergelden werden konnten wegen Konflikte
	- Folgender Lösungsweg ist der richtige, damit niemand was verliert:
	  1. versuche Team->Pull und dann Team->Push to Upstream
	  2. wenn eines oder beides klemmt, dann:
	     a. alle Dateien im "unstaged Changes" müssen manuell sync. werden, d.h.
	         - im Kontext mit "Replace with HEAD Rev." verliere ich meine Version (nur im Notfall)
	         - im Kontext mit "Compare with Index" (oder Doppelklick) führt zum Vegleich der beiden Dateien
	         - einzeln jeden Unterschied manuell nach rechts oder links kopieren bis beide Dateien gleich
	         - immer alles speichern und zwar auch oft zwischendurch, 
	           da der Merge-Editor gerne abstürzt und man muss neu anfangen
	     f. wiederhole a. bis unstaged Changes leer, dann Pull oder fetch from Upstream, 
	     g. dann Pusch to Upstream und schliesslich evtl. noch  ein Commit, wenn Dateien im Staged changes
	- am Schluss darf nur noch [master] ohne Pfeile im Titel stehen und keine Datein im GitStage
	     
### ================= ###
### Zum GitHub Konto  ###
### ================= ###
Schritte:
- Konto erstellen
- Zugang zum Projekt bekommen

