import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.peer.ComponentPeer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.logging.Logger;

public class TesterGui{

	private static final long serialVersionUID = -4009476863824852594L;
	private Logger log;
	private ThreadMXBean cpuBean = null;
	private Frame frame = new Frame("Tester");

	
	public TesterGui(){


		init();
		System.out.println("asdfdasfdsfdsf");
	}
	
	private void init(){
//		cpuBean = ManagementFactory.getThreadMXBean();
	    frame.setVisible(true);
	    frame.setBounds(50, 50, 800, 600);
		log = null;

	}
	
	private long updateCpuTime(){
		return cpuBean.getCurrentThreadCpuTime();		
	}
}
