package fr.uge.poo.cmdline.ex4;

import java.net.InetSocketAddress;

public class PaintSettings {

	private final boolean legacy;
	private final boolean bordered;
	private final int borderWidth;
	private final String windowName;
	private final WindowSize minWindowSize;
	private final InetSocketAddress remoteServer;

	private PaintSettings(PaintSettingsBuilder builder) {
		this.legacy = builder.legacy;
		this.bordered = builder.bordered;
		this.borderWidth = builder.borderWidth;
		this.windowName = builder.windowName;
		this.minWindowSize = builder.minWindowSize;
		this.remoteServer = builder.remoteServer;
	}

	public static class PaintSettingsBuilder {
		private boolean legacy = false;
		private boolean bordered = true;
		private int borderWidth = 10;
		private String windowName = "";
		private WindowSize minWindowSize = new WindowSize(500, 500);
		private InetSocketAddress remoteServer;

		public PaintSettingsBuilder setLegacy(boolean legacy) {
			this.legacy = legacy;
			return this;
		}

		public PaintSettingsBuilder setBordered(boolean bool) {
			this.bordered = bool;
			return this;
		}

		public PaintSettingsBuilder setBorderWidth(int borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}

		public PaintSettingsBuilder setWindowName(String windowName) {
			this.windowName = windowName;
			return this;
		}

		public PaintSettingsBuilder setMinSize(WindowSize minWindowSize) {
			this.minWindowSize = this.minWindowSize.union(minWindowSize);
			return this;
		}

//		public PaintSettingsBuilder setRemoteServer(InetSocketAddress remoteServer) {
//			this.remoteServer = remoteServer;
//			return this;
//		}

		public PaintSettingsBuilder setRemoteServer(String name, int port) {
			this.remoteServer = new InetSocketAddress(name, port);
			return this;
		}

		public PaintSettings build() {
			if (windowName == "") { // TODO who manages the name obligation for the window?
				throw new IllegalArgumentException("window name is required");
			}
			return new PaintSettings(this);
		}
	}

	@Override
	public String toString() {
		return "PaintSettings [ bordered = " + bordered + ", legacy = " + legacy + ", border-width = " + borderWidth
				+ ", min-size =  " + minWindowSize + ", window-name = " + windowName + ", remote-server = "
				+ remoteServer + " ]";
	}
}
