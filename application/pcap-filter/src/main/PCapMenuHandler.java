package main;

import java.io.IOException;
import java.util.Scanner;

import packetProcessor.IPacketReader;
import packetProcessor.PacketReaderFactory;
import constants.PCapFilterConstants;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class PCapMenuHandler {

	private static final String FILE = "file";
	private static final String NETWORK = "network";
	private static final String EXIT = "exit";

	private Scanner input;

	public PCapMenuHandler() {
		this.input = new Scanner(System.in);
	}

	/**
	 * User interaction starts here
	 */
	public void start() {
		System.out.println("select input source:");
		System.out.println("type \"file\" for file");
		System.out.println("type \"network\" for network interface");
		System.out.println("type \"exit\" for exit");

		String choice = input.next();
		IPacketReader source = getSourceByChoice(choice);

		PCapServer server = new PCapServer(source, null);
		server.start();
	}

	private IPacketReader getSourceByChoice(String choice) {
		IPacketReader source = null;
		if (choice.equals(FILE)) {
			source = getPacketFilerReader();
		} else if (choice.equals(NETWORK)) {
			source = getNetworkInterfaceReader();
		} else if (choice.equals(EXIT)) {
			System.exit(0);
		} else {
			System.out.println("Error: unknown command...");
		}

		return source;
	}

	private IPacketReader getPacketFilerReader() {
		String fileName = "";
		while (fileName == null || fileName.trim().length() == 0) {
			System.out.println("Enter file name: ");
			fileName = input.next();
		}

		IPacketReader filePacketReader = PacketReaderFactory.getPacketReader(
				fileName, PCapFilterConstants.SIP_FILTER, 11111);
		return filePacketReader;
	}

	private IPacketReader getNetworkInterfaceReader() {
		NetworkInterface networkInterface = chooseDevice();
		int timeoutSeconds = getTimeout();

		IPacketReader networkPacketReader = PacketReaderFactory
				.getPacketReader(networkInterface,
						PCapFilterConstants.SIP_FILTER,
						(timeoutSeconds * 100L));

		return networkPacketReader;
	}

	private NetworkInterface chooseDevice() {
		StringBuilder outputMsg = new StringBuilder(
				"Please choose device from list below:\n");
		// Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0;
		for (index = 0; index < devices.length; index++) {
			outputMsg.append((index + 1) + ", " + devices[index].description
					+ "\n");
		}
		System.out.println(outputMsg);
		int deviceNumber = 0;
		while (true) {
			System.out.print("Enter number: ");
			String line = input.nextLine();
			try {
				deviceNumber = Integer.parseInt(line);
				index = deviceNumber - 1;
				if (index >= 0 && index < devices.length) {
					break;
				} else {
					System.out.println("Number is out of range");
				}
			} catch (NumberFormatException ex) {
				System.out.println("Wrong number");
			}
		}
		return devices[index];
	}

	private int getTimeout() {
		int timeoutSeconds;

		while (true) {
			System.out.println("Enter timeout in seconds: ");
			String timeStr = input.nextLine();
			try {
				timeoutSeconds = Integer.parseInt(timeStr);
				if (timeoutSeconds > 0) {
					break;// breaks only if the parsing was OK
				} else {
					System.out
							.println("You have entered negative value. Please enter positive one!");
				}
			} catch (NumberFormatException ex) {
				System.out.println("wrong number");
			}
		}

		return timeoutSeconds;
	}
}
