package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import manipulator.SipManager;
import jpcap.JpcapCaptor;
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
		// JpcapCaptor captor = reader.getCaptor();

		try {
			List<Packet> packets = reader.getPackets();
			
			Properties prop = new Properties();
			prop.load(new FileInputStream("SIP_MASK.properties"));

			SipManager sipManager = new SipManager(prop);

			if (packets != null) {
				for (Packet currPacket : packets) {
					System.out.println("-----\n old:");
					System.out.print(new String(currPacket.data));
					byte[] modifiedSipPacket = sipManager
							.modifyPacket(currPacket.data);

					System.out.println("-----\n new: ");

					System.out.print(new String(modifiedSipPacket));
					System.out.println("-----");
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
