package views;

import java.io.File;

import globals.Functions;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.ControlLayout;
import views.components.MainLayout;
import views.components.VerticalScroller;

/**
 * Hilfe System Index-Suche
 *  
 * @author hugo-lucca
 *
 */
public class HelpSerachView extends FXView
{

	public HelpSerachView(String newName, FXController newController) {
		// this constructor is the same for all view's
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer() {
		// TODO Auto-generated method stub

		Label labelText;
		try {
			labelText = new Label (Functions.fileToString(new File(
					"src\\views\\txt\\index.txt")) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			labelText = new Label("leer");
		}
		
		
		labelText.setWrapText(true);
		labelText.setMaxWidth(800);
		labelText.setId("impressumtext");

		Label labelTitel = new Label("Index");
		labelTitel.setId("impressumtitel");

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e -> getFXController().showMainView());

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		
		VerticalScroller scroLay = new VerticalScroller(labelText, 25);
		scroLay.setMaxWidth(800);

		ControlLayout conLay = new ControlLayout(backBtn);

		MainLayout maLay = new MainLayout(scroLay, headLayout, conLay);

		return maLay;
	}

	@Override
	public void refreshView() {
		// TODO Auto-generated method stub
		
	}
}
