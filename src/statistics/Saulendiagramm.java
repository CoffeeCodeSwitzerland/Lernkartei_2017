package statistics;

import java.util.ArrayList;

import database.Categories;
import database.Database;
import debug.Debugger;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;

public class Saulendiagramm
{
	HBox Dia = new HBox();
	
	CategoryAxis xAchse = new CategoryAxis();
	NumberAxis yAchse = new NumberAxis();
	
	ArrayList<String> Stacks = new ArrayList<String>();
	ArrayList<String> Punkte = new ArrayList<String>(); 
	
	BarChart<String, Number> BC = new BarChart<String, Number>(xAchse, yAchse);
	
	Series<String, Number> Serie = new Series<String, Number>();
	
	public void genSaulendiagramm() {
		getKarteien();
		getPunkte();
		setSerie();
	}
	
	public HBox getDiagramm()
	{
		BC.getData().addAll(Serie);
		Dia.getChildren().addAll(BC);
		return Dia;
	}
	
	private void setSerie() {
		for (int i = 0; i < Stacks.size(); i++)
		{
			System.out.println("1 Saulendiagram setSerie: " + Punkte.get(i));
			Double tempPunkte = Double.parseDouble(Punkte.get(i));
			System.out.println("2 Saulendiagram setSerie: " + Punkte.get(i));
			Serie.getData().addAll(new XYChart.Data<String, Number>(Stacks.get(i), tempPunkte));
		}
	}
	
	Categories C = new Categories();
	private void getKarteien() {
		Stacks = C.getStacknames();
		Debugger.out(Stacks.get(0).toString());
	}
	
	public void getPunkte()
	{
		System.out.println("Profil 1");
		for (int i = 1; i < Stacks.size(); i++)
		{
			int[] temp = Database.getScore(Stacks.get(i).toString());
			double result = temp[0] / temp[1] * 100;
			String tempStr = new Double(result).toString();
			Punkte.add(tempStr);
		}
	}
	
	public Boolean delOldStats()
	{
		Stacks = null;
		Punkte = null;
		Serie = null;
		return true;
	}
	
	

}
