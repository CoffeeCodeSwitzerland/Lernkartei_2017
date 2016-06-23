package database;

public abstract class LKDatabase {
	
	public static final Config myConfig = new Config("config.db","config");
	public static final UserEntity myUsers = new UserEntity("User");
	public static final UserLogin myLogins = new UserLogin("Login");
	public static final DoorEntity myDoors = new DoorEntity("Door");
	public static final CardEntity myCards = new CardEntity("Card");
	public static final SideEntity mySides = new SideEntity("Side");
	public static final StackEntity myStacks = new StackEntity("Stack");
	public static final LearnEntity myLearns = new LearnEntity("Learn");
	
}
