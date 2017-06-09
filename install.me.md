# ============================ #
#   Installation Hints for Eclipse Neon    #
# ============================ #
>	@AUTHOR hugo-lucca
	@DATE	08. june 2017

### ========= ###
###  To Java FX  ###
### ========= ###
- FX advantages: simpler to program than Swing and you may use CSS like functionality
- FX disadvantage: not compatible with multi-threading viewing applications
- only one additional installation is needed:
	Help -> New SW -> add Work with Neon: - http://download.eclipse.org/releases/neon
	Select General Purpose -> e(fx)clipse - IDE -> Next -> Finish
- build a new FX project with: New -> Java FX Project (adds all needed JAR's and files)
- if you want to use the FXML (you have to install the scene builder), but not recommended for this project

### =================== ###
###  To reference a new JAR  ###
### =================== ###
- copy the JAR file in a corresponding source directory
- goto Java Build Path -> Library
- select Add JARs button and then the copied file within the src tree

### ========== ###
###  To use JUnit  ###
### ========== ###
- no additional installation needed
- right-click on the class you want to test and then select New->New JUnit testCase from the context menu
- add test methods and calls like assertEquals()
- to perform integration tests or full testing procedures (for ex. TestAll()), 
  place test sub-sequences in a static method (see ex. myTest()) to be called from TestAll().
