package views;

import java.io.File;

import globals.Functions;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import mvc.fx.FXController;
import mvc.fx.FXView;
import views.components.AppButton;
import views.components.ControlLayout;


/**
 * Diese Klasse hilft dem User das Prinzip von BB-Codes zu verstehen und
 * anzuwenden.
 * 
 * @author miro albrecht
 *
 */
public class BBCodeInfoView extends FXView
{
	private String filePath = "src\\views\\txt\\BBCodeInfo.txt";

	public BBCodeInfoView (String newName, FXController newController)
	{
		super(newController);
		construct(newName);
	}

	@Override
	public Parent constructContainer ()
	{
		Label labelText;

		try
		{
			labelText = new Label(Functions.fileToString(new File(filePath)));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			labelText = new Label("leer"); // TODO saubere verarbeitung der
											 // exception
		}

		labelText.setWrapText(true);
		labelText.setMaxWidth(800);
		labelText.setId("impressumtext");

		Label labelTitel = new Label("BBCode");
		labelTitel.setId("impressumtitel");

		AppButton backBtn = new AppButton("_Zurück");
		backBtn.setOnAction(e ->
		{
			getFXController().showLastView();
			EditorView.front.setText(EditorView.frontinfo);
			EditorView.back.setText(EditorView.backinfo);
			EditorView.engineback.loadContent(EditorView.backinfo);
			EditorView.enginefront.loadContent(EditorView.frontinfo);
			EditorView.UpdatePreview();
		});

		BorderPane headLayout = new BorderPane(labelTitel);
		headLayout.setPadding(new Insets(20));
		ScrollPane scroller = new ScrollPane();
		scroller.setMaxWidth(800);

		scroller.setContent(labelText);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		ControlLayout conLay = new ControlLayout(backBtn);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setPadding(new Insets(15));
		mainLayout.setTop(headLayout);
		mainLayout.setCenter(scroller);
		mainLayout.setBottom(conLay);

		return mainLayout;
	}

	@Override
	public void refreshView ()
	{
		// No refresh needed
	}
}