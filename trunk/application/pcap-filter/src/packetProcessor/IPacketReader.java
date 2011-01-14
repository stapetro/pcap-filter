package packetProcessor;

import jpcap.JpcapCaptor;

public interface IPacketReader {

	/**
	 * Starts process of reading packets.
	 */
	public void startReadingPackets();

	public void stopReadingPackets();

	public JpcapCaptor getCaptor();

}
