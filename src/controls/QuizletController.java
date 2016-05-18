package controls;

import javafx.stage.Stage;
import models.CardModel;
import models.QuizletModel;
import mvc.Controller;
import mvc.fx.FXSettings;

/**
 * 
 * @author miro
 *
 */
public class QuizletController extends Controller
{
	public QuizletController ()
	{
		super(new Stage(), new FXSettings());
	}

	@Override
	public void initMyModels() {
		this.addUniqueModel(new QuizletModel("quizlet"));
		this.addUniqueModel(new CardModel("cards"));
	}

	@Override
	public void initMyViews() {
		
	}


}
