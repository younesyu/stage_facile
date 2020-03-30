package com.stage_facile.stage_facile.models;

public enum EReview {
	REVIEW_TERRIBLE,
	REVIEW_POOR,
	REVIEW_AVERAGE,
	REVIEW_GOOD,
	REVIEW_EXCELLENT;

	public static EReview parseInt(int i) {
		switch(i) {
		case 1:
			return REVIEW_TERRIBLE;
		case 2:
			return REVIEW_POOR;
		case 3:
			return REVIEW_AVERAGE;
		case 4:
			return REVIEW_GOOD;
		case 5:
			return REVIEW_EXCELLENT;
		default:
			return null;
		}
	}
}
