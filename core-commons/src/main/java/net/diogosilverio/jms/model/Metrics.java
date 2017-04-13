package net.diogosilverio.jms.model;

import java.io.File;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Just a few system metrics.
 * @author diogo
 *
 */
public class Metrics implements Serializable {

	private static final long serialVersionUID = -1840334410346515312L;
	
	private String host;
	private Short processors;
	private Long freeMemory;
	private Long maxMemory;
	private Long totalMemory;
	private Long totalSpace;
	private Long freeSpace;
	private Long usableSpace;

	public Metrics() {
		super();
		try {
			this.host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			this.host = "???";
		}
		this.processors = (short) Runtime.getRuntime().availableProcessors();
		this.freeMemory = Runtime.getRuntime().freeMemory();
		this.maxMemory = Runtime.getRuntime().maxMemory();
		this.totalMemory = Runtime.getRuntime().totalMemory();
		this.totalSpace = File.listRoots()[0].getTotalSpace();
		this.freeSpace = File.listRoots()[0].getFreeSpace();
		this.usableSpace = File.listRoots()[0].getUsableSpace();
	}

	public String getHost() {
		return host;
	}

	public Short getProcessors() {
		return processors;
	}

	public Long getFreeMemory() {
		return freeMemory;
	}

	public Long getMaxMemory() {
		return maxMemory;
	}

	public Long getTotalMemory() {
		return totalMemory;
	}

	public Long getTotalSpace() {
		return totalSpace;
	}

	public Long getFreeSpace() {
		return freeSpace;
	}

	public Long getUsableSpace() {
		return usableSpace;
	}

	@Override
	public String toString() {
		return "Metrics [host=" + host + ", processors=" + processors + ", freeMemory=" + freeMemory + ", maxMemory="
				+ maxMemory + ", totalMemory=" + totalMemory + ", totalSpace=" + totalSpace + ", freeSpace=" + freeSpace
				+ ", usableSpace=" + usableSpace + "]";
	}
	
	
	
	
}

