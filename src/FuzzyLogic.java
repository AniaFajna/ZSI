public class FuzzyLogic {

	int roznicaWysokosci;
	int planeX, planeY;
	private final double[][] alfaTable = new double[5][91];
	double[][] T = new double[6][201];
	int[] floorX;
	double[] floorY;
	int floorYY;

	FuzzyLogic(int[] floorX, double[] floorY, int planeX, int planeY) {
		fillAlfaTable();
		fillDeltaH();
		this.floorX = floorX;
		this.floorY = floorY;
		this.planeX = planeX;
		this.planeY = planeY;
	}

	public void active() {
		roznicaWysokosci = (int) floorY[planeX/10] - planeY; // Math.ceil()
		double r = Math.sqrt(2500 + Math.pow(roznicaWysokosci, 2));
		double aIndex = findAngle(String.valueOf(roznicaWysokosci/r));
		int a = (int) aIndex;

		System.out.println(roznicaWysokosci);
		System.out.println(aIndex + "\n");


		double obniz5_1 = Math.min(alfaTable[0][a], T[1][roznicaWysokosci]);
		double obniz5_2 = Math.min(alfaTable[1][a], T[1][roznicaWysokosci]);
		double rown_1 = Math.min(alfaTable[2][a], T[1][roznicaWysokosci]);
		double podw10_1 = Math.min(alfaTable[3][a], T[1][roznicaWysokosci]);
		double podw10_2 = Math.min(alfaTable[4][a], T[1][roznicaWysokosci]);

		double obniz10_1 = Math.min(alfaTable[0][a], T[2][roznicaWysokosci]);
		double obniz5_3 = Math.min(alfaTable[1][a], T[2][roznicaWysokosci]);
		double rown_2 = Math.min(alfaTable[2][a], T[2][roznicaWysokosci]);
		double podw10_3 = Math.min(alfaTable[3][a], T[2][roznicaWysokosci]);
		double podw10_4 = Math.min(alfaTable[4][a], T[2][roznicaWysokosci]);

		double obniz10_2 = Math.min(alfaTable[0][a], T[3][roznicaWysokosci]);
		double obniz5_4 = Math.min(alfaTable[1][a], T[3][roznicaWysokosci]);
		double rown_3 = Math.min(alfaTable[2][a], T[3][roznicaWysokosci]);
		double podw10_5 = Math.min(alfaTable[3][a], T[3][roznicaWysokosci]);
		double podw10_6 = Math.min(alfaTable[4][a], T[3][roznicaWysokosci]);

		double obniz10_3 = Math.min(alfaTable[0][a], T[4][roznicaWysokosci]);
		double obniz5_5 = Math.min(alfaTable[1][a], T[4][roznicaWysokosci]);
		double rown_4 = Math.min(alfaTable[2][a], T[4][roznicaWysokosci]);
		double podw5_1 = Math.min(alfaTable[3][a], T[4][roznicaWysokosci]);
		double podw5_2 = Math.min(alfaTable[4][a], T[4][roznicaWysokosci]);

		double obniz10_4 = Math.min(alfaTable[0][a], T[5][roznicaWysokosci]);
		double obniz10_5 = Math.min(alfaTable[1][a], T[5][roznicaWysokosci]);
		double rown_5 = Math.min(alfaTable[2][a], T[5][roznicaWysokosci]);
		double podw5_3 = Math.min(alfaTable[3][a], T[5][roznicaWysokosci]);
		double podw5_4 = Math.min(alfaTable[4][a], T[5][roznicaWysokosci]);

		//max
		double maxObn10_12 = Math.max(obniz10_1, obniz10_2);
		double maxObn10_34 = Math.max(obniz10_3, obniz10_4);
		double maxObn10_12_34 = Math.max(maxObn10_12, maxObn10_34);

		double maxObn5_12 = Math.max(obniz5_1, obniz5_2);
		double maxObn5_34 = Math.max(obniz5_3, obniz5_4);
		double maxObn5_12_34 = Math.max(maxObn5_12, maxObn5_34);

		double maxRown_12 = Math.max(rown_1, rown_2);
		double maxRown_34 = Math.max(rown_3, rown_4);
		double maxRown_12_34 = Math.max(maxRown_12, maxRown_34);

		double maxPodw_12 = Math.max(podw5_1, podw5_2);
		double maxPodw_34 = Math.max(podw5_3, podw5_4);

		double maxPodw10_12 = Math.max(podw10_1, podw10_2);
		double maxPodw10_34 = Math.max(podw10_3, podw10_4);
		double maxPodw10_13 = Math.max(podw10_5, podw10_6);
		double maxPodw10_12_34 = Math.max(maxPodw10_12, maxPodw10_34);

		double maxObn10 = Math.max(obniz10_5, maxObn10_12_34);
		double maxObn5 = Math.max(obniz5_5, maxObn5_12_34);
		double maxRown = Math.max(rown_5, maxRown_12_34);
		double maxPodw5 = Math.max(maxPodw_12, maxPodw_34);
		double maxPodw10 = Math.max(maxPodw10_12_34, maxPodw10_13);

		double y1 = maxObn10 * (-10) + maxObn5 * (-5) + maxRown * 0 + maxPodw5 * 5 + maxPodw10 * 10;
		double y2 = maxObn10 + maxObn5 + maxRown + maxPodw5 + maxPodw10;
		double y = y1/y2;

		planeY += y;
		planeX += 1;
	}

	public int getPlaneX() {
		return planeX;
	}

	public int getPlaneY() {
		return planeY;
	}

	private void fillAlfaTable() {
		for (int i = 0; i <= 90; i++) {
			if (i <= 29) {
				alfaTable[0][i] = 1;
				alfaTable[1][i] = 0;
				alfaTable[2][i] = 0;
				alfaTable[3][i] = 0;
				alfaTable[4][i] = 0;
			} else if (i <= 34) {
				alfaTable[0][i] = alfaTable[0][i-1] - 0.2;
				alfaTable[1][i] = alfaTable[1][i-1] + 0.2;
				alfaTable[2][i] = 0;
				alfaTable[3][i] = 0;
				alfaTable[4][i] = 0;
			} else if (i <= 39) {
				alfaTable[0][i] = 0;
				alfaTable[1][i] = 1;
				alfaTable[2][i] = 0;
				alfaTable[3][i] = 0;
				alfaTable[4][i] = 0;
			} else if (i <= 43) {
				alfaTable[0][i] = 0;
				alfaTable[1][i] = alfaTable[1][i-1] - 0.2;
				alfaTable[2][i] = alfaTable[2][i-1] + 0.2;
				alfaTable[3][i] = 0;
				alfaTable[4][i] = 0;
			} else if (i <= 46) {
				alfaTable[0][i] = 0;
				alfaTable[1][i] = 0;
				alfaTable[2][i] = 1;
				alfaTable[3][i] = 0;
				alfaTable[4][i] = 0;
			} else if (i <= 51) {
				alfaTable[0][i] = 0;
				alfaTable[1][i] = 0;
				alfaTable[2][i] = alfaTable[2][i-1] - 0.2;
				alfaTable[3][i] = alfaTable[3][i-1] + 0.2;
				alfaTable[4][i] = 0;
			} else if (i <= 56) {
				alfaTable[0][i] = 0;
				alfaTable[1][i] = 0;
				alfaTable[2][i] = 0;
				alfaTable[3][i] = 1;
				alfaTable[4][i] = 0;
			} else if (i <= 61) {
				alfaTable[0][i] = 0;
				alfaTable[1][i] = 0;
				alfaTable[2][i] = 0;
				alfaTable[3][i] = alfaTable[3][i-1] - 0.2;
				alfaTable[4][i] = alfaTable[4][i-1] + 0.2;
			}  else {
				alfaTable[0][i] = 0;
				alfaTable[1][i] = 0;
				alfaTable[2][i] = 0;
				alfaTable[3][i] = 0;
				alfaTable[4][i] = 1;
			}
		}
	}

	private void fillDeltaH() {
		for (int i = 0; i <= 200; i++) {
			if (i/2 <= 20) {
				T[1][i] = 1;
				T[2][i] = 0;
				T[3][i] = 0;
				T[4][i] = 0;
				T[5][i] = 0;
			} else if (i/2 <= 35) {
				T[1][i] = T[1][i - 1] - 0.1;
				T[2][i] = T[2][i - 1] + 0.1;
				T[3][i] = 1;
				T[4][i] = 0;
				T[5][i] = 0;
			} else if (i/2 <= 40) {
				T[1][i] = 0;
				T[2][i] = 1;
				T[3][i] = 0;
				T[4][i] = 0;
				T[5][i] = 0;
			} else if (i/2 <= 45) {
				T[1][i] = 0;
				T[2][i] = T[2][i - 1] - 0.1;
				T[3][i] = T[3][i - 1] + 0.1;
				T[4][i] = 0;
				T[5][i] = 0;
			} else if (i/2 <= 50) {
				T[1][i] = 0;
				T[2][i] = 0;
				T[3][i] = 1;
				T[4][i] = 0;
				T[5][i] = 0;
			} else if (i/2 <= 55) {
				T[1][i] = 0;
				T[2][i] = 0;
				T[3][i] = T[3][i - 1] - 0.1;
				T[4][i] = T[4][i - 1] + 0.1;
				T[5][i] = 0;
			} else if (i/2 <= 60) {
				T[1][i] = 0;
				T[2][i] = 0;
				T[3][i] = 0;
				T[4][i] = 1;
				T[5][i] = 0;
			} else if (i/2 <= 65) {
				T[1][i] = 0;
				T[2][i] = 0;
				T[3][i] = 0;
				T[4][i] = T[4][i - 1] - 0.1;
				T[5][i] = T[5][i - 1] + 0.1;
			} else {
				T[1][i] = 0;
				T[2][i] = 0;
				T[3][i] = 0;
				T[4][i] = 0;
				T[5][i] = 1;
			}
		}
	}
	private int findAngle(String x) {
		double x_2 = limitPrecision(x, 2);
		for (int i = 1; i <= 90; i++) {
			double radians = Math.toRadians(i);
			double val = limitPrecision(String.valueOf(Math.sin(radians)), 2);
			if (x_2 == val) {
				return i;
			} else if (x_2 == (val - 0.01)) {
				return i;
			} else if (x_2 == (val + 0.01)) {
				return i;
			}
		}
		return 0;
	}

	public double limitPrecision(String dblAsString, int maxDigitsAfterDecimal) {
		int multiplier = (int) Math.pow(10, maxDigitsAfterDecimal);
		double truncated = (double) ((long) ((Double.parseDouble(dblAsString)) * multiplier)) / multiplier;
		return truncated;
	}
}
