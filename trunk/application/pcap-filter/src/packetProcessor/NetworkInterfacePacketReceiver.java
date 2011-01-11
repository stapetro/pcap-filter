package packetProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class NetworkInterfacePacketReceiver implements PacketReceiver {

	private List<Packet> receivedPackets;
	
	public NetworkInterfacePacketReceiver(){
		this.receivedPackets = new ArrayList<Packet>();
	}
	
	public List<Packet> getReceivedPackets() {
		return receivedPackets;
	}

	@Override
	public void receivePacket(Packet packet) {
		System.out.println(packet);
		this.receivedPackets.add(packet);
	}

}
