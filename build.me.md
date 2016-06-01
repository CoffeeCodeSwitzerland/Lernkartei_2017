# ======================================== #
# Build Informationen für FX Applikationen #
# ======================================== #

>	@AUTHOR hugo-lucca
	@DATE	29. Mai 2016

1. Edit Globals.java and set DebuggerIsOn and TestingIsOn to false
2. Edit Globals.java and assert DB settings ForceNewDB and ForDBVersionLT are ok.
3. Stelle sicher, dass die RUN->external tools->external configurations->JRE Einstellung stimmt
   (siehe auch http://stackoverflow.com/questions/1558780/why-does-ant-tell-me-that-java-home-is-wrong-when-it-is-not )
4. Stelle sicher, dass die Umgebungsvariable JAVA_HOME auf ein JDK zeigt,
   bei dem im \bin Verzeichnis der javac.exe (Java Compiler) sich befindet. Ansonsten JDK mit compiler installieren.
5. copy (no move) alle .css from **views.styles** to **views** folder
6. open file **build.fxbuild**
7. fill out the fields with a '*' and click on link "ant build and run"  
8. select the JAR file ind **dist** folder
9. CM->Export->**JAR executabe** to a target folder
10. delete all .css in **views** (undo step 5)
