# =================== #
#	  Debug Package   #
# =================== #
>	@AUTHOR hugo-lucca
	@DATE	29. Mai 2016

##	Content
##	-------
>	- for debugger informations to the developer 
>	- for logger information from the used product (as logfile)
>	- environment informations (about OS, user, file and directory system etc.)
	
##	Dependencies
##	------------
>	- globals.globals.java	to activate or deactivate
>	- debugger need a console (should be deactivated for the runnable app)
>	- looger need's the globals.environment.java 

## Classes
## -------
> Debugger.java		Puts messages to the console (may be deactivated)
> Logger.java		Puts log's to a file (in APPDATA/Roaming/... or project directory)
					The log-file viewer is part of this project.
> Supervisor.java	The supervisor is able to react differently on errors and warnings
					(you may change the behave @ runtime)
> MyFile.java		File Handler for the Logger and other files 
					(TDOD: update to new File-Path Java 8 concept)

### ======================================================================================================= ###
