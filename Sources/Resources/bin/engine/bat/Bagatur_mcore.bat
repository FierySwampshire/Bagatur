

REM Goes to parent directory (the root of the distribution)
cd ..

echo off

REM Sets path to binaries
set BIN_PATH=./bin/


REM Sets the path to jar files containing the compiled java code of Bagatur engine
set JARS_PATH=%BIN_PATH%egtbprobe.jar;%BIN_PATH%BagaturBoard.jar;%BIN_PATH%BagaturOpening.jar;%BIN_PATH%BagaturSearch.jar;%BIN_PATH%BagaturUCI.jar;%BIN_PATH%BagaturEngines.jar;%BIN_PATH%BagaturLearningAPI.jar;%BIN_PATH%BagaturEGTB.jar;%BIN_PATH%BagaturLearningImpl.jar;


REM Sets the memory (in megabytes) for the WHOLE java process.
REM Only one part of this memory (up to 35%) will be used for Transposition Table.
set PROCESS_MEMORY=1024M

set ARGS=bagaturchess.engines.cfg.base.UCIConfig_BaseImpl
set ARGS=%ARGS% bagaturchess.search.impl.uci_adaptor.UCISearchAdaptorImpl_PonderingOpponentMove
set ARGS=%ARGS% bagaturchess.engines.cfg.base.UCISearchAdaptorConfig_BaseImpl
REM set ARGS=%ARGS% bagaturchess.search.impl.rootsearch.sequential.SequentialSearch_MTD
set ARGS=%ARGS% bagaturchess.search.impl.rootsearch.parallel.MTDParallelSearch_ProcessesImpl
REM set ARGS=%ARGS% bagaturchess.engines.cfg.base.RootSearchConfig_BaseImpl_1Core
set ARGS=%ARGS% bagaturchess.engines.cfg.base.RootSearchConfig_BaseImpl_SMP
REM set ARGS=%ARGS% bagaturchess.search.impl.alg.impl1.Search_NegaScout
set ARGS=%ARGS% bagaturchess.search.impl.alg.impl0.Search_PVS_NWS
set ARGS=%ARGS% bagaturchess.engines.cfg.base.SearchConfigImpl_AB
REM set ARGS=%ARGS% bagaturchess.engines.evaladapters.chess22k.cfg.BoardConfigImpl_Chess22k
REM set ARGS=%ARGS% bagaturchess.engines.evaladapters.chess22k.cfg.EvaluationConfg_Chess22k
set ARGS=%ARGS% bagaturchess.learning.goldmiddle.impl.cfg.bagatur_allfeatures.filler.Bagatur_ALL_BoardConfigImpl
set ARGS=%ARGS% bagaturchess.learning.goldmiddle.impl.cfg.bagatur_allfeatures.eval.EvaluationConfig

echo on

REM Executes the java process of the Bagatur engine with parallel search. It uses all CPU Cores.
REM Sometimes, the engine performs a sequential search for depth below 6 because of performance reasons.
REM The idea is to optimize the engine work for short time controls (1 move for 1 second or less). 
java.exe -Xmx%PROCESS_MEMORY% -Djava.library.path=%BIN_PATH% -cp %JARS_PATH% bagaturchess.uci.run.Boot %ARGS%

