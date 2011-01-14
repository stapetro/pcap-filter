package main;

import java.util.Scanner;

import packetProcessor.IPacketReader;
import packetProcessor.IPacketWriter;

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
		System.out.print("Enter sth to stop reading: ");
		Scanner input = new Scanner(System.in);
		String stopCommand = input.nextLine();
		//TODO processing stop command
		reader.stopReadingPackets();
		
	}
}
