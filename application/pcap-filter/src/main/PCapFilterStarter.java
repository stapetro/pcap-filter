package main;

import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
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
		// Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface wanInterface = devices[1];
		JpcapCaptor captor = null;
		try {
			captor = JpcapCaptor.openDevice(wanInterface, 65535, false, 0);
			captor.setFilter(PCapFilterConstants.SIP_FILTER, true);
//			captor.processPacket(-1, new PacketPrinter());
//			Loops packet sniffing infinitely
//			captor.loopPacket(-1, new PacketPrinter());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (captor != null) {
				captor.close();
			}
		}

	}

}
