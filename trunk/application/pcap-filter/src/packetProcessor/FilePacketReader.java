package packetProcessor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;

public class FilePacketReader extends AbstractPacketReader implements Runnable {

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
		Thread thread = new Thread(this);
		thread.start();
		Thread.yield();
	}

	public Packet readPacket() {
		return null;
		// TODO implement
	}

	@Override
	public List<Packet> getPackets() {
		// TODO think if we should keep this
		return packetsList;
	}

	@Override
	public void run() {
		JpcapCaptor captor = getCaptor();
		if (captor != null) {
			Packet packet = null;
			int capturedPacks = 0;
			while (true) {
				// read a packet from the opened file
				packet = captor.getPacket();
				// if some error occurred or EOF has reached, break the loop
				if (packet == null || packet == Packet.EOF) {
					break;
				}
				packetsList.add(packet);
				capturedPacks++;
			}
			captor.updateStat();
			System.out.println("Received packs: " + captor.received_packets
					+ ", Dropped packs: " + captor.dropped_packets
					+ " Captured: " + capturedPacks);
			captor.close();
		}		
	}

}
