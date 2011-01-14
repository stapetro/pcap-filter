package main;

import java.util.Scanner;

import packetProcessor.IPacketReader;
import packetProcessor.IPacketWriter;
import packetProcessor.NetworkInterfacePacketReader;

public class PCapServer {

	private IPacketReader reader;
	private IPacketWriter writer;

	public PCapServer(IPacketReader readerInstance, IPacketWriter writerInstance) {
		this.reader = readerInstance;
		this.writer = writerInstance;
	}

	public void start() {
		System.out.println("Start listening for packets...");
		reader.startReadingPackets();
		if (reader instanceof NetworkInterfacePacketReader) {
			System.out.print("Enter sth to stop reading: ");
			Scanner input = new Scanner(System.in);
			String stopCommand = input.nextLine();
			// TODO processing stop command
			System.out
					.println("Test ================ just before stopping packet reading");
			reader.stopReadingPackets();
		}
	}
}
