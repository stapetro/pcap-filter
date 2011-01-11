package packetProcessor;

import jpcap.packet.Packet;

/**
 * This class provides functionality for reading and writing packets. Sources
 * and destination of packets are determined based on the constructor
 * parameters.
 */
public class PacketProcessor implements IPacketProcessor {

	/**
	 * Reads packets from a predefined source. This could be network interface
	 * or a file.
	 */
	private IPacketReader reader;

	/**
	 * Writes packets to a predefined destination. This could be network
	 * interface of a file.
	 */
	private IPacketWriter writer;

	/**
	 * Constructor initializing the reader and writer variables. They are
	 * initialized based on the input and output objects provided. They could be
	 * <code>JpcapWriter</code>, <code>JpcapCaptor</code> or
	 * <code>JpcapSender</code>
	 * 
	 * @param source
	 *            identifies the source where packets will be read from
	 * @param destination
	 *            identifies the destination where packets will be written to
	 */
	public PacketProcessor(Object source, Object destination) {
		// TODO init reader!!!

	}

	private void initializeWriterByDestination(Object destination) {
		writer = PacketWriterFactory.getPacketWriter(destination);
	}

	public Packet readNextPacket() {
		// TODO implement
		return null;
	}

	public void writeNextPacket(Packet packet) {
		// TODO implement
	}

}
