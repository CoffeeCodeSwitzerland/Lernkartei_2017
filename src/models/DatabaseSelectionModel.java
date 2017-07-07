 package models;

import java.util.ArrayList;

import database.LKDatabase;
import debug.Supervisor;
import mvc.Model;
import mvc.ModelInterface.Command;
import serverdb.databasemgmt.ServerTable;


public class DatabaseSelectionModel extends Model
{
	static ArrayList<String> data = new ArrayList<String>();
	
	@Override
	public ArrayList<String> getDataList (String action)
	{
		return data;
	}
	
	@Override
	public void setDataList (ArrayList<String> dataList)
	{
		data = dataList;
	}
	
	@Override
	public void addDataList (ArrayList<String> dataList)
	{
		for (String s: dataList)
		{
			data.add(s);
		}
	}
	
	@Override
	public void addData (String value)
	{
		data.add(value);
	}
	
	public void buildWissDatabase()
	{
		ArrayList<ArrayList<String[]>> tables = new ArrayList<ArrayList<String[]>>();
		
		ArrayList<String[]> dbuser = new ArrayList<String[]>();
		String[] dbusertable = {"dbuser"}; dbuser.add(dbusertable);
		String[] dbuserUserID = {"UserID BIGINT AUTO_INCREMENT"}; dbuser.add(dbuserUserID);
		String[] dbuserFirstName = {"FirstName TEXT NOT NULL"};dbuser.add(dbuserFirstName);
		String[] dbuserLastName = {"LastName TEXT NOT NULL"};dbuser.add(dbuserLastName);
		String[] dbuserMail = {"Mail TEXT NOT NULL"};dbuser.add(dbuserMail);
		String[] dbuserPassword = {"Password TEXT  NOT NULL"};dbuser.add(dbuserPassword);
		String[] dbuserPRIMARY = {"PRIMARY KEY(UserID)"};dbuser.add(dbuserPRIMARY);
		
		
		
		ArrayList<String[]> dbgroup = new ArrayList<String[]>();
		String[] dbgrouptable = {"dbgroup"}; dbgroup.add(dbgrouptable);
		String[] dbgroupdbgroup = {"GroupID BIGINT AUTO_INCREMENT"}; dbgroup.add(dbgroupdbgroup);
		String[] dbgroupName = {"Name TEXT NOT NULL"};dbgroup.add(dbgroupName);
		String[] dbgroupErstellerID = {"ErstellerID BIGINT NOT NULL"};dbgroup.add(dbgroupErstellerID);
		String[] dbgroupPRIMARY = {"PRIMARY KEY(GroupID"};dbgroup.add(dbgroupPRIMARY);
		String[] dbgroupFOREIGN = {"PRIMARY KEY(GroupID"};dbgroup.add(dbgroupPRIMARY);

		ArrayList<String[]> dbuseringroup = new ArrayList<String[]>();
		String[] dbuseringrouptable = {"dbuseringroup"}; dbuseringroup.add(dbuseringrouptable);
		String[] dbuseringroupUserInGroupID = {"UserInGroupID BIGINT AUTO_INCREMENT"}; dbuseringroup.add(dbuseringroupUserInGroupID);
		String[] dbuseringroupUserID = {"UserID BIGINT NOT NULL"};dbuseringroup.add(dbuseringroupUserID);
		String[] dbuseringroupGroupID = {"GroupID BIGINT NOT NULL"};dbuseringroup.add(dbuseringroupGroupID);
		String[] dbuseringroupPRIMARY  = {"GroupID BIGINT NOT NULL"};dbuseringroup.add(dbuseringroupGroupID);
		String[] dbuseringroupFOREIGN = {"CONSTRAINT fk_dbuserTOdbuseringroup FOREIGN KEY(UserID) REFERENCES User(UserID) ON DELETE CASCADE ON UPDATE CASCADE"};dbuseringroup.add(dbuseringroupFOREIGN);
		String[] dbuseringroupFOREIGN2 = {"CONSTRAINT fk_dbuserTOdbuseringroup FOREIGN KEY(GroupID) REFERENCES Group(GroupID) ON DELETE SET NULL ON UPDATE CASCADE"};dbuseringroup.add(dbuseringroupFOREIGN2);
		
		ArrayList<String[]> datamanagementtouser = new ArrayList<String[]>();
		String[] datamanagementtousertable = {"datamanagementtouser"}; datamanagementtouser.add(datamanagementtousertable);
		String[] datamanagementtouserUserID = {"UserID BIGINT AUTO_INCREMENT"}; datamanagementtouser.add(datamanagementtouserUserID);
		String[] datamanagementtouserDeriveGroupID = {"DeriveGroupID BIGINT NULL"};datamanagementtouser.add(datamanagementtouserDeriveGroupID);
		String[] datamanagementtouserTeamworkGroupID= {"TeamworkGroupID BIGINT NULL"};datamanagementtouser.add(datamanagementtouserTeamworkGroupID);
		String[] datamanagementtouserDatamanagementToUserID = {"DatamanagementID BIGINT NOT NULL"};datamanagementtouser.add(datamanagementtouserDatamanagementToUserID);
		String[] datamanagementtouserPRIMARY = {"PRIMARY KEY (UserID)"};datamanagementtouser.add(datamanagementtouserPRIMARY);
		String[] datamanagementtouserFOREIGN1 = {"COSTRAINT fk_datamgmtTOuser FOREIGN KEY(UserID) REFERENCES User(UserID) ON DELETE SET NULL ON UPDATE CASCADE"};datamanagementtouser.add(datamanagementtouserFOREIGN1);
		String[] datamanagementtouserFOREIGN2 = {"COSTRAINT fk_datamgmtTOgroup1 FOREIGN KEY(DeriveGroupID) REFERENCES Group(GroupID) ON DELETE SET NULL ON UPDATE CASCADE"};datamanagementtouser.add(datamanagementtouserFOREIGN2);
		String[] datamanagementtouserFOREIGN3 = {"COSTRAINT fk_datamgmtTOgroup2 FOREIGN KEY(TeamworkGroupID) REFERENCES Group(GroupID) ON DELETE SET NULL ON UPDATE CASCADE"};datamanagementtouser.add(datamanagementtouserFOREIGN3);
		String[] datamanagementtouserFOREIGN4 = {"COSTRAINT fk_datamgmtTOdatamgmt FOREIGN KEY(DatamanagementID) REFERENCES Group(GroupID) ON DELETE SET NULL ON UPDATE CASCADE"};datamanagementtouser.add(datamanagementtouserFOREIGN3);
		
		ArrayList<String[]> datamanagement = new ArrayList<String[]>();
		String[] datamanagementtable = {"datamanagement"}; datamanagement.add(datamanagementtable);
		String[] datamanagementDatamanagementID = {"DatamanagementID BIGINT AUTO_INCREMENT"}; datamanagement.add(datamanagementDatamanagementID);
		String[] datamanagementCardTableReferenceName = {"CardTableReferenceName TEXT NOT NULL"};datamanagement.add(datamanagementCardTableReferenceName);
		String[] datamanagementDoorTableReferenceName = {"DoorTableReferenceName TEXT NOT NULL"};datamanagement.add(datamanagementDoorTableReferenceName);
		String[] datamanagementStackTableReferenceName = {"StackTableReferenceName TEXT NOT NULL"};datamanagement.add(datamanagementStackTableReferenceName);
		
		ArrayList<String[]> dbtrash = new ArrayList<String[]>();
		String[] dbtrashtable = {""}; dbtrash.add(dbtrashtable);
		String[] dbtrashID= {"TrashID BIGINT AUTO_INCREMENT"}; dbtrash.add(dbtrashID);
		String[] dbtrashDatamanagementID = {"DatamanagementID BIGINT NOT NULL"}; dbtrash.add(dbtrashDatamanagementID);
		String[] dbtrashStackName = {"StackName TEXT NULL"};dbtrash.add(dbtrashStackName);
		String[] dbtrashKEY_NAME = {"KEY_NAME TEXT NULL"};dbtrash.add(dbtrashKEY_NAME);
		String[] dbtrashKEY_VALUE = {"KEY_VALUE TEXT NULL"}; dbtrash.add(dbtrashKEY_VALUE);
		String[] dbtrashPK_Card = {"PK_Card BIGINT NOT NULL"}; dbtrash.add(dbtrashPK_Card);
		String[] dbtrashFrontside = {"Frontside TEXT NULL"};dbtrash.add(dbtrashFrontside);
		String[] dbtrashBackside = {"Backside TEXT NULL"};dbtrash.add(dbtrashBackside);
		String[] dbtrashPriority = {"Priority BIGINT NULL"}; dbtrash.add(dbtrashPriority);
		String[] dbtrashColor = {"Color TEXT NULL"}; dbtrash.add(dbtrashColor);
		String[] dbtrashLink = {"Link TEXT NULL"};dbtrash.add(dbtrashLink);
		String[] dbtrashDescription = {"Description TEXT NULL"};dbtrash.add(dbtrashDescription);
		String[] dbtrashDate = {"Date TEXT NULL"};dbtrash.add(dbtrashDate);
				
		tables.add(dbuser);
		tables.add(dbgroup);
		tables.add(dbuseringroup);
		tables.add(datamanagement);
		tables.add(dbtrash);
		
		ServerTable st = new ServerTable(tables);
		st.createTables();
	}
	
	
}
