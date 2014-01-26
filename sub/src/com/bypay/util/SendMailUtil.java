package com.bypay.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class SendMailUtil {
	/**
	 * Call linux command to send mail
	 * @param cmd
	 * @param tp
	 * @return
	 */
	public static String runCommand(String cmd, int tp) {
		StringBuffer buf = new StringBuffer(1000);
		String rt = "-1";
		try {
			String[] cmdA = { "/bin/sh", "-c", cmd };
			Process pos = Runtime.getRuntime().exec(cmdA);
			pos.waitFor();
			if (tp == 1) {
				if (pos.exitValue() == 0) {
					rt = "1";
				}
			} else {
				InputStreamReader ir = new InputStreamReader(
						pos.getInputStream(),"UTF-8");
				LineNumberReader input = new LineNumberReader(ir);
				String ln = "";
				while ((ln = input.readLine()) != null) {
					buf.append(ln + "<br>");
				}
				rt = buf.toString();
				input.close();
				ir.close();
			}
		} catch (java.io.IOException e) {
			rt = e.toString();
		} catch (Exception e) {
			rt = e.toString();
		}
		return rt;
	}
}
