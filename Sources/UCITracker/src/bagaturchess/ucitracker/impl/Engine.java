/*
 *  BagaturChess (UCI chess engine and tools)
 *  Copyright (C) 2005 Krasimir I. Topchiyski (k_topchiyski@yahoo.com)
 *  
 *  Open Source project location: http://sourceforge.net/projects/bagaturchess/develop
 *  SVN repository https://bagaturchess.svn.sourceforge.net/svnroot/bagaturchess
 *
 *  This file is part of BagaturChess program.
 * 
 *  BagaturChess is open software: you can redistribute it and/or modify
 *  it under the terms of the Eclipse Public License version 1.0 as published by
 *  the Eclipse Foundation.
 *
 *  BagaturChess is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  Eclipse Public License for more details.
 *
 *  You should have received a copy of the Eclipse Public License version 1.0
 *  along with BagaturChess. If not, see <http://www.eclipse.org/legal/epl-v10.html/>.
 *
 */
package bagaturchess.ucitracker.impl;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class Engine {
	
	
	private String startCommand;
	private String[] props;
	private String workDir;
	private Process process;
	private BufferedReader is;
	private BufferedWriter os;
	private BufferedReader err;
	
	private EngineDummperThread dummper;
	
	
	public Engine(String _startCommand, String[] _props, String _workDir) {
		System.out.println(_startCommand);
		startCommand = _startCommand;
		props = _props;
		workDir = _workDir;
	}
	
	public void setDummperMode(boolean enabled) {
		if (enabled) {
			dummper.enabled();
		} else {
			dummper.disable();
		}
	}
	
	public void start() throws IOException {
		//Process process = Runtime.getRuntime().exec(startCommand);
		process = Runtime.getRuntime().exec(startCommand, props, new File(workDir));
		is = new BufferedReader(new InputStreamReader(process.getInputStream()));
		os = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		dummper = new EngineDummperThread("OUT", is);
		dummper.start();
		(new EngineDummperThread("ERR", err)).start();
	}
	
	public void stop() throws IOException {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {}
		}
		
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {}
		}
		
		if (err != null) {
			try {
				err.close();
			} catch (IOException e) {}
		}
		
		if (process != null) {
			process.destroy();
		}
		
		dummper.interrupt();
	}
	
	public boolean supportsUCI() throws IOException {
		
		os.write("uci");	
		os.newLine();
		os.flush();
		
		String line;
		while ((line = is.readLine()) != null) {
			if (line.contains("uciok")) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isReady() throws IOException {
		
		os.write("isready");	
		os.newLine();
		os.flush();
		
		String line;
		while ((line = is.readLine()) != null) {
			if (line.contains("readyok")) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setupPossition(String position) throws IOException {
		os.write("position " + position);	
		os.newLine();
		os.flush();
	}
	
	public void go(int depth) throws IOException {
		//System.out.println("go depth " + depth);
		os.write("go depth " + depth);
		os.newLine();
		os.flush();
	}
	
	public void go_FixedNodes(int nodes) throws IOException {
		os.write("go nodes " + nodes);
		os.newLine();
		os.flush();
	}
	
	public void go_FixedDepth(int depth) throws IOException {
		//System.out.println("go depth " + depth);
		os.write("go depth " + depth);
		os.newLine();
		os.flush();
	}
	
	public void go_TimePerMove(int milis) throws IOException {
		//System.out.println("go depth " + depth);
		os.write("go movetime " + milis);
		os.newLine();
		os.flush();
	}
	
	public void go_TimeAndInc(int wtime, int btime, int winc, int binc) throws IOException {
		//go wtime 120000 btime 120000 winc 6000 binc 6000
		os.write("go wtime " + wtime + " btime " + btime + " winc " + winc + " binc " + binc);
		os.newLine();
		os.flush();
	}

	
	public void newGame() throws IOException {
		os.write("ucinewgame");	
		os.newLine();
		os.flush();
	}

	public String getInfoLine() throws IOException {
		
		List<String> lines = new ArrayList<String>();
		
		String line;
		while ((line = is.readLine()) != null) {
			if (line.contains("bestmove")) {
				for (int i=lines.size() - 1; i >=0; i--) {
					if (lines.get(i).contains("info depth") && lines.get(i).contains(" pv ")) {
						//System.out.println("PV: '" + lines.get(i) + "'");
						return lines.get(i);
					}
				}
				throw new IllegalStateException("No pv: " + lines);
			}
			
			//System.out.println(line);
			
			lines.add(line);
		}
		
		throw new IllegalStateException();
	}	
	
	public String getInfoLine1() throws IOException {
		
		List<String> lines = new ArrayList<String>();
		
		//System.out.println("in");
		
		String line;
		//while ((line = is.readLine()) != null) {
		
		while (true) {
			
			//System.out.println("in");
			/*long starttime = System.currentTimeMillis();
			while (!is.ready()) {
				long endtime = System.currentTimeMillis();
				if (endtime - starttime > 1000) {
					System.out.println("hangs");
					return null; //engine hangs
				}
				try {
					//System.out.println("retry");
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			
			line = is.readLine();
			//System.out.println(line);
			
			if (line.contains("bestmove")) {
				for (int i=lines.size() - 1; i >=0; i--) {
					if (lines.get(i).startsWith("info")
							&& lines.get(i).contains(" depth ")
							&& (lines.get(i).contains(" pv ") || lines.get(i).contains(" multipv 1 "))) {
						return lines.get(i);
					}
				}
				
				//No info before bestmove
				//System.out.println("No info before bestmove");
				return null;
				//throw new IllegalStateException("No pv: " + lines);
			}
			lines.add(line);
		}
		
		//throw new IllegalStateException("getInfoLine blocked");
	}

	public String getName() {
		int idx = Math.max(startCommand.lastIndexOf('/'), startCommand.lastIndexOf('\\'));
		if (idx < 0) {
			idx = 0;
		}
		return startCommand.substring(idx + 1);
	}
}