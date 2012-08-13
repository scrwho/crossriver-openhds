package org.openhds.extensions;

import org.openhds.domain.util.CalendarAdapter;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class OutMigrationTemplateBuilder implements ExtensionTemplate {

	JCodeModel jCodeModel;
	boolean templateBuilt = false;
		
	OutMigrationTemplateBuilder(JCodeModel jCodeModel) {
		this.jCodeModel = jCodeModel;
	}
	
	@Override
	public void buildTemplate(JDefinedClass jc) {
		
		JDocComment jDocComment = jc.javadoc();
		jDocComment.add("Generated by JCodeModel");
		
		jc._extends(org.openhds.domain.model.VisitableEntity.class);
		jc._implements(java.io.Serializable.class);
		
		buildClassAnnotations(jc);	
		buildFieldsAndMethods(jc);
		
		templateBuilt = true;	
	}

	@Override
	public void buildFieldsAndMethods(JDefinedClass jc) {
		
		// serial uuid
		JFieldVar jfSerial = jc.field(JMod.PUBLIC | JMod.STATIC | JMod.FINAL, long.class, "serialVersionUID");
		jfSerial.init(JExpr.lit(6736599408170070468L));
		
		// individual		
		JFieldVar jfIndividual = jc.field(JMod.PRIVATE , org.openhds.domain.model.Individual.class, "individual");
		JClass jIndividualClassRef = jCodeModel.ref(org.openhds.domain.model.Individual.class);
		jfIndividual.init(JExpr._new(jIndividualClassRef));	
		jfIndividual.annotate(javax.validation.constraints.NotNull.class);
		jfIndividual.annotate(org.openhds.domain.constraint.Searchable.class);
		jfIndividual.annotate(org.openhds.domain.constraint.CheckEntityNotVoided.class);
		jfIndividual.annotate(javax.persistence.ManyToOne.class);
		jfIndividual.annotate(org.openhds.domain.constraint.CheckIndividualNotUnknown.class);
		JAnnotationUse jaIndividualDesc = jfIndividual.annotate(org.openhds.domain.annotations.Description.class);
		jaIndividualDesc.param("description", "Individual who is outmigrating, identified by external id.");
		
		// getter
		JMethod jmgIndividual = jc.method(JMod.PUBLIC, org.openhds.domain.model.Individual.class, "getIndividual");
		JBlock jmgIndividualBlock = jmgIndividual.body();
		jmgIndividualBlock._return(jfIndividual);
		
		// setter
		JMethod jmsIndividual = jc.method(JMod.PUBLIC, void.class, "setIndividual");
		JVar jvarIndividual = jmsIndividual.param(org.openhds.domain.model.Individual.class, "indiv");
		JBlock jmsIndividualBlock = jmsIndividual.body();
		jmsIndividualBlock.assign(jfIndividual, jvarIndividual);
		
		// residency
		JFieldVar jfResidency = jc.field(JMod.PRIVATE , org.openhds.domain.model.Residency.class, "residency");	
		jfResidency.annotate(javax.persistence.OneToOne.class);
		jfResidency.annotate(javax.validation.constraints.NotNull.class);
		JAnnotationUse jaResidencyDesc = jfResidency.annotate(org.openhds.domain.annotations.Description.class);
		jaResidencyDesc.param("description", "The residency the individual is outmigrating to.");
		
		// getter
		JMethod jmgResidency = jc.method(JMod.PUBLIC, org.openhds.domain.model.Residency.class, "getResidency");
		JBlock jmgResidencyBlock = jmgResidency.body();
		jmgResidencyBlock._return(jfResidency);
		
		// setter
		JMethod jmsResidency = jc.method(JMod.PUBLIC, void.class, "setResidency");
		JVar jvarResidency = jmsResidency.param(org.openhds.domain.model.Residency.class, "res");
		JBlock jmsResidencyBlock = jmsResidency.body();
		jmsResidencyBlock.assign(jfResidency, jvarResidency);
		
		// house
		JFieldVar jfHouse = jc.field(JMod.PRIVATE , org.openhds.domain.model.Location.class, "house");
		jfHouse.annotate(org.openhds.domain.constraint.Searchable.class);
		jfHouse.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaHouseDesc = jfHouse.annotate(org.openhds.domain.annotations.Description.class);
		jaHouseDesc.param("description", "Moving away from house.");
		
		// getter
		JMethod jmgHouse = jc.method(JMod.PUBLIC, org.openhds.domain.model.Location.class, "getHouse");
		JBlock jmgHouseBlock = jmgHouse.body();
		jmgHouseBlock._return(jfHouse);
		
		// setter
		JMethod jmsHouse = jc.method(JMod.PUBLIC, void.class, "setHouse");
		JVar jvarHouse = jmsHouse.param(org.openhds.domain.model.Location.class, "place");
		JBlock jmsHouseBlock = jmsHouse.body();
		jmsHouseBlock.assign(jfHouse, jvarHouse);
		
		// household
		JFieldVar jfHousehold = jc.field(JMod.PRIVATE , org.openhds.domain.model.SocialGroup.class, "household");
		jfHousehold.annotate(org.openhds.domain.constraint.Searchable.class);
		jfHousehold.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaHouseholdDesc = jfHousehold.annotate(org.openhds.domain.annotations.Description.class);
		jaHouseholdDesc.param("description", "Moving away from household.");
		
		// getter
		JMethod jmgHousehold = jc.method(JMod.PUBLIC, org.openhds.domain.model.SocialGroup.class, "getHousehold");
		JBlock jmgHouseholdBlock = jmgHousehold.body();
		jmgHouseholdBlock._return(jfHousehold);
		
		// setter
		JMethod jmsHousehold = jc.method(JMod.PUBLIC, void.class, "setHousehold");
		JVar jvarHousehold = jmsHousehold.param(org.openhds.domain.model.SocialGroup.class, "place");
		JBlock jmsHouseholdBlock = jmsHousehold.body();
		jmsHouseholdBlock.assign(jfHousehold, jvarHousehold);
		
		// origin
		JFieldVar jfOrigin = jc.field(JMod.PRIVATE , java.lang.String.class, "origin");
		jfOrigin.annotate(org.openhds.domain.constraint.Searchable.class);
		JAnnotationUse jaOriginDesc = jfOrigin.annotate(org.openhds.domain.annotations.Description.class);
		jaOriginDesc.param("description", "Destination of the outmigration.");
		
		// getter
		JMethod jmgOrigin = jc.method(JMod.PUBLIC, java.lang.String.class, "getOrigin");
		JBlock jmgOriginBlock = jmgOrigin.body();
		jmgOriginBlock._return(jfOrigin);
		
		// setter
		JMethod jmsOrigin = jc.method(JMod.PUBLIC, void.class, "setOrigin");
		JVar jvarOrigin = jmsOrigin.param(java.lang.String.class, "name");
		JBlock jmsOriginBlock = jmsOrigin.body();
		jmsOriginBlock.assign(jfOrigin, jvarOrigin);
		
		// reason
		JFieldVar jfReason = jc.field(JMod.PRIVATE , java.lang.Integer.class, "reason");
		jfReason.annotate(org.openhds.domain.constraint.Searchable.class);
		JAnnotationUse jaReason = jfReason.annotate(org.openhds.domain.constraint.ExtensionIntegerConstraint.class);
		jaReason.param("constraint", "reasonForOutmigrationConstraint");
		jaReason.param("message", "Invalid Value for reason");
		jaReason.param("allowNull", true);
		JAnnotationUse jaReasonDesc = jfReason.annotate(org.openhds.domain.annotations.Description.class);
		jaReasonDesc.param("description", "Reason for outmigrating.");
		
		// getter
		JMethod jmgReason = jc.method(JMod.PUBLIC, java.lang.Integer.class, "getReason");
		JBlock jmgReasonBlock = jmgReason.body();
		jmgReasonBlock._return(jfReason);
		
		// setter
		JMethod jmsReason = jc.method(JMod.PUBLIC, void.class, "setReason");
		JVar jvarReason = jmsReason.param(java.lang.Integer.class, "name");
		JBlock jmsReasonBlock = jmsReason.body();
		jmsReasonBlock.assign(jfReason, jvarReason);
		
		// recordedDate
		JFieldVar jfRecordedDate = jc.field(JMod.PRIVATE , java.util.Calendar.class, "recordedDate");
		jfRecordedDate.annotate(javax.validation.constraints.NotNull.class);
		JAnnotationUse jaRecordedDateCalendar = jfRecordedDate.annotate(org.openhds.domain.constraint.CheckCalendar.class);
		jaRecordedDateCalendar.param("message", "Recorded Date is invalid");
		JAnnotationUse jaRecordedDateTemporal = jfRecordedDate.annotate(javax.persistence.Temporal.class);
		jaRecordedDateTemporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaRecordedDatePast = jfRecordedDate.annotate(javax.validation.constraints.Past.class);
		jaRecordedDatePast.param("message", "The date of the migration must be in the past");
		JAnnotationUse jaRecordedDateDesc = jfRecordedDate.annotate(org.openhds.domain.annotations.Description.class);
		jaRecordedDateDesc.param("description", "Recorded date of the inmigration.");
		
		// getter
		JMethod jmgRecordedDate = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getRecordedDate");
		JAnnotationUse jaXmlRecordedDate = jmgRecordedDate.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlRecordedDate.param("value", CalendarAdapter.class);
		JBlock jmgRecordedDateBlock = jmgRecordedDate.body();
		jmgRecordedDateBlock._return(jfRecordedDate);
		
		// setter
		JMethod jmsRecordedDate = jc.method(JMod.PUBLIC, void.class, "setRecordedDate");
		JVar jvarRecordedDate = jmsRecordedDate.param(java.util.Calendar.class, "date");
		JBlock jmsRecordedDateBlock = jmsRecordedDate.body();
		jmsRecordedDateBlock.assign(jfRecordedDate, jvarRecordedDate);
		
		// toString
		JMethod toString = jc.method(JMod.PUBLIC, java.lang.String.class, "toString");
		toString.annotate(Override.class);
		JBlock toStringBlock = toString.body();
		toStringBlock._return(JExpr.lit("Out Migration"));
	}

	@Override
	public void buildClassAnnotations(JDefinedClass jc) {
		
		// create Description annotation
		JAnnotationUse jad = jc.annotate(org.openhds.domain.annotations.Description.class);
		jad.param("description", "An OutMigration represents a migration out of the study area." +
				"It contains information about the Individual who is out-migrating to a particular" +
				"Residency. It also contains information about the destination, date, and reason the" +
				"Individual is migrating as well as the Visit associated with the migration.");
				
		// create Entity annotation
		jc.annotate(javax.persistence.Entity.class);
				
		JAnnotationUse jat = jc.annotate(javax.persistence.Table.class);
		jat.param("name", "outmigration");
		
		JAnnotationUse jxmlRoot = jc.annotate(javax.xml.bind.annotation.XmlRootElement.class);
		jxmlRoot.param("name", "outmigration");	
	}
}
