package com.graphhopper.routing.util;

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
		double segmentCurvature = OSMReader.getSegmentCurvatureMap().get(edge.getEdge());
		double segmentLength = OSMReader.getSegmentLengthMap().get(edge.getEdge());
		
		if(segmentCurvature > 300 && segmentLength > 1000){
			return 0; //give these babies for free
		}
		return edge.getDistance(); //return shortestpath
	}
	
    @Override
    public String toString(){
        return "RacerBoiSegment";
    }

}
