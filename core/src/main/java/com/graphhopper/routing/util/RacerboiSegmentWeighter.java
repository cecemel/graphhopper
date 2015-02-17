package com.graphhopper.routing.util;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.OSMReader;
/**
*
* @author felix 
*/
import com.graphhopper.util.EdgeIteratorState;

public class RacerboiSegmentWeighter implements Weighting {
	
	@Override
	public double getMinWeight(double distance) {
		return distance;
	}

	@Override
	public double calcWeight(EdgeIteratorState edge, boolean reverse) {
		
		//get the curvature of segment
		//new GraphHopper().logger.info("STARTING ###################");
		double segmentCurvature = OSMReader.getSegmentCurvatureMap().get(edge.getEdge());
		
		//new GraphHopper().logger.info("GOT CURVATURE:" + String.valueOf(segmentCurvature));
		double segmentLength = OSMReader.getSegmentLengthMap().get(edge.getEdge());
		
		if(segmentCurvature > 100 && segmentLength > 100){
			//new GraphHopper().logger.info("Got fun segment ###################");
			return 0; //give these babies for free
		}
		return 10000 * edge.getDistance(); //return shortestpath
	}
	
    @Override
    public String toString(){
        return "RacerBoiSegment";
    }

}
