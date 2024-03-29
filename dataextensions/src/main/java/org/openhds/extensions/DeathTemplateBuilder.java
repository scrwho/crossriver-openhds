package org.openhds.extensions;

import org.openhds.domain.util.CalendarAdapter;
import com.sun.codemodel.JAnnotationArrayMember;
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

public class DeathTemplateBuilder implements ExtensionTemplate {
	
	JCodeModel jCodeModel;
	boolean templateBuilt = false;
	
	DeathTemplateBuilder(JCodeModel jCodeModel) {
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
		jfSerial.init(JExpr.lit(-6644256636909420061L));
					
		// individual
		JFieldVar jfIndividual = jc.field(JMod.PRIVATE , org.openhds.domain.model.Individual.class, "individual");
		JClass jIndividualClassRef = jCodeModel.ref(org.openhds.domain.model.Individual.class);
		jfIndividual.init(JExpr._new(jIndividualClassRef));	
		jfIndividual.annotate(org.openhds.domain.constraint.Searchable.class);
		jfIndividual.annotate(org.openhds.domain.constraint.CheckEntityNotVoided.class);
		jfIndividual.annotate(org.openhds.domain.constraint.CheckIndividualNotUnknown.class);
		JAnnotationUse jfIndividualCascade = jfIndividual.annotate(javax.persistence.ManyToOne.class);
		JAnnotationArrayMember cascadeArray = jfIndividualCascade.paramArray("cascade");
		cascadeArray.param(javax.persistence.CascadeType.MERGE);
		cascadeArray.param(javax.persistence.CascadeType.PERSIST);
		JAnnotationUse jaGroupHeadDesc = jfIndividual.annotate(org.openhds.domain.annotations.Description.class);
		jaGroupHeadDesc.param("description", "Individual who has died, identified by external id.");
		
		// getter
		JMethod jmgIndividual = jc.method(JMod.PUBLIC, org.openhds.domain.model.Individual.class, "getIndividual");
		JBlock jmgIndividualBlock = jmgIndividual.body();
		jmgIndividualBlock._return(jfIndividual);
		
		// setter
		JMethod jmsIndividual = jc.method(JMod.PUBLIC, void.class, "setIndividual");
		JVar jvarIndividual = jmsIndividual.param(org.openhds.domain.model.Individual.class, "indiv");
		JBlock jmsIndividualBlock = jmsIndividual.body();
		jmsIndividualBlock.assign(jfIndividual, jvarIndividual);
		
		// house
		JFieldVar jfHouse = jc.field(JMod.PRIVATE , org.openhds.domain.model.Location.class, "house");
		jfHouse.annotate(org.openhds.domain.constraint.Searchable.class);
		jfHouse.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaHouseDesc = jfHouse.annotate(org.openhds.domain.annotations.Description.class);
		jaHouseDesc.param("description", "House in which this Death took place.");
		
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
		JClass jHouseholdClassRef = jCodeModel.ref(org.openhds.domain.model.SocialGroup.class);
		jfHousehold.init(JExpr._new(jHouseholdClassRef));	
		jfHousehold.annotate(org.openhds.domain.constraint.Searchable.class);
		jfHousehold.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaHouseholdDesc = jfHousehold.annotate(org.openhds.domain.annotations.Description.class);
		jaHouseholdDesc.param("description", "Household in which this Death took place.");
		
		// getter
		JMethod jmgHousehold = jc.method(JMod.PUBLIC, org.openhds.domain.model.SocialGroup.class, "getHousehold");
		JBlock jmgHouseholdBlock = jmgHousehold.body();
		jmgHouseholdBlock._return(jfHousehold);
		
		// setter
		JMethod jmsHousehold = jc.method(JMod.PUBLIC, void.class, "setHousehold");
		JVar jvarHousehold = jmsHousehold.param(org.openhds.domain.model.SocialGroup.class, "place");
		JBlock jmsHouseholdBlock = jmsHousehold.body();
		jmsHouseholdBlock.assign(jfHousehold, jvarHousehold);
				
		// deathPlace
		JFieldVar jfDeathPlace = jc.field(JMod.PRIVATE , java.lang.Integer.class, "deathPlace");
		jfDeathPlace.annotate(org.openhds.domain.constraint.Searchable.class);
		JAnnotationUse jaDeathPlaceDesc = jfDeathPlace.annotate(org.openhds.domain.annotations.Description.class);
		JAnnotationUse jaDeathPlace = jfDeathPlace.annotate(org.openhds.domain.constraint.ExtensionIntegerConstraint.class);
		jaDeathPlace.param("constraint", "placeOfDeathConstraint");
		jaDeathPlace.param("message", "Invalid Value for deathPlace");
		jaDeathPlace.param("allowNull", true);
		jaDeathPlaceDesc.param("description", "Place where the death occurred.");
		
		// getter
		JMethod jmgDeathPlace = jc.method(JMod.PUBLIC, java.lang.Integer.class, "getDeathPlace");
		JBlock jmgDeathPlaceBlock = jmgDeathPlace.body();
		jmgDeathPlaceBlock._return(jfDeathPlace);
		
		// setter
		JMethod jmsDeathPlace = jc.method(JMod.PUBLIC, void.class, "setDeathPlace");
		JVar jvarDeathPlace = jmsDeathPlace.param(java.lang.Integer.class, "place");
		JBlock jmsDeathPlaceBlock = jmsDeathPlace.body();
		jmsDeathPlaceBlock.assign(jfDeathPlace, jvarDeathPlace);
				
		// deathDate
		JFieldVar jfDeathDate = jc.field(JMod.PRIVATE , java.util.Calendar.class, "deathDate");
		JAnnotationUse jaDeathDateCalendar = jfDeathDate.annotate(org.openhds.domain.constraint.CheckCalendar.class);
		jaDeathDateCalendar.param("message", "Death date is invalid");
		JAnnotationUse jaDeathDate = jfDeathDate.annotate(javax.validation.constraints.NotNull.class);
		jaDeathDate.param("message", "You must provide a Death date");
		JAnnotationUse jaPast = jfDeathDate.annotate(javax.validation.constraints.Past.class);
		jaPast.param("message", "Death date should be in the past");
		JAnnotationUse jaTemporal = jfDeathDate.annotate(javax.persistence.Temporal.class);
		jaTemporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaDeathDateDesc = jfDeathDate.annotate(org.openhds.domain.annotations.Description.class);
		jaDeathDateDesc.param("description", "Date of the Death.");
		
		// getter
		JMethod jmgDeathDate = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getDeathDate");
		JAnnotationUse jaXmlDeathDate = jmgDeathDate.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlDeathDate.param("value", CalendarAdapter.class);
		JBlock jmgDeathDateBlock = jmgDeathDate.body();
		jmgDeathDateBlock._return(jfDeathDate);
		
		// setter
		JMethod jmsDeathDate = jc.method(JMod.PUBLIC, void.class, "setDeathDate");
		JVar jvarDeathDate = jmsDeathDate.param(java.util.Calendar.class, "date");
		JBlock jmsDeathDateBlock = jmsDeathDate.body();
		jmsDeathDateBlock.assign(jfDeathDate, jvarDeathDate);
		
		// ageAtDeath
		JFieldVar jfAge = jc.field(JMod.PRIVATE , java.lang.Long.class, "ageAtDeath");
		JAnnotationUse jaAgeDesc = jfAge.annotate(org.openhds.domain.annotations.Description.class);
		jaAgeDesc.param("description", "Age of death in number of data.");
		
		// getter
		JMethod jmgAge = jc.method(JMod.PUBLIC, java.lang.Long.class, "getAgeAtDeath");
		JBlock jmgAgeBlock = jmgAge.body();
		jmgAgeBlock._return(jfAge);
		
		// setter
		JMethod jmsAge = jc.method(JMod.PUBLIC, void.class, "setAgeAtDeath");
		JVar jvarAge = jmsAge.param(java.lang.Long.class, "age");
		JBlock jmsAgeBlock = jmsAge.body();
		jmsAgeBlock.assign(jfAge, jvarAge);
		
		// toString
		JMethod toString = jc.method(JMod.PUBLIC, java.lang.String.class, "toString");
		toString.annotate(Override.class);
		JBlock toStringBlock = toString.body();
		toStringBlock._return(JExpr.lit("Death"));
	}

	@Override
	public void buildClassAnnotations(JDefinedClass jc) {
		
		// create Description annotation
		JAnnotationUse jad = jc.annotate(org.openhds.domain.annotations.Description.class);
		jad.param("description", "A Death represents the final event than an Individual can " +
		"have within the system. It consists of the Individual who has passed on, the " +
		"Visit associated with the Death, as well as descriptive information about the " +
		"occurrence, cause, and date of the death. If the Individual had any Residencies, " +
		"Relationships, or Memberships then they will become closed.");
				
		jc.annotate(org.openhds.domain.constraint.CheckDeathDateGreaterThanBirthDate.class);
		
		// create Entity annotation
		jc.annotate(javax.persistence.Entity.class);
		
		JAnnotationUse jat = jc.annotate(javax.persistence.Table.class);
		jat.param("name", "death");
		
		JAnnotationUse jxmlRoot = jc.annotate(javax.xml.bind.annotation.XmlRootElement.class);
		jxmlRoot.param("name", "death");
	}
}
