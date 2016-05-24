package controls;

import models.CardModel;
import models.QuizletModel;
import mvc.fx.FXController;

/**
 * 
 * @author miro
 *
 */
public class QuizletController extends FXController
{
	@Override
	public void initMyModels() {
		this.addUniqueModel(new QuizletModel(),"quizlet");
		this.addUniqueModel(new CardModel(),"cards");
	}

	@Override
	public void initMyViews() {
		
	}

	@Override
	public void startApp() {
	}


}
