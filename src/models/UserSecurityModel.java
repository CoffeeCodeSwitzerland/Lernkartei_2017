package models;

import mvc.ModelInterface.Command;
import mvc.fx.FXModel;

public class UserSecurityModel extends FXModel
{
	@Override
	public int doAction (Command command, String... param)
	{
		return 1;
	}
}
