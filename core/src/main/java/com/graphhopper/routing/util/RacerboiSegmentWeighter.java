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
		
		double segmentCurvature = OSMReader.getSegmentCurvatureMap().get(edge.getEdge());
		double segmentLength = OSMReader.getSegmentLengthMap().get(edge.getEdge());
		double curvature = OSMReader.getNodeCurvatureMap().get(edge.getEdge());
		
		if(segmentCurvature > 100 && segmentLength > 100){
			//new GraphHopper().logger.info("Got fun segment ###################");
			return 0; //give these babies for free
		}
		
		//double test = curvature * edge.getDistance();
		//new GraphHopper().logger.info("###################### WEIGHT: " + String.valueOf(test));
		//new GraphHopper().logger.info("###################### DISTANCE: " + String.valueOf(edge.getDistance()));
		return curvature * edge.getDistance();
	}
	
    @Override
    public String toString(){
        return "RacerBoiSegment";
    }

}
