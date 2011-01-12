package packetProcessor;

import java.util.ArrayList;
import java.util.List;

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
		//analyze packet
//		System.out.println(packet);
		this.receivedPackets.add(packet);
	}
}
