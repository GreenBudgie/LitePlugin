package ru.util;

public enum Case {

	NOMINATIVE, GENITIVE, DATIVE, ACCUSATIVE, INSTRUMENTAL, PREPOSITIONAL;

	public static Case byOrdinal(int n) {
		for(Case c : Case.values()) {
			if(c.ordinal() == n) return c;
		}
		return null;
	}
	
}
