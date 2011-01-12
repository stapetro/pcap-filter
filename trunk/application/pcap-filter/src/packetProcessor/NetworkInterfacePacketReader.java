package packetProcessor;

import java.io.IOException;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class NetworkInterfacePacketReader extends AbstractPacketReader
		implements Runnable {

	private NetworkInterfacePacketReceiver packetAnalyzer;
	private long timeOutInMillis;

	public NetworkInterfacePacketReader(NetworkInterface networkInterface,
			String packetFilterRule, long timeOutInMillis) {
		this.timeOutInMillis = timeOutInMillis;
		this.packetAnalyzer = new NetworkInterfacePacketReceiver();
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
		Thread t = new Thread(this);
		t.start();
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
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		long duration = 0L;
		int numberOfCapturedPackets = 0;
		int result = 0;
		while (true) {
			result = captor.processPacket(1, packetAnalyzer);
			if (result != 0) {
				numberOfCapturedPackets++;
			}
			currentTime = System.currentTimeMillis();
			duration = currentTime - startTime;
			if (duration > timeOutInMillis) {
				break;
			}
		}
		captor.updateStat();
		System.out.println("Caputed: " + numberOfCapturedPackets
				+ " received: " + captor.received_packets + " dropped:"
				+ captor.dropped_packets);
		captor.close();
	}
}
