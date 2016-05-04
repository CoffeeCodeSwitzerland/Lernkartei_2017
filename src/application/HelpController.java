package application;

import gui.*;
import mvc.Controller;
/**
 * Diese Klasse Kontrolliert alle Sichten und Models. Den Sichten wird die
 * Navigation zur Verfügung gestellt. Alle Sichten (ausser Modalfenster) werden
 * hier mit eindeutigen Namen versehen.
 * 
 * @author miro albrecht & hugo-lucca
 *
 */
public class HelpController extends Controller
{
	@Override
	public void initMyModels() {
		// no Models for Help
	}

	@Override
	public void initMyViews() {
		this.addUniqueView(new HelpSerachView("helpsearch", this));
		this.addUniqueView(new HelpInfoView("helpinfo", this));
		this.addUniqueView(new ImpressumView("impressumview", this));
	}
}
