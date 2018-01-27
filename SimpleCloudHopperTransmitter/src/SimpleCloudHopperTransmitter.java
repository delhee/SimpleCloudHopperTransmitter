import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;


public class SimpleCloudHopperTransmitter {

	/**
	 * @param args
	 */
	private SmppSession session = null;
	private String ipAddress = "192.168.43.28";
	private String systemId = "smppclient1";
	private String password = "password";
	private String shortMessage = "Hello from ALBProgramming!";
	private String srcAddr = "12340001";
	private String destAddr = "56780002";
	private int smppPort = 2775;

	public static void main(String[] args) {
		System.out.println("Program starts...");
		SimpleCloudHopperTransmitter objSimpleCloudHopperTransmitter = new SimpleCloudHopperTransmitter();
		objSimpleCloudHopperTransmitter.bindToSMSC();
		objSimpleCloudHopperTransmitter.sendSingleSMS();
		objSimpleCloudHopperTransmitter.exitSystem();
	}

	private void bindToSMSC() {
		DefaultSmppClient smppClient = new DefaultSmppClient();

		SmppSessionConfiguration config0 = new SmppSessionConfiguration();
		config0.setHost(ipAddress);
		config0.setPort(smppPort);
		config0.setSystemId(systemId);
		config0.setPassword(password);
		config0.setType(SmppBindType.TRANSMITTER);

		try {
			this.session = smppClient.bind(config0);
			System.out.println("Connected to SMSC...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendSingleSMS() {
		Address srcAddr = new Address();
		Address destAddr = new Address();
		byte[] shortMessageBytes = this.shortMessage.getBytes();
		srcAddr.setAddress(this.srcAddr);
		destAddr.setAddress(this.destAddr);
		
		SubmitSm submit0 = new SubmitSm();
		submit0.setSourceAddress(srcAddr);
		submit0.setDestAddress(destAddr);
		try {
			submit0.setShortMessage(shortMessageBytes);
			this.session.submit(submit0, 10000);
			System.out.println("Message submitted...");
		} catch (SmppInvalidArgumentException e) {
			e.printStackTrace();
		} catch (RecoverablePduException e) {
			e.printStackTrace();
		} catch (UnrecoverablePduException e) {
			e.printStackTrace();
		} catch (SmppTimeoutException e) {
			e.printStackTrace();
		} catch (SmppChannelException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void exitSystem() {
		this.session.unbind(0);
		System.out.println("System terminated");
		System.exit(1);
	}

}
