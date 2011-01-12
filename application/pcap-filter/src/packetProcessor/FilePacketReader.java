package packetProcessor;

import java.io.IOException;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

public class FilePacketReader extends AbstractPacketReader {

	public FilePacketReader(String fileName, String packetFilterRule) {
		try {
			JpcapCaptor captor = JpcapCaptor.openFile(fileName);
			setCaptor(captor);
			setPacketFilterRule(packetFilterRule);
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
				// otherwise, print out the packet
				System.out.println(packet);
			}
		}
	}

	public Packet readPacket() {
		return null;
		// TODO implement
	}

	@Override
	public List<Packet> getPackets() {
		// TODO Auto-generated method stub
		return null;
	}

}
