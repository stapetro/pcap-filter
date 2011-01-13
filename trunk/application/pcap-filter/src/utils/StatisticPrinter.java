package utils;

import java.util.List;

import jpcap.JpcapCaptor;
import packetProcessor.NetworkSession;

public class StatisticPrinter {

	public static void printStatistics(JpcapCaptor captor,
			List<NetworkSession> networkSessions, int numberOfCapturedPackets) {
		StringBuilder statistics = new StringBuilder("Statistics:\n");
		int numberOfCapturedSessions = networkSessions.size();
		statistics.append("Sessions: " + numberOfCapturedSessions + "\n");
		NetworkSession currentSession = null;
		for (int sessionIndex = 0; sessionIndex < numberOfCapturedSessions; sessionIndex++) {
			currentSession = networkSessions.get(sessionIndex);
			statistics.append("Session " + sessionIndex + "\n (srcIP="
					+ currentSession.getSourceIPAddr() + ", destIP="
					+ currentSession.getDestIPAddr() + ", srcPort="
					+ currentSession.getSourcePort() + ", destPort="
					+ currentSession.getDestPort() + ")\nPackets: "
					+ currentSession.getNumberOfPackets() + "\n");
		}
		statistics.append("Caputed: " + numberOfCapturedPackets + " received: "
				+ captor.received_packets + " dropped:"
				+ captor.dropped_packets + "\n");
		System.out.println(statistics);
	}
}
