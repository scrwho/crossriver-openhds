package org.openhds.domain.constaint.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.openhds.domain.constraint.AppContextAware;
import org.openhds.domain.constraint.ExtensionStringConstraint;
import org.openhds.domain.service.impl.ValueConstraintServiceImpl;

public class ExtensionStringConstraintImpl extends AppContextAware implements ConstraintValidator<ExtensionStringConstraint, String> {

	private String constraint;
	private boolean allowNull;
	private ValueConstraintServiceImpl service;

	public void initialize(ExtensionStringConstraint arg0) {
		service = (ValueConstraintServiceImpl)context.getBean("valueConstraintService");
		this.constraint = arg0.constraint();
		this.allowNull = arg0.allowNull();
	}

	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		
		if (arg0 == null)
			return true;
		
		if (allowNull == true && arg0.equals(""))
			return true;
		
		return service.isValidConstraintValue(constraint, arg0);
	}
}

