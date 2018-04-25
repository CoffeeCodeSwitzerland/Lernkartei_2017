 package models;

import java.util.ArrayList;

import mvc.Model;
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
		String[] dbgroupPRIMARY = {"PRIMARY KEY(GroupID)"};dbgroup.add(dbgroupPRIMARY);

		ArrayList<String[]> dbuseringroup = new ArrayList<String[]>();
		String[] dbuseringrouptable = {"dbuseringroup"}; dbuseringroup.add(dbuseringrouptable);
		String[] dbuseringroupUserInGroupID = {"UserInGroupID BIGINT AUTO_INCREMENT"}; dbuseringroup.add(dbuseringroupUserInGroupID);
		String[] dbuseringroupUserID = {"UserID BIGINT  NULL"};dbuseringroup.add(dbuseringroupUserID);
		String[] dbuseringroupGroupID = {"GroupID BIGINT  NULL"};dbuseringroup.add(dbuseringroupGroupID);
		String[] dbuseringroupPRIMARY  = {"PRIMARY KEY (UserInGroupID)"};dbuseringroup.add(dbuseringroupPRIMARY);
		String[] dbuseringroupFOREIGN = {"CONSTRAINT fk_dbuserTOdbuseringroup FOREIGN KEY(UserID) REFERENCES dbuser(UserID) ON DELETE CASCADE ON UPDATE CASCADE"};dbuseringroup.add(dbuseringroupFOREIGN);
		String[] dbuseringroupFOREIGN2 = {"CONSTRAINT fk_dbuserTOdbuseringroupto FOREIGN KEY(GroupID) REFERENCES dbgroup(GroupID) ON UPDATE CASCADE"};dbuseringroup.add(dbuseringroupFOREIGN2);
		
		ArrayList<String[]> dbdatamanagement = new ArrayList<String[]>();
		String[] dbdatamanagementtable = {"dbdatamanagement"}; dbdatamanagement.add(dbdatamanagementtable);
		String[] dbdatamanagementDatamanagementID = {"DatamanagementID BIGINT AUTO_INCREMENT"}; dbdatamanagement.add(dbdatamanagementDatamanagementID);
		String[] dbdatamanagementCardTableReferenceName = {"CardTableReferenceName TEXT NOT NULL"};dbdatamanagement.add(dbdatamanagementCardTableReferenceName);
		String[] dbdatamanagementDoorTableReferenceName = {"DoorTableReferenceName TEXT NOT NULL"};dbdatamanagement.add(dbdatamanagementDoorTableReferenceName);
		String[] dbdatamanagementStackTableReferenceName = {"StackTableReferenceName TEXT NOT NULL"};dbdatamanagement.add(dbdatamanagementStackTableReferenceName);
		String[] dbdatamanagementPRIMARY = {"PRIMARY KEY (DatamanagementID)"};dbdatamanagement.add(dbdatamanagementPRIMARY);
		
		ArrayList<String[]> dbdatamanagementtouser = new ArrayList<String[]>();
		String[] dbdatamanagementtousertable = {"datamanagementtouser"}; dbdatamanagementtouser.add(dbdatamanagementtousertable);
		String[] dbdatamanagementtouserUserID = {"UserID BIGINT AUTO_INCREMENT"}; dbdatamanagementtouser.add(dbdatamanagementtouserUserID);
		String[] dbdatamanagementtouserDeriveGroupID = {"DeriveGroupID BIGINT NULL"};dbdatamanagementtouser.add(dbdatamanagementtouserDeriveGroupID);
		String[] dbdatamanagementtouserTeamworkGroupID= {"TeamworkGroupID BIGINT NULL"};dbdatamanagementtouser.add(dbdatamanagementtouserTeamworkGroupID);
		String[] dbdatamanagementtouserDatamanagementToUserID = {"DatamanagementID BIGINT "};dbdatamanagementtouser.add(dbdatamanagementtouserDatamanagementToUserID);
		String[] dbdatamanagementtouserPRIMARY = {"PRIMARY KEY (UserID)"};dbdatamanagementtouser.add(dbdatamanagementtouserPRIMARY);
		String[] dbdatamanagementtouserFOREIGN1 = {"CONSTRAINT fk_datamgmtTOuser FOREIGN KEY(UserID) REFERENCES dbuser(UserID) ON DELETE CASCADE ON UPDATE CASCADE"};dbdatamanagementtouser.add(dbdatamanagementtouserFOREIGN1);
		String[] dbdatamanagementtouserFOREIGN2 = {"CONSTRAINT fk_datamgmtTOgroup1 FOREIGN KEY(DeriveGroupID) REFERENCES dbgroup(GroupID) ON DELETE SET NULL ON UPDATE CASCADE"};dbdatamanagementtouser.add(dbdatamanagementtouserFOREIGN2);
		String[] dbdatamanagementtouserFOREIGN3 = {"CONSTRAINT fk_datamgmtTOgroup2 FOREIGN KEY(TeamworkGroupID) REFERENCES dbgroup(GroupID) ON DELETE SET NULL ON UPDATE CASCADE"};dbdatamanagementtouser.add(dbdatamanagementtouserFOREIGN3);
		String[] dbdatamanagementtouserFOREIGN4 = {"CONSTRAINT fk_datamgmtTOdatamgmt FOREIGN KEY(DatamanagementID) REFERENCES dbdatamanagement(DatamanagementID) ON DELETE CASCADE ON UPDATE CASCADE"};dbdatamanagementtouser.add(dbdatamanagementtouserFOREIGN4);
		
		ArrayList<String[]> dbtrash = new ArrayList<String[]>();
		String[] dbtrashtable = {"dbtrash"}; dbtrash.add(dbtrashtable);
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
		String[] dbtrashPRIMARY = {"PRIMARY KEY (TrashID)"};dbtrash.add(dbtrashPRIMARY);
		String[] dbtrashFOREIGN = {"FOREIGN KEY(DatamanagementID) REFERENCES dbdatamanagement(DatamanagementID) ON DELETE CASCADE ON UPDATE CASCADE"};dbtrash.add(dbtrashFOREIGN);		
		
		//CONSTRAINT fk_trashTOdatamgmt 
		tables.add(dbuser);
		tables.add(dbgroup);
		tables.add(dbuseringroup);
		tables.add(dbdatamanagement);
		tables.add(dbdatamanagementtouser);
		tables.add(dbtrash);
		
		ServerTable st = new ServerTable(tables);
		st.createTables();

	}
	
	
}
