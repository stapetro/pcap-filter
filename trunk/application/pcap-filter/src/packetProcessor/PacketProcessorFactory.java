package packetProcessor;

/**
 * This is a factory class responsible for providing packet processors used for
 * writing and reading packets.
 */
public class PacketProcessorFactory {

	/**
	 * Provides an IPacketProcessor for reading and writing packets. The source
	 * and destination are determined based on the provided instances. They
	 * could be <code>JpcapWriter</code>, <code>JpcapCaptor</code> or
	 * <code>JpcapSender</code>
	 * 
	 * @param source
	 *            determines the source where packets will be read.
	 * @param destination
	 *            determines the destination where packets will be written
	 * @return
	 */
	public static IPacketProcessor getPacketProcessor(Object source,
			Object destination) {
		IPacketProcessor packetProcessor = new PacketProcessor(source,
				destination);
		return packetProcessor;
	}

}
