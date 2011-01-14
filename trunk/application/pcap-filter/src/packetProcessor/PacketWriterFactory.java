package packetProcessor;

import java.io.PrintStream;

import jpcap.JpcapSender;
import jpcap.JpcapWriter;

public class PacketWriterFactory {

	public static IPacketWriter getPacketWriter(Object destination) {
		if (destination instanceof JpcapSender) {
			// send packets over the network interface

			JpcapSender sender = (JpcapSender) destination;
			return new NetworkInterfacePacketWriter(sender);
		} else if (destination instanceof JpcapWriter) {
			// write packets to a file

			JpcapWriter writer = (JpcapWriter) destination;
			return new FilePacketWriter(writer);
		} else if (destination instanceof PrintStream) {
			// write packet to the System output

			PrintStream printStream = (PrintStream) destination;
			return new SystemWriter(printStream);
		}

		// if no proper destination objects is provided return null
		return null;
	}

}
