package constants;

public class PCapFilterConstants {
	
	/**
	 * Filter rules for SIP traffic.
	 */
	public static final String SIP_FILTER = "udp and portrange 5060-5080";
	/**
	 * Filter rules for IP/TCCP traffic.
	 */
	public static final String TCP_IP_FILTER = "ip and tcp";

}
