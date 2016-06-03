package mvc.fx;

import globals.SingletonInterface;
import javafx.stage.Stage;
/**
 * 
 * @author © 2013 AxxG – Alexander Gräsel
 *
 * Comment:
 * ======= 
 * @author http://stackoverflow.com/questions/519520/difference-between-static-class-and-singleton-pattern
 * ... static classes should not do anything need state, it is useful for putting bunch 
 * of functions together i.e Math (or Utils in projects). 
 * So the class name just give us a clue where we can find the functions and there's nothing more.
 * Singleton is my favorite pattern and use it to manage something at a single point. 
 * It's more flexible than static classes and can maintain state. 
 * It can implement interfaces, inherit from other classes and allow inheritance and polymorphism.
 * 
 * My rule for choosing between static and singleton:
 * -------------------------------------------------
 * If there are bunch of functions should be kept together, then static is the choice. 
 * Anything else which needs single access to some resources, could be implemented singleton.
 * 
 */
public class FXStageManager implements SingletonInterface {
    
    private static SingletonInterface instance;
    private static Stage primaryStage;
  
    /** Konstruktor ist privat, Klasse darf nicht von außen instanziiert werden. */
    private FXStageManager() {
    }
  
    /**
     * Statische Methode 'getInstance()Ä liefert die einzige Instanz der Klasse zurück.
     * Ist synchronisiert und somit thread-sicher.
     */
    public synchronized SingletonInterface getInstance() 
    {
        if (instance == null) 
        {
            instance = new FXStageManager();
        }
        return instance;
    }
 
   public Stage getPrimaryStage() {
      return primaryStage;
   }
 
   public void setPrimaryStage(Stage primaryStage) {
      FXStageManager.primaryStage = primaryStage;
   }  
}