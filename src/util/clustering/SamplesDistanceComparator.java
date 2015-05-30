package util.clustering;

import java.util.Comparator;

import models.SamplesDistance;

public class SamplesDistanceComparator implements Comparator<SamplesDistance>{

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(SamplesDistance sampleDistance1, SamplesDistance sampleDistance2) {
		// TODO Auto-generated method stub
		if(sampleDistance1.getDistance() > sampleDistance2.getDistance())
			return 1;
		else if(sampleDistance1.getDistance() < sampleDistance2.getDistance())
			return -1;
		else
			return 0;
	}
	
}
