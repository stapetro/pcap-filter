package packetProcessor;

import java.io.IOException;
import java.util.List;

import utils.StatisticPrinter;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class NetworkInterfacePacketReader extends AbstractPacketReader
		implements Runnable {

	private PacketAnalyzer packetAnalyzer;

	public NetworkInterfacePacketReader(NetworkInterface networkInterface,
			String packetFilterRule) {
		this.packetAnalyzer = new PacketAnalyzer();
		try {
			JpcapCaptor captor = JpcapCaptor.openDevice(networkInterface,
					65535, false, 0);
			setCaptor(captor);
			setPacketFilterRule(packetFilterRule);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Packet> getPackets() {
		return this.packetAnalyzer.getReceivedPackets();
	}

	public void startReadingPackets() {
		Thread thread = new Thread(this);
		thread.start();
		Thread.yield();
	}

	@Override
	public Packet readPacket() {
		// TODO To be implemented.
		return null;
	}

	@Override
	public void run() {
		JpcapCaptor captor = getCaptor();
		captor.setNonBlockingMode(true);
		captor.loopPacket(-1, packetAnalyzer);
		captor.updateStat();
		StatisticPrinter.printStatistics(captor, packetAnalyzer
				.getNetworkSessions(), packetAnalyzer
				.getNumberOfCapturedPackets());
	}

	@Override
	public void stopReadingPackets() {
		JpcapCaptor captor = getCaptor();
		captor.breakLoop();
		captor.close();		
	}

}
