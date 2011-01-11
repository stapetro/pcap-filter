package main;

import java.io.IOException;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import packetProcessor.NetworkInterfacePacketReader;
import packetProcessor.NetworkInterfacePacketReceiver;
import constants.PCapFilterConstants;

/**
 * Starts application.
 * 
 */
public class PCapFilterStarter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**** Stanislav Petrov Code *****/
		
		// Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface wanInterface = devices[1];		
		NetworkInterfacePacketReader interfacePacketReader = new NetworkInterfacePacketReader(
				wanInterface);
		System.out.println("Start listening for packets...");		
		interfacePacketReader.startReadingPackets();
		System.out.println("Finish listening for packets...");
		
		/**** Krasimir Baylov Code *****/

	}

}
