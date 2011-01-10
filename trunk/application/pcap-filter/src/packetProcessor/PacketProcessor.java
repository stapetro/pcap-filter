package packetProcessor;

import jpcap.packet.Packet;

public class PacketProcessor implements IPacketProcessor{

	private IPacketReader reader;
	private IPacketWriter writer;
	
	public PacketProcessor(Object input, Object output){
		//TODO implement
		//use the IPacket factories to obtain the right reader and writer
	}
	
	public Packet readNextPacket(){
		//TODO implement
		return null;
	}
	
	public void writeNextPacket(Packet packet){
		//TODO implement
	}
	
}
