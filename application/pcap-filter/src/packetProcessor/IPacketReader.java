package packetProcessor;

import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

public interface IPacketReader {

	/**
	 * Starts process of reading packets.
	 */
	public void startReadingPackets();
	public void stopReadingPackets();
	
	public Packet readPacket();
	
	//TODO to be deleted
	public List<Packet> getPackets();
	public JpcapCaptor getCaptor();
	
	/**
	 * Close source of packets.
	 */
	public void close();

}
