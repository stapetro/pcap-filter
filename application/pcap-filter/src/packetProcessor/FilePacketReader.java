package packetProcessor;

import java.io.IOException;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

public class FilePacketReader implements IPacketReader {

	private String packetFilterRule;
	private JpcapCaptor captor;

	public FilePacketReader(String fileName, String packetFilterRule) {
		try {
			captor = JpcapCaptor.openFile(fileName);
			if (packetFilterRule != null) {
				this.packetFilterRule = packetFilterRule;
				captor.setFilter(packetFilterRule, true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startReadingPackets() {
		if (this.captor != null) {
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
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public JpcapCaptor getCaptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Packet> getPackets() {
		// TODO Auto-generated method stub
		return null;
	}

}
