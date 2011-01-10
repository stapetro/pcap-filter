package packetProcessor;

import jpcap.packet.Packet;

public interface IPacketReader {
	
	public Packet readPacket();
	
}
