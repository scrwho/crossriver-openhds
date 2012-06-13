package org.openhds.extensions;

import org.openhds.domain.util.CalendarAdapter;
import com.sun.codemodel.JAnnotationArrayMember;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JDocComment;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JVar;

public class PostNeoNatalVPMTemplateBuilder implements ExtensionTemplate {
	
	JCodeModel jCodeModel;
	boolean templateBuilt = false;
		
	PostNeoNatalVPMTemplateBuilder(JCodeModel jCodeModel) {
		this.jCodeModel = jCodeModel;
	}

	public void buildTemplate(JDefinedClass jc) {
		JDocComment jDocComment = jc.javadoc();
		jDocComment.add("Generated by JCodeModel");
		
		jc._extends(org.openhds.domain.model.AuditableCollectedEntity.class);
		jc._implements(java.io.Serializable.class);
		
		buildClassAnnotations(jc);
		buildFieldsAndMethods(jc);
		
		templateBuilt = true;	
	}

	public void buildFieldsAndMethods(JDefinedClass jc) {
		
		// serial uuid
		JFieldVar jfSerial = jc.field(JMod.PUBLIC | JMod.STATIC | JMod.FINAL, long.class, "serialVersionUID");
		jfSerial.init(JExpr.lit(7646636209213391150L));
		
		// individual
		JFieldVar jfIndividual = jc.field(JMod.PRIVATE , org.openhds.domain.model.Individual.class, "individual");
		jfIndividual.annotate(org.openhds.domain.constraint.Searchable.class);
		jfIndividual.annotate(org.openhds.domain.constraint.CheckEntityNotVoided.class);
		jfIndividual.annotate(org.openhds.domain.constraint.CheckIndividualNotUnknown.class);
		JAnnotationUse jfMotherCascade = jfIndividual.annotate(javax.persistence.ManyToOne.class);
		JAnnotationArrayMember motherArray = jfMotherCascade.paramArray("cascade");
		motherArray.param(javax.persistence.CascadeType.ALL);	
		JAnnotationUse jaMotherDesc = jfIndividual.annotate(org.openhds.domain.annotations.Description.class);
		jaMotherDesc.param("description", "Individual who deceased, identified by the external id.");
		
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
		JFieldVar jfLocation = jc.field(JMod.PRIVATE , org.openhds.domain.model.Location.class, "house");
		jfLocation.annotate(org.openhds.domain.constraint.Searchable.class);
		jfLocation.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaLocationDesc = jfLocation.annotate(org.openhds.domain.annotations.Description.class);
		jaLocationDesc.param("description", "Location of the deceased individual.");
		
		// getter
		JMethod jmgLocation = jc.method(JMod.PUBLIC, org.openhds.domain.model.Location.class, "getHouse");
		JBlock jmgLocationBlock = jmgLocation.body();
		jmgLocationBlock._return(jfLocation);
		
		// setter
		JMethod jmsLocation = jc.method(JMod.PUBLIC, void.class, "setHouse");
		JVar jvarLocation = jmsLocation.param(org.openhds.domain.model.Location.class, "location");
		JBlock jmsLocationBlock = jmsLocation.body();
		jmsLocationBlock.assign(jfLocation, jvarLocation);
		
		// visit
		JFieldVar jfVisit = jc.field(JMod.PRIVATE , org.openhds.domain.model.Visit.class, "visit");
		jfVisit.annotate(javax.persistence.ManyToOne.class);
		JAnnotationUse jaVisitDesc = jfVisit.annotate(org.openhds.domain.annotations.Description.class);
		jaVisitDesc.param("description", "Visit that is associated with the verbal autopsy, identified by the external id.");
		
		// getter
		JMethod jmgVisit = jc.method(JMod.PUBLIC, org.openhds.domain.model.Visit.class, "getVisit");
		JBlock jmgVisitBlock = jmgVisit.body();
		jmgVisitBlock._return(jfVisit);
		
		// setter
		JMethod jmsVisit = jc.method(JMod.PUBLIC, void.class, "setVisit");
		JVar jvarVisit = jmsVisit.param(org.openhds.domain.model.Visit.class, "vis");
		JBlock jmsVisitBlock = jmsVisit.body();
		jmsVisitBlock.assign(jfVisit, jvarVisit);
		
		// childDeathDate
		JFieldVar jfDeathDate = jc.field(JMod.PRIVATE , java.util.Calendar.class, "childDeathDate");
		JAnnotationUse jaPast = jfDeathDate.annotate(javax.validation.constraints.Past.class);
		jaPast.param("message", "Death date should be in the past");
		JAnnotationUse jaTemporal = jfDeathDate.annotate(javax.persistence.Temporal.class);
		jaTemporal.param("value", javax.persistence.TemporalType.DATE);
		JAnnotationUse jaDeathDateDesc = jfDeathDate.annotate(org.openhds.domain.annotations.Description.class);
		jaDeathDateDesc.param("description", "Date of the Death.");
		
		// getter
		JMethod jmgDeathDate = jc.method(JMod.PUBLIC, java.util.Calendar.class, "getChildDeathDate");
		JAnnotationUse jaXmlDeathDate = jmgDeathDate.annotate(javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.class);
		jaXmlDeathDate.param("value", CalendarAdapter.class);
		JBlock jmgDeathDateBlock = jmgDeathDate.body();
		jmgDeathDateBlock._return(jfDeathDate);
		
		// setter
		JMethod jmsDeathDate = jc.method(JMod.PUBLIC, void.class, "setChildDeathDate");
		JVar jvarDeathDate = jmsDeathDate.param(java.util.Calendar.class, "date");
		JBlock jmsDeathDateBlock = jmsDeathDate.body();
		jmsDeathDateBlock.assign(jfDeathDate, jvarDeathDate);
		
	}

	@Override
	public void buildClassAnnotations(JDefinedClass jc) {
		
		// create Description annotation
		JAnnotationUse jad = jc.annotate(org.openhds.domain.annotations.Description.class);
		jad.param("description", "Standard Verbal Autopsy Questionnaire PostNeonatal Deaths (28 days to <12 years)");
		
		// create Entity annotation
		jc.annotate(javax.persistence.Entity.class);
		
		JAnnotationUse jat = jc.annotate(javax.persistence.Table.class);
		jat.param("name", "postneonatalvpm");
		
		JAnnotationUse jxmlRoot = jc.annotate(javax.xml.bind.annotation.XmlRootElement.class);
		jxmlRoot.param("name", "postneonatalvpm");
	}
}