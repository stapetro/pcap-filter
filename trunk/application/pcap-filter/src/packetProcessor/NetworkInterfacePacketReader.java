package packetProcessor;

import java.io.IOException;
import java.util.List;

import constants.PCapFilterConstants;

import jpcap.JpcapCaptor;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class NetworkInterfacePacketReader implements IPacketReader {

	private JpcapCaptor captor;
	private NetworkInterfacePacketReceiver packetReceiver;

	private NetworkInterfacePacketReader() {
		this.packetReceiver = new NetworkInterfacePacketReceiver();
	}

	public NetworkInterfacePacketReader(NetworkInterface networkInterface) {
		this();
		try {
			captor = JpcapCaptor.openDevice(networkInterface, 65535, false, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public NetworkInterfacePacketReader(String fileName) {
		this();
		try {
			captor = JpcapCaptor.openFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Packet> getPackets() {
		return this.packetReceiver.getReceivedPackets();
	}

	public void startReadingPackets() {
		if (this.captor != null) {
			try {
				captor.setFilter(PCapFilterConstants.IP_TCP_FILTER, true);
				captor.processPacket(10, packetReceiver);
				// Loops packet sniffing infinitely
				// captor.loopPacket(-1, packetReceiver);

				// if (packetReceiver != null) {
				// JpcapWriter writer = null;
				// try {
				// writer = JpcapWriter.openDumpFile(captor,
				// "stas_captured.txt");
				// List<Packet> output = packetReceiver.getReceivedPackets();
				// for (Packet currPacket : output) {
				// // System.out.printf("%n%s%n%n", currPacket.toString());
				// writer.writePacket(currPacket);
				// }
				// System.out.println("Packets are saved successfully");
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// System.out.println("Packets are not saved due to error");
				// }
				// finally {
				// if(writer != null) {
				// writer.close();
				// }
				// }
				// }
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (captor != null) {
					captor.close();
				}
			}
		}
	}

	public Packet readPacket() {
		return null;
	}

}
