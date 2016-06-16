package models;

import debug.Logger;
import multiuser.CannotPerformOperationException;
import multiuser.User;
import mvc.fx.FXModel;

public class UserSecurityModel extends FXModel
{
	@Override
	public int doAction(Command command, String... param)
	{
		switch (command) {
		case NEW:
			if (param.length != 4)
			{
				return -2;
			}
			User newUser = new User();
			return newUser.registration(param[0], param[1], param[2], param[3]);
		case DELETE:
			if (param.length != 2)
			{
				return -2;
			}
			User u = new User();
			boolean success = u.delete(param[0], param[1]);
			return success ? 1 : -1;
		case TRUE:
			if (param.length != 2)
			{
				return -2;
			}
			User b = new User();
			try
			{
				boolean successb = b.login(param[0], param[1]);
				return successb ? 1 : -1;
			} catch (CannotPerformOperationException e)
			{
				Logger.log(e.getMessage());
			}
		default:
			int superIsSuccessful = super.doAction(command, param);
			return superIsSuccessful;
		}
	}
}
