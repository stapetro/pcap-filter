package packetProcessor;

import java.io.IOException;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;

public class NetworkInterfacePacketReader extends AbstractPacketReader {

	private NetworkInterfacePacketReceiver packetReceiver;
	
	public NetworkInterfacePacketReader(NetworkInterface networkInterface,
			String packetFilterRule) {
		this.packetReceiver = new NetworkInterfacePacketReceiver();
		try {
			JpcapCaptor captor = JpcapCaptor.openDevice(networkInterface,
					65535, false, 0);
			setCaptor(captor);
			setPacketFilterRule(packetFilterRule);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Packet> getPackets() {
		return this.packetReceiver.getReceivedPackets();
	}

	public void startReadingPackets() {
		JpcapCaptor captor = getCaptor();
		if (captor != null) {
			captor.processPacket(5, packetReceiver);
			// Loops packet sniffing infinitely
			// captor.loopPacket(-1, packetReceiver);
		}
	}

	@Override
	public Packet readPacket() {
		// TODO To be implemented.
		return null;
	}
}
