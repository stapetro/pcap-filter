package main;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import packetProcessor.IPacketReader;
import packetProcessor.IPacketWriter;
import packetProcessor.PacketReaderFactory;
import packetProcessor.PacketWriterFactory;
import constants.PCapFilterConstants;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.JpcapWriter;
import jpcap.NetworkInterface;

public class PCapMenuHandler {

	private static final String FILE = "file";
	private static final String NETWORK = "network";
	private static final String EXIT = "exit";
	private static final String SYSTEM_OUT = "system";

	private Scanner input;

	public PCapMenuHandler() {
		this.input = new Scanner(System.in);
	}

	/**
	 * User interaction starts here
	 */
	public void start() {
		try {
			String sourceChoice = "";
			do {
				System.out.println("select input source:");
				System.out.println("type \"file\" for file");
				System.out.println("type \"network\" for network interface");
				System.out.println("type \"exit\" for exit");

				sourceChoice = input.nextLine();
			} while (sourceChoice.equals(FILE) == false
					&& sourceChoice.equals(NETWORK) == false
					&& sourceChoice.equals(EXIT) == false);

			IPacketReader source = getSourceByChoice(sourceChoice);

			String destinationChoice = "";
			do {
				System.out.println("Select packet destination:");
				System.out.println("type \"file\" for file");
				System.out.println("type \"network\" for network interface");
				System.out.println("type \"system\" for System.out");
				System.out.println("type \"exit\" for exit");

				destinationChoice = input.nextLine();
			} while (sourceChoice.equals(FILE) == false
					&& sourceChoice.equals(NETWORK) == false
					&& sourceChoice.equals(SYSTEM_OUT) == false
					&& sourceChoice.equals(EXIT) == false);

			IPacketWriter destination = getDestinationByChoice(
					destinationChoice, source.getCaptor());

			PCapServer server = new PCapServer(source, destination);
			server.start();
		} catch (IOException ioEx) {
			System.err.print("IO error ocured...");
			ioEx.printStackTrace();
		}
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
			System.exit(1);
		}

		return source;
	}

	private IPacketReader getPacketFilerReader() {
		String fileName = "";
		while (fileName == null || fileName.trim().length() == 0) {
			System.out.println("Enter file name: ");
			fileName = input.nextLine();
		}

		IPacketReader filePacketReader = PacketReaderFactory.getPacketReader(
				fileName, PCapFilterConstants.SIP_FILTER);
		return filePacketReader;
	}

	private IPacketReader getNetworkInterfaceReader() {
		NetworkInterface networkInterface = chooseDevice();
		IPacketReader networkPacketReader = PacketReaderFactory
				.getPacketReader(networkInterface,
						PCapFilterConstants.SIP_FILTER);
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

	private IPacketWriter getDestinationByChoice(String choice,
			JpcapCaptor captor) throws IOException {
		IPacketWriter writer = null;

		if (choice.equals(FILE)) {
			writer = getPacketFileWriter(captor);
		} else if (choice.equals(NETWORK)) {
			writer = getNetworkInterfacePacketWriter();
		} else if (choice.equals(SYSTEM_OUT)) {
			writer = getSystemWriter();
		} else if (choice.equals(EXIT)) {
			System.exit(0);
		} else {
			System.out.println("Error: unknown command...");
			System.exit(1);
		}

		return writer;
	}

	private IPacketWriter getPacketFileWriter(JpcapCaptor captor)
			throws IOException {
		String fileName = "";
		while (fileName == null || fileName.trim().length() == 0) {
			System.out.println("Enter file name: ");
			fileName = input.nextLine();
		}
		JpcapWriter fileWriter = JpcapWriter.openDumpFile(captor, fileName);
		IPacketWriter filePacketWriter = PacketWriterFactory
				.getPacketWriter(fileWriter);

		return filePacketWriter;
	}

	private IPacketWriter getNetworkInterfacePacketWriter() throws IOException {
		NetworkInterface device = chooseDevice();

		JpcapSender sender = JpcapSender.openDevice(device);
		IPacketWriter networkWriter = PacketWriterFactory
				.getPacketWriter(sender);

		return networkWriter;
	}

	private IPacketWriter getSystemWriter() {
		PrintStream printStream = System.out;

		IPacketWriter systemWriter = PacketWriterFactory
				.getPacketWriter(printStream);
		return systemWriter;
	}

	// TODO think if we need to remove this method
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
