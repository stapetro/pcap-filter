package packetProcessor;

import jpcap.NetworkInterface;

/**
 * This factory class provides instances for reading packets.
 */
public class PacketReaderFactory {

	/**
	 * Provides an <code>IPacketReader</code> instance for reading packets from
	 * the specified source.
	 * 
	 * @param source
	 *            determined the source where packets will be read from.
	 * @return <code>IPacketReader</code> for reading packets
	 */
	public static IPacketReader getPacketReader(Object source,
			String packetFilterRule) {
		NetworkInterfacePacketReader packetReader = null;
		if (source instanceof String) {
			String fileName = (String) source;
			packetReader = new NetworkInterfacePacketReader(fileName);
			packetReader.setPacketFilterRule(packetFilterRule);

		} else if (source instanceof NetworkInterface) {
			NetworkInterface networkInterface = (NetworkInterface) source;
			packetReader = new NetworkInterfacePacketReader(networkInterface);
			packetReader.setPacketFilterRule(packetFilterRule);
		}
		return packetReader;
	}

}
