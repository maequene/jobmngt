package fr.atlantique.imt.inf211.jobmngt.entity;
// Generated Mar 3, 2025, 4:38:44 PM by Hibernate Tools 5.6.15.Final


import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Joboffer generated by hbm2java
 */
@Entity
@Table(name="joboffer"
    ,schema="public"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class JobOffer  implements java.io.Serializable {


    private int id;
    private Company company;
    private QualificationLevel qualificationlevel;
    private String title;
    private String taskdescription;
    private Date publicationdate;
    private Set<Sector> sectors = new HashSet<>(0);
    private Set<OfferMessage> offermessages = new HashSet<>(0);

    public JobOffer() {
    }

	
    public JobOffer(int id, Company company, QualificationLevel qualificationlevel, String title) {
        this.id = id;
        this.company = company;
        this.qualificationlevel = qualificationlevel;
        this.title = title;
    }
    public JobOffer(int id, Company company, QualificationLevel qualificationlevel, String title, String taskdescription, Date publicationdate, Set<Sector> sectors, Set<OfferMessage> offermessages) {
       this.id = id;
       this.company = company;
       this.qualificationlevel = qualificationlevel;
       this.title = title;
       this.taskdescription = taskdescription;
       this.publicationdate = publicationdate;
       this.sectors = sectors;
       this.offermessages = offermessages;
    }
   
    @Id
	@SequenceGenerator(name = "JOBOFFER_ID_GENERATOR", sequenceName = "JOBOFFER_ID_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "JOBOFFER_ID_GENERATOR") 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="compid", nullable=false)
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="qualiflevelid", nullable=false)
    public QualificationLevel getQualificationlevel() {
        return this.qualificationlevel;
    }
    
    public void setQualificationlevel(QualificationLevel qualificationlevel) {
        this.qualificationlevel = qualificationlevel;
    }

    
    @Column(name="title", nullable=false, length=50)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    @Column(name="taskdescription")
    public String getTaskdescription() {
        return this.taskdescription;
    }
    
    public void setTaskdescription(String taskdescription) {
        this.taskdescription = taskdescription;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="publicationdate", length=13)
    public Date getPublicationdate() {
        return this.publicationdate;
    }
    
    public void setPublicationdate(Date publicationdate) {
        this.publicationdate = publicationdate;
    }

@ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="indexjoboffersector", schema="public", joinColumns = { 
        @JoinColumn(name="jobofferid", nullable=false, updatable=false) }, inverseJoinColumns = { 
        @JoinColumn(name="sectorid", nullable=false, updatable=false) })
    public Set<Sector> getSectors() {
        return this.sectors;
    }
    
    public void setSectors(Set<Sector> sectors) {
        this.sectors = sectors;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="joboffer")
    public Set<OfferMessage> getOffermessages() {
        return this.offermessages;
    }
    
    public void setOffermessages(Set<OfferMessage> offermessages) {
        this.offermessages = offermessages;
    }
}