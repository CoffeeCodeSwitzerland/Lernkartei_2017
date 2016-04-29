package scrollyv8;

public class RunAudio implements Runnable {
	
	private Audio audio;
	
	public RunAudio (Audio a) {
		
		this.audio = a;
		
	}
	
	public void run () {

		audio.playAudio();
		
	}
	
}
