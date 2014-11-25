/*
 * Copyright 2014 madnificent.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.graphhopper.routing.util;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.OSMReader;
import com.graphhopper.util.EdgeIteratorState;

/**
 *
 * @author madnificent & felix 
 */
public class RacerboiWeighter implements Weighting
{
	private final FlagEncoder encoder;

    public RacerboiWeighter( FlagEncoder encoder )
    {  
    	this.encoder = encoder;
        //new GraphHopper().logger.info(encoder.getClass().getName());
    }

    @Override
    public double getMinWeight( double distance ) {
    	
    	new GraphHopper().logger.info("Min weight called");
        return 0;
   }

    @Override
    public double calcWeight( EdgeIteratorState edge, boolean reverse )
    {   
    	//new GraphHopper().logger.info("Using encoder: " + encoder.getClass().getName());
    	//Use encoding information, else it is just going trough shortest path
    	double speed = 0;
    	speed = reverse ? encoder.getReverseSpeed(edge.getFlags()) : encoder.getSpeed(edge.getFlags());
    	
        if (speed == 0){
        	new GraphHopper().logger.info("Curvature: Unrideable way found...");
            return Double.POSITIVE_INFINITY;
        }
        
        //new GraphHopper().logger.info("Speed: " + speed);
        double curvature = OSMReader.getNodeCurvatureMap().get(edge.getEdge());
    	//new GraphHopper().logger.info("Curvature: " + curvature);
        //return speed/curvature; 
        return curvature; //this is cooler, but takes autobahn, we don't want this
        
    }
    
    @Override
    public String toString()
    {
        return "RacerBoi";
    }
    
}
