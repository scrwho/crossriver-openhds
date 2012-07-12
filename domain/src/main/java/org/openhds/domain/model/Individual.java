
package org.openhds.domain.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openhds.domain.annotations.Description;
import org.openhds.domain.constraint.CheckCalendar;
import org.openhds.domain.constraint.CheckEntityNotVoided;
import org.openhds.domain.constraint.CheckFieldNotBlank;
import org.openhds.domain.constraint.CheckIndividualGenderFemale;
import org.openhds.domain.constraint.CheckIndividualGenderMale;
import org.openhds.domain.constraint.CheckIndividualParentAge;
import org.openhds.domain.constraint.CheckMotherFatherNotIndividual;
import org.openhds.domain.constraint.CheckWorkStatusAndOccupation;
import org.openhds.domain.constraint.ExtensionIntegerConstraint;
import org.openhds.domain.constraint.ExtensionStringConstraint;
import org.openhds.domain.constraint.Searchable;


/**
 * Generated by JCodeModel
 * 
 */
@Description(description = "An Individual represents one who is a part of the study. Each Individual is identified by a uniquely generated external identifier which the system uses internally. Information about the Individual such as name, gender, date of birth, and parents are stored here. An Individual may be associated with many Residencies, Relationships, and Memberships.")
@CheckMotherFatherNotIndividual
@CheckWorkStatusAndOccupation
@Entity
@Table(name = "individual")
@XmlRootElement(name = "individual")
public class Individual
    extends AuditableCollectedEntity
    implements Serializable
{

    public final static long serialVersionUID = 9058114832143454609L;
    @Searchable
    @Description(description = "External Id of the individual. This id is used internally.")
    private String extId;
    @CheckFieldNotBlank(message = "First name cannot be blank")
    @Searchable
    @Description(description = "First name of the individual.")
    private String firstName;
    @Searchable
    @Description(description = "Middle name of the individual.")
    private String middleName;
    @CheckFieldNotBlank(message = "First name cannot be blank")
    @Searchable
    @Description(description = "Last name of the individual.")
    private String lastName;
    @ExtensionStringConstraint(constraint = "genderConstraint", message = "Invalid Value for gender", allowNull = true)
    @Description(description = "The gender of the individual.")
    private String gender;
    @Past(message = "Date of birth must a date in the past")
    @CheckCalendar(message = "Date of birth is invalid")
    @Temporal(TemporalType.DATE)
    @Description(description = "Birth date of the individual.")
    private Calendar dob;
    @CheckIndividualGenderFemale(allowNull = true, message = "The mother specified is not female gender")
    @CheckIndividualParentAge(allowNull = true, message = "The mother is younger than the minimum age required in order to be a parent")
    @CheckEntityNotVoided(allowNull = true, message = "The mother has been voided")
    @ManyToOne(cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST
    }, targetEntity = org.openhds.domain.model.Individual.class)
    @Description(description = "The individual's mother, identified by the external id.")
    private org.openhds.domain.model.Individual mother;
    @CheckIndividualGenderMale(allowNull = true, message = "The father specified is not male gender")
    @CheckIndividualParentAge(allowNull = true, message = "The father is younger than the minimum age required in order to be a parent")
    @CheckEntityNotVoided(allowNull = true, message = "The father has been voided")
    @ManyToOne(cascade = {
        CascadeType.MERGE,
        CascadeType.PERSIST
    }, targetEntity = org.openhds.domain.model.Individual.class)
    @Description(description = "The individual's father, identified by the external id.")
    private org.openhds.domain.model.Individual father;
    @ExtensionStringConstraint(constraint = "dobAspectConstraint", message = "Invalid Value for partial date", allowNull = true)
    @Description(description = "Identifer for determining if the birth date is partially known.")
    private String dobAspect;
    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL)
    @OrderBy("startDate")
    @Description(description = "The set of all residencies that the individual may have.")
    private Set<Residency> allResidencies = new HashSet<Residency>();
    @OneToMany(mappedBy = "individualA", cascade = CascadeType.ALL)
    @Description(description = "The set of all relationships that the individual may have with another individual.")
    private Set<Relationship> allRelationships1 = new HashSet<Relationship>();
    @OneToMany(mappedBy = "individualB", cascade = CascadeType.ALL)
    @Description(description = "The set of all relationships where another individual may have with this individual.")
    private Set<Relationship> allRelationships2 = new HashSet<Relationship>();
    @OneToMany(mappedBy = "individual", cascade = CascadeType.ALL)
    @Description(description = "The set of all memberships the individual is participating in.")
    private Set<Membership> allMemberships = new HashSet<Membership>();
    @Description(description = "Specify the occupational status of the individual")
    private String occupationalStatusOther;
    @Description(description = "Specify the educational status of the individual")
    private String educationalStatusOther;
    @Description(description = "Educational Status of individual")
    @ExtensionIntegerConstraint(constraint = "educationalStatusConstraint", message = "Invalid Value for educationalStatus", allowNull = true)
    private Integer educationalStatus;
    @Description(description = "The work status of the individual")
    @ExtensionIntegerConstraint(constraint = "workStatusConstraint", message = "Invalid Value for workStatus", allowNull = true)
    private Integer workStatus;
    @Description(description = "Occupational status of the individual")
    @ExtensionIntegerConstraint(constraint = "occupationalStatusConstraint", message = "Invalid Value for occupationalStatus", allowNull = true)
    private Integer occupationalStatus;
    @Description(description = "Marital status of the individual")
    @ExtensionIntegerConstraint(constraint = "maritalStatusOfDeceasedConstraint", message = "Invalid Value for maritalStatus", allowNull = true)
    private Integer maritalStatus;

    @XmlElement(name = "extid")
    public String getExtId() {
        return extId;
    }

    public void setExtId(String id) {
        extId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        firstName = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String name) {
        middleName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String name) {
        lastName = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String sex) {
        gender = sex;
    }

    @XmlJavaTypeAdapter(org.openhds.domain.util.CalendarAdapter.class)
    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar date) {
        dob = date;
    }

    public org.openhds.domain.model.Individual getMother() {
        return mother;
    }

    public void setMother(org.openhds.domain.model.Individual mom) {
        mother = mom;
    }

    public org.openhds.domain.model.Individual getFather() {
        return father;
    }

    public void setFather(org.openhds.domain.model.Individual dad) {
        father = dad;
    }

    public String getDobAspect() {
        return dobAspect;
    }

    public void setDobAspect(String aspect) {
        dobAspect = aspect;
    }

    public Set<Residency> getAllResidencies() {
        return allResidencies;
    }

    public void setAllResidencies(Set<Residency> list) {
        allResidencies = list;
    }

    public Set<Relationship> getAllRelationships1() {
        return allRelationships1;
    }

    public void setAllRelationships1(Set<Relationship> list) {
        allRelationships1 = list;
    }

    public Set<Relationship> getAllRelationships2() {
        return allRelationships2;
    }

    public void setAllRelationships2(Set<Relationship> list) {
        allRelationships2 = list;
    }

    public Set<Membership> getAllMemberships() {
        return allMemberships;
    }

    public void setAllMemberships(Set<Membership> list) {
        allMemberships = list;
    }

    public Residency getCurrentResidency() {
        if (allResidencies.size() == 0) {
            return null;
        }
        Iterator<Residency> itr = allResidencies.iterator();
        Residency residency = null;
        while (itr.hasNext()) {
            residency = itr.next();
        }
        return residency;
    }

    public Set<Relationship> getAllRelationships() {
        Set<Relationship> all = new HashSet<Relationship>(getAllRelationships2());
        all.addAll(allRelationships1);
        return all;
    }

    public String getOccupationalStatusOther() {
        return occupationalStatusOther;
    }

    public void setOccupationalStatusOther(String data) {
        occupationalStatusOther = data;
    }

    public String getEducationalStatusOther() {
        return educationalStatusOther;
    }

    public void setEducationalStatusOther(String data) {
        educationalStatusOther = data;
    }

    public Integer getEducationalStatus() {
        return educationalStatus;
    }

    public void setEducationalStatus(Integer data) {
        educationalStatus = data;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer data) {
        workStatus = data;
    }

    public Integer getOccupationalStatus() {
        return occupationalStatus;
    }

    public void setOccupationalStatus(Integer data) {
        occupationalStatus = data;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer data) {
        maritalStatus = data;
    }

}
