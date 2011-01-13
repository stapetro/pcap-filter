package packetProcessor;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.UDPPacket;

public class PacketAnalyzer implements PacketReceiver {

	private List<Packet> receivedPackets;
	private List<NetworkSession> networkSessions;
	private int numberOfCapturedPackets;

	public int getNumberOfCapturedPackets() {
		return numberOfCapturedPackets;
	}

	public PacketAnalyzer() {
		this.receivedPackets = new ArrayList<Packet>();
		this.networkSessions = new ArrayList<NetworkSession>();
	}

	public List<Packet> getReceivedPackets() {
		return receivedPackets;
	}

	@Override
	public void receivePacket(Packet packet) {
		numberOfCapturedPackets++;
		// analyze packet
		if (packet instanceof UDPPacket) {
			UDPPacket udpPacket = (UDPPacket) packet;
			InetAddress sourceIPAddr = udpPacket.src_ip;
			InetAddress destIPAddr = udpPacket.dst_ip;
			int sourcePort = udpPacket.src_port;
			int destPort = udpPacket.dst_port;
			boolean result = isNetworkSessionExists(sourceIPAddr, destIPAddr,
					sourcePort, destPort);
		}
		this.receivedPackets.add(packet);
	}

	public List<NetworkSession> getNetworkSessions() {
		return networkSessions;
	}

	/**
	 * Check if there is new network session and adds it to the pool of
	 * sessions.
	 * 
	 * @param sourceIPAddr
	 * @param destIPAddr
	 * @param sourcePort
	 * @param destPort
	 * @return
	 */
	private boolean isNetworkSessionExists(InetAddress sourceIPAddr,
			InetAddress destIPAddr, int sourcePort, int destPort) {
		NetworkSession otherSession = new NetworkSession(sourceIPAddr,
				destIPAddr, sourcePort, destPort);
		if (networkSessions.size() > 0) {
			for (NetworkSession session : networkSessions) {
				boolean result = otherSession.equals(session);
				System.out.println("Res: " + result);
				if (otherSession.equals(session)) {
					session.incrementNumberOfPackets();
					return true;
				}
			}
		} else {
			networkSessions.add(otherSession);
			otherSession.incrementNumberOfPackets();
		}
		return false;
	}
}
