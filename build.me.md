# ======================================== #
# Build Informationen für FX Applikationen #
# ======================================== #

1. Edit Globals.java and set DebuggerIsOn and TestingIsOn to false
2. Edit Globals.java and assert DB settings ForceNewDB and ForDBVersionLT are ok.
3. copy (no move) alle .css from **views.styles** to **views** folder
4. open file **build.fxbuild**
5. fill out the fields with a '*' and click on link "ant build and run"  
6. select the JAR file ind **dist** folder
7. CM->Export->**JAR executabe** to a target folder
8. delete all .css in **views** (undo step 3)