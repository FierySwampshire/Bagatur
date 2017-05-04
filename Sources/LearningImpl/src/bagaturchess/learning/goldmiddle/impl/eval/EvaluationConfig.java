package bagaturchess.learning.goldmiddle.impl.eval;


import bagaturchess.learning.goldmiddle.impl.cfg.bagatur.eval.BagaturPawnsEvalFactory;
import bagaturchess.search.api.IEvalConfig;


public class EvaluationConfig implements IEvalConfig {

	@Override
	public boolean useLazyEval() {
		return true;
	}

	@Override
	public boolean useEvalCache() {
		return true;
	}

	@Override
	public String getEvaluatorFactoryClassName() {
		return FeaturesEvaluatorFactory.class.getName();
	}

	@Override
	public String getPawnsCacheFactoryClassName() {
		return BagaturPawnsEvalFactory.class.getName();
		//return bagaturchess.bitboard.impl.eval.pawns.model.PawnsModelEvalFactory.class.getName();
	}

}
