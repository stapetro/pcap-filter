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

	private String packetFilterRule;
	private JpcapCaptor captor;
	private boolean readFromFile;

	private NetworkInterfacePacketReceiver packetReceiver;

	private NetworkInterfacePacketReader() {
		this.packetReceiver = new NetworkInterfacePacketReceiver();
	}

	public NetworkInterfacePacketReader(NetworkInterface networkInterface) {
		this();
		try {
			captor = JpcapCaptor.openDevice(networkInterface, 65535, false, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public NetworkInterfacePacketReader(String fileName) {
		this();
		this.readFromFile = true;
		try {
			captor = JpcapCaptor.openFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JpcapCaptor getCaptor() {
		return captor;
	}

	public List<Packet> getPackets() {
		return this.packetReceiver.getReceivedPackets();
	}

	public String getPacketFilterRule() {
		return packetFilterRule;
	}

	public void setPacketFilterRule(String packetFilterRule) {
		this.packetFilterRule = packetFilterRule;
	}

	public void startReadingPackets() {
		if (this.captor != null) {
			try {
				if (packetFilterRule != null) {
					captor.setFilter(this.packetFilterRule, true);
				}
				if (this.readFromFile) {
					readPacketsFromFile();
				} else {
					captor.processPacket(5, packetReceiver);
					// Loops packet sniffing infinitely
					// captor.loopPacket(-1, packetReceiver);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Packet readPacket() {
		// TODO To be implemented.
		return null;
	}

	@Override
	public void close() {
		if (captor != null) {
			captor.close();
		}
	}

	/**
	 * Reads packets from file.
	 */
	private void readPacketsFromFile() {
		Packet packet = null;
		while (true) {
			// read a packet from the opened file
			packet = captor.getPacket();
			// if some error occurred or EOF has reached, break the loop
			if (packet == null || packet == Packet.EOF) {
				break;
			}
			// otherwise, print out the packet
			System.out.println(packet);
		}
	}

}
