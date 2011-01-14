package packetProcessor;

import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;
import utils.StatisticPrinter;

public class FilePacketReader extends AbstractPacketReader {

	private PacketAnalyzer packetAnalyzer;

	public FilePacketReader(String fileName, String packetFilterRule) {
		try {
			JpcapCaptor captor = JpcapCaptor.openFile(fileName);
			setCaptor(captor);
			setPacketFilterRule(packetFilterRule);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.packetAnalyzer = new PacketAnalyzer();
	}

	@Override
	public void run() {
		JpcapCaptor captor = getCaptor();
		if (captor != null) {
			Packet packet = null;
			while (true) {
				// read a packet from the opened file
				packet = captor.getPacket();
				// if some error occurred or EOF has reached, break the loop
				if (packet == null || packet == Packet.EOF) {
					break;
				}
				packetAnalyzer.receivePacket(packet);
			}
			// captor.updateStat();
			StatisticPrinter.printStatistics(captor, packetAnalyzer
					.getNetworkSessions(), packetAnalyzer
					.getNumberOfCapturedPackets(), packetAnalyzer
					.getNumberOfModifiedPackets());
			captor.close();
		}
	}

	@Override
	public void stopReadingPackets() {
		// do nothing because reading packets from file stops automatically
	}

	@Override
	public void setWriter(IPacketWriter writer) {
		packetAnalyzer.setWriter(writer);
	}

}
