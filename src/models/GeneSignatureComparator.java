package models;

import java.util.Comparator;

public class GeneSignatureComparator implements Comparator<GeneSignature> {
	@Override
	public int compare(GeneSignature o1, GeneSignature o2) {
		return o2.getVariance().compareTo(o1.getVariance());
	}

}
