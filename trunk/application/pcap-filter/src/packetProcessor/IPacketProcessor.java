package packetProcessor;

import jpcap.packet.Packet;

public interface IPacketProcessor {

	public Packet readNextPacket();
	public void writeNextPacket(Packet packet);
}
