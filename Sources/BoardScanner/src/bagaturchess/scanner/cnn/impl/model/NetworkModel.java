/**
 *  BagaturChess (UCI chess engine and tools)
 *  Copyright (C) 2005 Krasimir I. Topchiyski (k_topchiyski@yahoo.com)
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
 *  along with BagaturChess. If not, see http://www.eclipse.org/legal/epl-v10.html
 *
 */
package bagaturchess.scanner.cnn.impl.model;


import java.awt.image.BufferedImage;

import deepnetts.net.ConvolutionalNetwork;


public abstract class NetworkModel {
	
	
	protected ConvolutionalNetwork network;
	
	
	public NetworkModel() {
	}
	
	
	public ConvolutionalNetwork getNetwork() {
		return network;
	}
	
	
	public abstract Object createInput(Object image);
	
	
	public abstract void setInputs(Object input);
	
	
	public abstract DataSetInitPair createDataSetInitPair(BufferedImage boardImage);
}
