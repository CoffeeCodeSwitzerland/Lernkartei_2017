# ================================= #		
# Build Informationen for FX Applikacions #		
# ================================= #		
>	@AUTHOR  hugo-lucca	
	@DATE	 1. June 17 
	@VERSION for Neon 3

1. Edit Globals.java (in package globals) and set DebuggerIsOn and TestingIsOn to false		
* ... and assert DB settings ForceNewDB and ForDBVersionLT are also ok.		
* Assert, RUN->external tools->external configurations->JRE configs are ok, with the following steps:		
  * in Eclipse click Run -> External Tools -> External Tools Configuration
  * deactivate any filters in navigation	and select your ant project	
  * select the JRE tab
  * click on "Installed JREs" button.		
  * click on Add (!!!) button. (Select Standard VM, where applicable.)		
  * click on Directory button.		
  * browse to your JDK version (not JRE) of your installed Java (e.g. C:\Program Files\Java\jdk1.7.0_04).		
  * Click Finish and OK.		
  * Select the JDK at Separate JRE and click Close.		
  * Re-run your Ant script — have fun! 
  * See also: _stackoverflow.com/questions/1558780/why-does-ant-tell-me-that-java-home-is-wrong-when-it-is-not_
* Assert, the OS environment variable JAVA_HOME references the JDK \bin directory containing javac.exe. If not do install JDK (before doing this step).		
* copy (do not move) all .css files from **views.styles** to the **views** folder		
* open file **build.fxbuild**		
* fill out the fields with a '*' and click on link "ant build and run" (Build directory should be: ${project}/build)  		
* select the JAR file in build\**dist** folder		
* Context menu->Export->**JAR executabe** to a target folder		
* delete all .css in **views** (undo step 5)		
