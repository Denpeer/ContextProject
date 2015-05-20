package com.funkydonkies.w4v3;


/** Dummy class.
 * @author Danilo
 *
 */
public class DummyCameraDetection implements Bridge {

	private static final int SIZE = 512;
	private static final int CLAMP = 255;
	
	/** Dummy values.
	 * @return values
	 */
	public float[] getControlPoints() {
		final float[] ret = new float[SIZE * SIZE]; 

		for (int z = 0; z < SIZE; z++) {
			for (int x = 0; x < SIZE; x++) {
				final float temp = (float) 120 / SIZE;
				ret[x + (z * SIZE)] = temp * CLAMP;
			}
		}

		return ret;
	}

	public int getxdist() {
		// TODO Auto-generated method stub
		return 0;
	}

}

/* 
for (int z = 0; z < SIZE; z++) {
for (int x = 0; x < SIZE; x++) {
final float temp = (((float) z * x) / (SIZE * SIZE));
ret[z + (x * SIZE)] = temp * CLAMP;
}
}
*/