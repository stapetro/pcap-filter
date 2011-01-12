package main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
		int deviceIndex = 1;

		// Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface wanInterface = devices[deviceIndex];
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
			// JpcapWriter writer = JpcapWriter
			// .openDumpFile(captor, outputSipFile);
			// packetWriter = PacketWriterFactory.getPacketWriter(writer);
			// Reading of packets shouldn't be here.
			List<Packet> packets = interfacePacketReader.getPackets();
			if (packets != null) {
				for (Packet currPacket : packets) {
					// packetWriter.writePacket(currPacket);
					// System.out.println(currPacket);
				}
			}
			// } catch (IOException e) {
			// e.printStackTrace();
		} finally {
			if (packetWriter != null) {
				packetWriter.close();
			}
		}
		interfacePacketReader.close();

		/**** Krasimir Baylov Code *****/

	}

	private static NetworkInterface chooseDevice() {
		StringBuilder outputMsg = new StringBuilder(
				"Please choose device from list below:\n");
		// Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0;
		for (index = 0; index < devices.length; index++) {
			outputMsg.append((index + 1) + ", " + devices[index].description
					+ "\n");
		}
		System.out.println(outputMsg);
		int deviceNumber = 0;
		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.print("Enter number: ");
			String line = input.nextLine();
			try {
				deviceNumber = Integer.parseInt(line);
				index = deviceNumber - 1;
				if(index >= 0 && index < devices.length) {
					break;					
				} else {
					System.out.println("Number is out of range");
				}
			} catch (NumberFormatException ex) {
				System.out.println("Wrong number");
			}
		}
		return devices[index];
	}

}
