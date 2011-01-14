package packetProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;
import manipulator.PacketManipulator;
import constants.PCapFilterConstants;

public class PacketAnalyzer implements PacketReceiver {

	private List<Packet> receivedPackets;
	private List<NetworkSession> networkSessions;
	private int numberOfCapturedPackets;
	private int numberOfModifiedPackets;
	private PacketManipulator sipManager;

	public PacketAnalyzer() {
		this.receivedPackets = new ArrayList<Packet>();
		this.networkSessions = new ArrayList<NetworkSession>();
		Properties prop = new Properties();
		String propFileName = "SIP_MASK.properties";
		try {
			prop.load(new FileInputStream(propFileName));
			sipManager = new PacketManipulator(prop);
		} catch (FileNotFoundException e) {
			System.out.println("Properties file '" + propFileName
					+ "' is not found.");
		} catch (IOException e) {
			System.out.println("Properties file '" + propFileName
					+ "' cannot be loaded.");
		}
	}

	public List<Packet> getReceivedPackets() {
		return receivedPackets;
	}

	public int getNumberOfCapturedPackets() {
		return numberOfCapturedPackets;
	}

	public int getNumberOfModifiedPackets() {
		return numberOfModifiedPackets;
	}

	@Override
	public void receivePacket(Packet packet) {
		numberOfCapturedPackets++;
		// analyze packet
		handleTransportPacket(packet);
		// write packet here
		this.receivedPackets.add(packet);
	}

	public List<NetworkSession> getNetworkSessions() {
		return networkSessions;
	}

	private void handleTransportPacket(Packet packet) {
		if (packet != Packet.EOF) {
			InetAddress sourceIPAddr = null;
			InetAddress destIPAddr = null;
			int sourcePort = PCapFilterConstants.INVALID_PORT_NUMBER;
			int destPort = PCapFilterConstants.INVALID_PORT_NUMBER;
			boolean transportPacketCaptured = false;
			if (packet instanceof UDPPacket) {
				UDPPacket udpPacket = (UDPPacket) packet;
				sourceIPAddr = udpPacket.src_ip;
				destIPAddr = udpPacket.dst_ip;
				sourcePort = udpPacket.src_port;
				destPort = udpPacket.dst_port;
				transportPacketCaptured = true;
			} else if (packet instanceof TCPPacket) {
				TCPPacket tcpPacket = (TCPPacket) packet;
				sourceIPAddr = tcpPacket.src_ip;
				destIPAddr = tcpPacket.dst_ip;
				sourcePort = tcpPacket.src_port;
				destPort = tcpPacket.dst_port;
				transportPacketCaptured = true;
			}
			if (transportPacketCaptured == true) {
				isNetworkSessionExists(sourceIPAddr, destIPAddr, sourcePort,
						destPort);
				byte[] newPacketData = sipManager.modifyPacket(packet.data);
				packet.data = newPacketData;
				numberOfModifiedPackets++;
			}
		}
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
				if (otherSession.equals(session)) {
					session.incrementNumberOfPackets();
					return true;
				}
			}
			networkSessions.add(otherSession);
		} else {
			networkSessions.add(otherSession);
			otherSession.incrementNumberOfPackets();
		}
		return false;
	}
}
