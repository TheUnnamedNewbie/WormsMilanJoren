package worms.programs.types;

public enum TypeType {
	BOOLEAN, DOUBLE, ENTITY;
	
	public static TypeType getCorrespondingType(String stringedType) {
		if (stringedType == "DoubleLiteral") {
			return DOUBLE;
		} else if (stringedType == "BooleanLiteral") {
			return 	BOOLEAN;
		} else if (stringedType == "EntityLiteral") {
			return ENTITY;
		} else return null; //Invalid type request, should throw error. But hey, internal method so no thx
	}
}
