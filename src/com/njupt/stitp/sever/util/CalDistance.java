package com.njupt.stitp.sever.util;

public class CalDistance {

	public static double calDistance(Double a_x_point, Double a_y_point,
			Double b_x_point, Double b_y_point) {
		Double R = new Double(6371);
		Double dlat = (b_x_point - a_x_point) * Math.PI / 180;
		Double dlon = (b_y_point - a_y_point) * Math.PI / 180;
		Double aDouble = Math.sin(dlat / 2) * Math.sin(dlat / 2)
				+ Math.cos(a_x_point * Math.PI / 180)
				* Math.cos(b_x_point * Math.PI / 180) * Math.sin(dlon / 2)
				* Math.sin(dlon / 2);
		Double cDouble = 2 * Math.atan2(Math.sqrt(aDouble),
				Math.sqrt(1 - aDouble));
		double d = R * cDouble * 1000;
		return d;
	}
}
