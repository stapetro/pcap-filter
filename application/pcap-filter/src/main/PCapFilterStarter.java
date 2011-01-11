package main;

import java.io.IOException;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import packetProcessor.IPacketReader;
import packetProcessor.IPacketWriter;
import packetProcessor.NetworkInterfacePacketReader;
import packetProcessor.PacketReaderFactory;
import packetProcessor.PacketWriterFactory;
import constants.PCapFilterConstants;

/**
 * Starts application.
 * 
 */
public class PCapFilterStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**** Stanislav Petrov Code *****/

		String outputSipFile = "sip_session_captured.jpcap";
		// String outputSipFile = "test_packets";

		// Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface wanInterface = devices[1];
		// IPacketReader interfacePacketReader = PacketReaderFactory
		// .getPacketReader(wanInterface, PCapFilterConstants.SIP_FILTER);
		IPacketReader interfacePacketReader = PacketReaderFactory
				.getPacketReader(outputSipFile, PCapFilterConstants.SIP_FILTER);

		System.out.println("Start listening for packets...");
		interfacePacketReader.startReadingPackets();
		System.out.println("Finish listening for packets...");
		JpcapCaptor captor = interfacePacketReader.getCaptor();
		IPacketWriter packetWriter = null;
		try {
//			JpcapWriter writer = JpcapWriter
//					.openDumpFile(captor, outputSipFile);
//			packetWriter = PacketWriterFactory.getPacketWriter(writer);
			// Reading of packets shouldn't be here.
			List<Packet> packets = interfacePacketReader.getPackets();
			for (Packet currPacket : packets) {
//				 packetWriter.writePacket(currPacket);
//				System.out.println(currPacket);
			}
//		} catch (IOException e) {
//			e.printStackTrace();
		} finally {
			if (packetWriter != null) {
				packetWriter.close();
			}
		}
		interfacePacketReader.close();

		/**** Krasimir Baylov Code *****/

	}

}
