package a6tests;

import java.lang.reflect.Field;

public class DisallowedFieldException extends RuntimeException {

	public DisallowedFieldException(Class type) {
		super("Cannot have this field of this type (" + type.getSimpleName() +  ") encapsulated" );
	}
	
}

class ExposedAccessException extends RuntimeException {
	
	public ExposedAccessException(Field field) {
		super("Field must be properly encapsulated (" + field.getName() + ")");
	}
}
