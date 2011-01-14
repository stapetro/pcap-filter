package packetProcessor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;
import utils.StatisticPrinter;

public class FilePacketReader extends AbstractPacketReader implements Runnable {

	private List<Packet> packetsList;
	private PacketAnalyzer packetAnalyzer;

	public FilePacketReader(String fileName, String packetFilterRule) {
		try {
			JpcapCaptor captor = JpcapCaptor.openFile(fileName);
			setCaptor(captor);
			setPacketFilterRule(packetFilterRule);
			packetsList = new LinkedList<Packet>();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.packetAnalyzer = new PacketAnalyzer();
	}

	@Override
	public void startReadingPackets() {
		Thread thread = new Thread(this);
		thread.start();
		Thread.yield();
	}

	public Packet readPacket() {
		return null;
		// TODO implement
	}

	@Override
	public List<Packet> getPackets() {
		// TODO think if we should keep this
		return packetsList;
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
				packetsList.add(packet);
			}
			captor.updateStat();
			StatisticPrinter.printStatistics(captor, packetAnalyzer
					.getNetworkSessions(), packetAnalyzer
					.getNumberOfCapturedPackets(), packetAnalyzer
					.getNumberOfModifiedPackets());
			captor.close();
		}
	}

	@Override
	public void stopReadingPackets() {
		// TODO Auto-generated method stub

	}

}
