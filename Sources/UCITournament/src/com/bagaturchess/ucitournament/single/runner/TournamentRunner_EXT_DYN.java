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
package com.bagaturchess.ucitournament.single.runner;


import bagaturchess.ucitracker.impl.Engine;

import com.bagaturchess.ucitournament.framework.match.MatchRunner;
import com.bagaturchess.ucitournament.framework.match.MatchRunner_TimeAndInc;
import com.bagaturchess.ucitournament.framework.utils.BagaturEngine;
import com.bagaturchess.ucitournament.single.Tournament;
import com.bagaturchess.ucitournament.single.schedule.ITournamentSchedule;
import com.bagaturchess.ucitournament.single.schedule.TournamentSchedule_EvenScores;


public class TournamentRunner_EXT_DYN {
	
	
	public static void main(String[] args) {
		
		Engine bagatur_NoExts = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.ECFG_NoExts", "");
		Engine bagatur_Default = new BagaturEngine("com.krasimir.topchiyski.chess.properties.EngineConfigBaseImpl", "");
		
		
		Engine bagatur1 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "1");
		Engine bagatur10 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "10");
		Engine bagatur25 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "25");
		Engine bagatur50 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "50");
		Engine bagatur100 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "100");
		Engine bagatur200 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "200");
		Engine bagatur400 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "400");
		Engine bagatur1000 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "1000");
		Engine bagatur10000 = new BagaturEngine("com.krasimir.topchiyski.chess.configs.tune.exts.extmode_mixed.all16.ECFG_DynamicExts", "10000");
		
		Engine[] engines = new Engine[] {bagatur_NoExts, bagatur_Default,
				bagatur1, bagatur10, bagatur25, bagatur50, bagatur100, bagatur200, bagatur400, bagatur1000, bagatur10000};
		
		
		try {
			
			for (int i=0; i<engines.length; i++) {
				engines[i].start();
				engines[i].supportsUCI();
				engines[i].stop();
			}
			
			System.out.println("Engines start check: OK");
			
			//ITournamentSchedule schedule = new ITournamentSchedule_OneRound(engines);
			ITournamentSchedule schedule = new TournamentSchedule_EvenScores(engines);
			
			//MatchRunner matchRunner = new MatchRunner_TimePerMove(1 * 1000);
			//MatchRunner matchRunner = new MatchRunner_FixedDepth(3);
			MatchRunner matchRunner = new MatchRunner_TimeAndInc(30 * 1000, 30 * 1000, 3 * 1000, 3 * 1000);
			//MatchRunner matchRunner = new MatchRunner_TimeAndInc(1 * 60 * 1000, 1 * 60 * 1000, 3 * 1000, 3 * 1000);
			
			Tournament tournament = new Tournament(schedule, matchRunner);
			
			tournament.start();
			
		} catch (Exception e) {
			
			for (int i=0; i<engines.length; i++) {
				try {
					engines[i].stop();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
			
			e.printStackTrace();
			
			System.exit(-1);
		}
	}
}