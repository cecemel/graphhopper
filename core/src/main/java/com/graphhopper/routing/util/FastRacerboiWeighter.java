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

import com.graphhopper.reader.OSMReader;
import com.graphhopper.util.EdgeIteratorState;

/**
 *
 * @author madnificent
 */
public class FastRacerboiWeighter extends FastestWeighting
{
     public FastRacerboiWeighter( FlagEncoder encoder )
    {
        super(encoder);
    }

    @Override
    public double getMinWeight( double distance )
    {
        return 0;
    }

    @Override
    public double calcWeight( EdgeIteratorState edge, boolean reverse )
    {
        double fastestWeight = super.calcWeight(edge, reverse);
        double curvature = OSMReader.getNodeCurvatureMap().get(edge.getEdge());
        double newValue = fastestWeight * curvature;
//        System.out.println("Curvature: " + curvature + " Old weight: " + fastestWeight + " new weight: " + newValue);
        return newValue;
    }   
}
