package views;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mvc.Controller;
import mvc.View;
import mvc.fx.FXViewModel;


/**
 * Gamestartfenster
 * 
 * @author nina egger & miro albrecht
 *
 */
public class StackView extends FXViewModel
{
 public StackView(String newName, Controller newController) {
  // this constructor is the same for all view's
  super(newName, newController);
  construct();
 }

 VBox boxLayout;
 VBox options;

 @Override
 public Parent constructContainer() {
  // Layouts für dynamische Inhalte
  boxLayout = new VBox(20);
  boxLayout.setAlignment(Pos.CENTER);
  
  options = new VBox(20);
  options.setAlignment(Pos.CENTER);
  options.setMinWidth(200);
  
  VBox placeholder = new VBox();
  placeholder.setMinWidth(200);
  
  // Buttons
  AppButton backBtn = new AppButton("_Zurück");
  AppButton newBoxBtn = new AppButton("_Neue Box");

  Image trashImg = new Image("views/pictures/Papierkorb.png");
  ImageView trashImgView = new ImageView(trashImg);

  // Layout für Controls
  HBox hBox = new HBox(20);
  hBox.setAlignment(Pos.CENTER);
  hBox.getChildren().addAll(backBtn, newBoxBtn, trashImgView);

  // Layout für die Scene
  BorderPane borderPane = new BorderPane();
  borderPane.setPadding(new Insets(15));

  borderPane.setCenter(boxLayout);
  borderPane.setLeft(options);
  borderPane.setRight(placeholder);
  borderPane.setBottom(hBox);

  // Behaviour
  backBtn.setOnAction(e -> getController().getView("doorview").show());

  newBoxBtn.setOnAction(e ->
  {
   final int choice = Alert.complexChoiceBox("Neue Box", "Was für eine Box willst du erstellen?", "Leere _Box", "_Quizlet");
   
   switch (choice)
   {
    case 0:
     final String boxName = Alert.simpleString("Neue Box", "Wie soll die neue Box heissen?");
     if (this.getName() != null && !boxName.equals(""))
     {
      getController().getModel("stack").doAction("new",
        getData() + controls.Globals.SEPARATOR + boxName);
      // TODO Feedback für den User (Fehlermeldungen)
     }
     break;
    case 1:
     getController().getView("quizlet").setData(getData());
     getController().getView("quizlet").show();
     break;
    default:
     
     break;
   }
   
   
  });

  trashImgView.setOnDragOver(e ->
  {
   if (e.getGestureSource() != trashImgView && e.getDragboard().hasString())
   {
    e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
   }

   e.consume();
  });

  trashImgView.setOnDragDropped(event ->
  {
   Dragboard db = event.getDragboard();
   boolean success = false;
   if (db.hasString())
   {
    if (Alert.ok("Achtung", "Willst du die Box '" + db.getString() + "' wirklich löschen?"))
    {
     getController().getModel("stack").doAction("delete", db.getString()); 
     // TODO Feedback für den User (Fehlermeldungen)
     if (options.getChildren().get(0).getTypeSelector().equals("Label"))
     {
      Label temp = (Label) options.getChildren().get(0);
      if (temp.getText().equals(db.getString()))
       options.getChildren().clear();
     }
    }
    success = true;
   }

   event.setDropCompleted(success);
   event.consume();
  });
  getController().getModel("stack").registerView(this);
  return borderPane;
 }

 @Override
 public void refreshView ()
 {
  boxLayout.getChildren().clear();
  options.getChildren().clear();

  String localdata = getData();

  if (localdata != null)
  {
   ArrayList<String> setData = getController().getModel("stack").getDataList(localdata);
   ArrayList<AppButton> sets = new ArrayList<AppButton>();

   for (String s : setData)
   {
    AppButton a = new AppButton(s);

    sets.add(a);
   }

   for (AppButton a : sets)
   {
    a.setId("BoxButtons");
    a.setOnAction(e ->

{
     setOptions(a.getText());
    });
    a.setOnDragDetected(e ->
    {

     Dragboard db = a.startDragAndDrop(TransferMode.MOVE);

     ClipboardContent content = new ClipboardContent();
     content.putString(a.getText());
     db.setContent(content);

     e.consume();
    });
    a.setOnDragDone(event ->
    {
     if (event.getTransferMode() == TransferMode.MOVE)
     {
      sets.remove(a);
     }
     event.consume();
    });
   }

   boxLayout.getChildren().addAll(sets);
  }
 }

 private void setOptions (String set)
 {
  options.getChildren().clear();

  Label setTitle = new Label(set);
  AppButton lernen = new AppButton("_Lernen");
  AppButton edit = new AppButton("B_earbeiten");

  setTitle.setId("bold");
  lernen.setOnAction(e ->
  {
   PreLearnView v = (PreLearnView) getController().getView("prelearn");
   v.setData(set);
   v.show();
  });
  edit.setOnAction(e -> {
   View v = getController().getView("simpleeditorview");
   v.setData(set);
   v.show();
  });

  options.getChildren().addAll(setTitle, lernen, edit);
 }
}