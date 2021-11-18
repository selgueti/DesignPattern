package fr.uge.poo.cmdline.ex3;
public record WindowSize(int width, int height) {
	
	public WindowSize union (WindowSize ws){
		return new WindowSize(Math.max(this.width, ws.width()), Math.max(this.width, ws.width()));
	}

}
