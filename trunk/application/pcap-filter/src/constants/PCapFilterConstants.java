package constants;

public class PCapFilterConstants {

	/**
	 * Filter rules for SIP traffic.
	 */
	public static final String SIP_FILTER = "portrange 5060-5080";
	/**
	 * Filter rules for IP/TCCP traffic.
	 */
	public static final String TCP_IP_FILTER = "ip and tcp";

	public static final String HTTP_FILTER = "port 80";

	public static final int INVALID_PORT_NUMBER = -1;

	public static final int HTTP_TCP_PORT_NUMBER = 80;
	public static final int SIP_UDP_PORT_NUMBER_MIN = 5060;
	public static final int SIP_UDP_PORT_NUMBER_MAX = 5080;

}
