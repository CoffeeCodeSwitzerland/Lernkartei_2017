package lernen;

public class Seite
{
	private String Content;
	private int parentCard;

	public Seite(String content)
	{
		if (content != null && content != "")
		{
			Content = content;
		} else
		{
			Content = "";
		}
	}

	public Seite(int CardID)
	{
		if (CardID != 0)
		{
			parentCard = CardID;
		} else
		{
			parentCard = -1;
		}
	}

	public String getContent()
	{
		if (Content != "" && Content != null)
		{
			return Content;
		} else
		{
			return "";
		}
	}

	public boolean setContent(String newContent)
	{
		if (newContent != null && newContent != "")
		{
			Content = newContent;
			return true;
		} else
		{
			return false;
		}
	}
	
	public int getParentCard() {
		if (!(parentCard < 0)) {
			return parentCard;
		} else {
			return -1;
		}
	}
	
	public boolean setParentCard(int newParentCard) {
		if (newParentCard != 0) {
			parentCard = newParentCard;
			return true;
		} else {
			return false;
		}
	}
}
