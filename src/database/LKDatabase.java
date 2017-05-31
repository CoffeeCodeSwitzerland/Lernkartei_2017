package database;

import database.jdbc.DBDriver;
import database.jdbc.SQLiteDriver;
import database.sql.Entity;

public abstract class LKDatabase {

	public static final DBDriver myConfigDB = new SQLiteDriver("config.db"); 
	public static final DBDriver myWLCDB = new SQLiteDriver(globals.Globals.db_name + ".db"); 
	
	public static final Entity myConfig = new Entity(myConfigDB,"config","PK_config",true);
	public static final UserEntity myUsers = new UserEntity(myWLCDB,"User");
	public static final UserLogin myLogins = new UserLogin(myWLCDB,"Login");
	public static final DoorEntity myDoors = new DoorEntity(myWLCDB,"Door");
	public static final CardEntity myCards = new CardEntity(myWLCDB,"Card");
	public static final SideEntity mySides = new SideEntity(myWLCDB,"Side");
	public static final StackEntity myStacks = new StackEntity(myWLCDB,"Stack");
	public static final LearnEntity myLearns = new LearnEntity(myWLCDB,"Learn");
}
