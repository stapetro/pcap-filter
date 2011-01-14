package packetProcessor;

import java.io.PrintStream;

import jpcap.packet.Packet;

public class SystemWriter implements IPacketWriter {

	private PrintStream printStream;

	public SystemWriter(PrintStream output) {
		printStream = output;
	}

	@Override
	public void close() {
		// Intentionally don't take any action. We shall not close the
		// System.out stream
	}

	@Override
	public void writePacket(Packet packet) {
		printStream.println(packet.toString());
	}

}
