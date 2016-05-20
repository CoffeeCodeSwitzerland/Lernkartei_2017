# Quizlet Package

## Funktionen

- Öffentliche Sets auf Quizlet durchsuchen
- Öffentliche Sets von Quizlet herunterladen

## Anwendung

Quizletobjekt erstellen.
Anstelle von **token** muss ein Quizlet Developer Key stehen -> siehe QuizletTest-Klasse.

	Quizlet q = new Quizlet(**token**);


Suchfunktion erfordert Suchstring und gibt eine ArrayList<String> zurük:

	ArrayList<String> result = q.searchSet(**search**);


Infos zur Suche (Element 0)

- total_results
- total_pages
- image_set_count
- page


Gefundene Sets (Element 1 - n):

- id
- title
- created_by
- term_count
- has_images
- description
- lang_terms
- lang_definitions


Einzelne Informationen wie Id und Title werden durch den Separator (default ':::') getrennt.


GetSet verlangt eine gültige (!) Quizlet-Set-Id als Parameter und gibt eine ArrayList<String> zurück:

	ArrayList<String> terms = q.getSet(<setId>);


Infos zum Set (Element 0)

- id
- title
- created_by
- term_count
- has_images
- description
- lang_terms
- lang_definitions


Karten im Set (Element 1 - n)

- id
- term
- definition


Gleiche Form wie bei searchSet(), gleicher Separator


Bei Fragen wendet euch an miro
