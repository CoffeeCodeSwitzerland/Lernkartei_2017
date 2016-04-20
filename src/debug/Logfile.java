package debug;

import java.time.LocalDate;
import java.time.LocalTime;

public class Logfile extends MyFile {
	private static final long serialVersionUID = 1L;

	private boolean saveLogActive = true;

	public Logfile (String fn) {
		super (fn);
	}

	public void save (String logText) {
		if (saveLogActive) {
			super.save( LocalDate.now() + "-" + LocalTime.now().toNanoOfDay() +": " + logText);
		}
	}

}
