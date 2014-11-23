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

import java.util.Random;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.OSMReader;
import com.graphhopper.util.EdgeIteratorState;

/**
 *
 * @author madnificent & felix 
 */
public class RacerboiWeighter extends FastestWeighting
{

    public RacerboiWeighter( FlagEncoder encoder )
    {  
        super(encoder);
        new GraphHopper().logger.info(encoder.getClass().getName());
    }

    @Override
    public double getMinWeight( double distance ) {
        //return 0;
        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((10000 - 0) + 1) + 0;

        return randomNum;
   }

    @Override
    public double calcWeight( EdgeIteratorState edge, boolean reverse )
    {   
    	//Use encoding information, else it is just going trough shortest path
    	double speed = 0;
    	try{
    		speed = reverse ? encoder.getReverseSpeed(edge.getFlags()) : encoder.getSpeed(edge.getFlags());
    	}
    	catch( IllegalStateException e){
    		new GraphHopper().logger.info("ERROR.....................................");
    		new GraphHopper().logger.info("ERROR.....................................");
    		new GraphHopper().logger.info("ERROR......................................");
    		throw e;	
    	}
        if (speed == 0){
        	new GraphHopper().logger.info("Curvature: Unrideable way found...");
            return Double.POSITIVE_INFINITY;
        }
        
        double curvature = OSMReader.getNodeCurvatureMap().get(edge.getEdge());
    	new GraphHopper().logger.info("Curvature: " + curvature);
        return Math.min(50000, Math.max(curvature, 2));
        
    }
    
    @Override
    public String toString()
    {
        return "RacerBoi|" + this.encoder;
    }
    
}
