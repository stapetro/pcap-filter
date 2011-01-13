package packetProcessor;

import java.net.InetAddress;

public class NetworkSession {

	private InetAddress sourceIPAddr;
	private InetAddress destIPAddr;
	private int sourcePort;
	private int destPort;
	private int numberOfPackets;

	public NetworkSession(InetAddress sourceIPAddr, InetAddress destIPAddr,
			int sourcePort, int destPort) {
		this.sourceIPAddr = sourceIPAddr;
		this.destIPAddr = destIPAddr;
		this.sourcePort = sourcePort;
		this.destPort = destPort;
	}

	public void incrementNumberOfPackets() {
		this.numberOfPackets++;
	}

	public int getNumberOfPackets() {
		return this.numberOfPackets;
	}

	public InetAddress getSourceIPAddr() {
		return sourceIPAddr;
	}

	public InetAddress getDestIPAddr() {
		return destIPAddr;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public int getDestPort() {
		return destPort;
	}

	@Override
	public boolean equals(Object otherSession) {
		boolean result = false;
		if (otherSession != null && otherSession instanceof NetworkSession) {
			NetworkSession networkSession = (NetworkSession) otherSession;
			boolean isIPAddrEquals = ((sourceIPAddr
					.equals(networkSession.sourceIPAddr) && destIPAddr
					.equals(networkSession.destIPAddr)) || (sourceIPAddr
					.equals(networkSession.destIPAddr) && destIPAddr
					.equals(networkSession.sourceIPAddr)));
			boolean isPortEquals = ((sourcePort == networkSession.sourcePort && destPort == networkSession.destPort) || (sourcePort == networkSession.destPort && destPort == networkSession.sourcePort));
			result = isIPAddrEquals && isPortEquals;
		}
		return result;
	}

	@Override
	public String toString() {
		return "src: " + sourceIPAddr + ", dest: " + destIPAddr + ", srcPort: "
				+ sourcePort + ", dest: " + destPort;
	}

}
