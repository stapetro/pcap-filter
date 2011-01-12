package packetProcessor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

public class FilePacketReader extends AbstractPacketReader {

	private List<Packet> packetsList;

	public FilePacketReader(String fileName, String packetFilterRule) {
		try {
			JpcapCaptor captor = JpcapCaptor.openFile(fileName);
			setCaptor(captor);
			setPacketFilterRule(packetFilterRule);
			
			packetsList = new LinkedList<Packet>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startReadingPackets() {
		JpcapCaptor captor = getCaptor();
		if (captor != null) {
			Packet packet = null;
			while (true) {
				// read a packet from the opened file
				packet = captor.getPacket();
				// if some error occurred or EOF has reached, break the loop
				if (packet == null || packet == Packet.EOF) {
					break;
				}
				packetsList.add(packet);
			}
		}
	}

	public Packet readPacket() {
		return null;
		// TODO implement
	}

	@Override
	public List<Packet> getPackets() {
		//TODO think if we should keep this
		return packetsList;
	}

}
