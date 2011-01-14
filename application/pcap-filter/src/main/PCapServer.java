package main;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import jpcap.packet.Packet;
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
		// TODO processing stop command
		System.out
				.println("Test ================ just before stopping packet reading");
		reader.stopReadingPackets();

		List<Packet> receivedPackets = reader.getPackets();
		Iterator<Packet> iterator = receivedPackets.iterator();

		while (iterator.hasNext()) {
			Packet packet = iterator.next();
			writer.writePacket(packet);
		}

	}
}
