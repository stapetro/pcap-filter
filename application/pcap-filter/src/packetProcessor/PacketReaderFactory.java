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
			String packetFilterRule, long millis) {
		if (source instanceof String) {
			String fileName = (String) source;
			FilePacketReader filePacketReader = new FilePacketReader(fileName,
					packetFilterRule);
			return filePacketReader;

		} else if (source instanceof NetworkInterface) {
			NetworkInterface networkInterface = (NetworkInterface) source;
			NetworkInterfacePacketReader networkInterfacePacketReader = new NetworkInterfacePacketReader(networkInterface, packetFilterRule, (millis));
			return networkInterfacePacketReader;
		}
		return null;
	}

}
