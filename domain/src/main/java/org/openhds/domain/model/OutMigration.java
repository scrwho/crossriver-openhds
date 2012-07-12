
package org.openhds.domain.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.openhds.domain.annotations.Description;
import org.openhds.domain.constraint.CheckCalendar;
import org.openhds.domain.constraint.CheckEntityNotVoided;
import org.openhds.domain.constraint.CheckIndividualNotUnknown;
import org.openhds.domain.constraint.ExtensionIntegerConstraint;
import org.openhds.domain.constraint.Searchable;


/**
 * Generated by JCodeModel
 * 
 */
@Description(description = "An OutMigration represents a migration out of the study area.It contains information about the Individual who is out-migrating to a particularResidency. It also contains information about the destination, date, and reason theIndividual is migrating as well as the Visit associated with the migration.")
@Entity
@Table(name = "outmigration")
@XmlRootElement(name = "outmigration")
public class OutMigration
    extends AuditableCollectedEntity
    implements Serializable
{

    public final static long serialVersionUID = 6736599408170070468L;
    @NotNull
    @Searchable
    @CheckEntityNotVoided
    @ManyToOne
    @CheckIndividualNotUnknown
    @Description(description = "Individual who is outmigrating, identified by external id.")
    private Individual individual = new Individual();
    @OneToOne
    @NotNull
    @Description(description = "The residency the individual is outmigrating to.")
    private Residency residency;
    @Searchable
    @ManyToOne
    @Description(description = "Moving away from house.")
    private Location house;
    @Searchable
    @ManyToOne
    @Description(description = "Moving away from household.")
    private SocialGroup household;
    @Searchable
    @Description(description = "Destination of the outmigration.")
    private String origin;
    @Searchable
    @ExtensionIntegerConstraint(constraint = "reasonForOutmigrationConstraint", message = "Invalid Value for reason", allowNull = true)
    @Description(description = "Reason for outmigrating.")
    private Integer reason;
    @Searchable
    @NotNull
    @ManyToOne
    @Description(description = "The visit associated with the outmigration, identified by external id.")
    private Visit visit;
    @NotNull
    @CheckCalendar(message = "Recorded Date is invalid")
    @Temporal(TemporalType.DATE)
    @Past(message = "The date of the migration must be in the past")
    @Description(description = "Recorded date of the inmigration.")
    private Calendar recordedDate;
    @Description(description = "If place moved to is other, please specify")
    private String placeMovedToOther;
    @Description(description = "Where the migrant moved to")
    @ExtensionIntegerConstraint(constraint = "placeMovedToConstraint", message = "Invalid Value for placeMovedTo", allowNull = true)
    private Integer placeMovedTo;
    @Description(description = "Date of interview for the outmigration")
    @CheckCalendar(message = "Invalid value for date")
    @Temporal(TemporalType.DATE)
    @Past
    private Calendar dateOfInterview;
    @Description(description = "The house moved to")
    private String houseName;
    @Description(description = "Phone number where the migrant can be reached")
    private String phoneNumber;
    @Description(description = "Village the migrant moved to")
    private String village;
    @Description(description = "If reason for migration is other, please specify")
    private String reasonForMigrationOther;
    @Description(description = "Section the migrant moved to")
    private String section;

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual indiv) {
        individual = indiv;
    }

    public Residency getResidency() {
        return residency;
    }

    public void setResidency(Residency res) {
        residency = res;
    }

    public Location getHouse() {
        return house;
    }

    public void setHouse(Location place) {
        house = place;
    }

    public SocialGroup getHousehold() {
        return household;
    }

    public void setHousehold(SocialGroup place) {
        household = place;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String name) {
        origin = name;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer name) {
        reason = name;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit vis) {
        visit = vis;
    }

    @XmlJavaTypeAdapter(org.openhds.domain.util.CalendarAdapter.class)
    public Calendar getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Calendar date) {
        recordedDate = date;
    }

    public String getPlaceMovedToOther() {
        return placeMovedToOther;
    }

    public void setPlaceMovedToOther(String data) {
        placeMovedToOther = data;
    }

    public Integer getPlaceMovedTo() {
        return placeMovedTo;
    }

    public void setPlaceMovedTo(Integer data) {
        placeMovedTo = data;
    }

    @XmlJavaTypeAdapter(org.openhds.domain.util.CalendarAdapter.class)
    public Calendar getDateOfInterview() {
        return dateOfInterview;
    }

    public void setDateOfInterview(Calendar data) {
        dateOfInterview = data;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String data) {
        houseName = data;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String data) {
        phoneNumber = data;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String data) {
        village = data;
    }

    public String getReasonForMigrationOther() {
        return reasonForMigrationOther;
    }

    public void setReasonForMigrationOther(String data) {
        reasonForMigrationOther = data;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String data) {
        section = data;
    }

}
