package org.openhds.integration;

import static org.junit.Assert.*;
import java.util.Calendar;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openhds.controller.service.CurrentUser;
import org.openhds.controller.service.LocationHierarchyService;
import org.openhds.dao.service.GenericDao;
import org.openhds.domain.model.FieldWorker;
import org.openhds.domain.model.Individual;
import org.openhds.domain.model.Location;
import org.openhds.domain.model.LocationHierarchy;
import org.openhds.domain.model.Round;
import org.openhds.domain.model.Visit;
import org.openhds.domain.service.SitePropertiesService;
import org.openhds.domain.util.CalendarUtil;
import org.openhds.integration.util.JsfServiceMock;
import org.openhds.web.crud.impl.LocationCrudImpl;
import org.openhds.web.crud.impl.LocationHierarchyCrudImpl;
import org.openhds.web.crud.impl.RoundCrudImpl;
import org.openhds.web.crud.impl.VisitCrudImpl;
import org.openhds.web.service.JsfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("/testContext.xml")
public class VisitTest extends AbstractTransactionalJUnit4SpringContextTests {
		 	 
	 @Autowired
	 @Qualifier("locationCrud")
	 LocationCrudImpl locationCrud;
	
	 @Autowired
	 @Qualifier("locationHierarchyCrud")
	 LocationHierarchyCrudImpl locationHierarchyCrud;
	 
	 @Autowired
	 @Qualifier("roundCrud")
	 RoundCrudImpl roundCrud;
	 
	 @Autowired
	 @Qualifier("visitCrud")
	 VisitCrudImpl visitCrud;
	 	 
	 @Autowired
	 @Qualifier("locationHierarchyService")
	 LocationHierarchyService locationHierarchyService;
	 	 
	 @Autowired
	 SessionFactory sessionFactory;
	 
	 @Autowired
	 GenericDao genericDao;
	 
	 @Autowired
	 SitePropertiesService siteProperties;
	 
	 @Autowired
	 CalendarUtil calendarUtil;
	 
	 @Autowired
	 JsfService jsfService;
	 	 
	 @Autowired
	 @Qualifier("currentUser")
	 CurrentUser currentUser;
	 
	 JsfServiceMock jsfServiceMock;
	 LocationHierarchy item;
	 FieldWorker fieldWorker;
	 Individual individual;
	 Location location;
	 
	 @Before
	 public void setUp() {
		 
		 jsfServiceMock = (JsfServiceMock)jsfService;
		 currentUser.setProxyUser("admin", "test", new String[] {"VIEW_ENTITY", "CREATE_ENTITY"});
		 
		 fieldWorker = genericDao.findByProperty(FieldWorker.class, "extId", "FWEK1D");
		 individual = genericDao.findByProperty(Individual.class, "extId", "BRIHA001", false);
		 createLocationHierarchy();
		 createLocation();
	 }
	 	 
	 @Test
	 public void testVisitCreate() {
		 		 
		 Round round = new Round();
		 round.setRoundNumber(1);
		 round.setStartDate(calendarUtil.getCalendar(Calendar.JANUARY, 4, 1989));
		 round.setEndDate(calendarUtil.getCalendar(Calendar.JANUARY, 5, 1990));
		 
		 roundCrud.setItem(round);
		 roundCrud.create();		 
		 
		 Visit visit = new Visit();
		 visit.setRoundNumber(1);
		 visit.setVisitLocation(location);
		 visit.setVisitDate(calendarUtil.getCalendar(Calendar.JANUARY, 4, 1990));
		 visit.setCollectedBy(fieldWorker);
		 
		 visitCrud.setItem(visit);
		 visitCrud.create();
		 
		 Visit savedVisit = genericDao.findByProperty(Visit.class, "extId", visit.getExtId());
		 assertNotNull(savedVisit);
	 }
	 
	 @Test
	 public void testInvalidVisit() {
						 
		 Visit visit = new Visit();
		 visit.setRoundNumber(2);
		 visit.setVisitLocation(location);
		 visit.setVisitDate(calendarUtil.getCalendar(Calendar.JANUARY, 4, 1990));
		 visit.setCollectedBy(fieldWorker);
		 
		 visitCrud.setItem(visit);
		 assertNull(visitCrud.create());
		 assertTrue(jsfServiceMock.getErrors().size() > 0);
	 }
	 
	 private void createLocationHierarchy() {
		 
    	 LocationHierarchy locH1 = new LocationHierarchy();
    	
    	 locH1.setParent(new LocationHierarchy());
    	 locH1.setLevel(locationHierarchyService.getLevel(1));
    	 locH1.setName("MOROGORO");

    	 locationHierarchyCrud.setItem(locH1);
    	 locationHierarchyCrud.create();
	   
	     LocationHierarchy locH2 = new LocationHierarchy();
	     locH2.setParent(locH1);
	     locH2.setLevel(locationHierarchyService.getLevel(2));
	     locH2.setName("IFAKARA");
   	    
 	     locationHierarchyCrud.setItem(locH2);
	     locationHierarchyCrud.create();
	  
	     item = new LocationHierarchy();
	     item.setParent(locH2);
	     item.setLevel(locationHierarchyService.getLevel(3));
	     item.setName("MBINGU");
   	    
	     locationHierarchyCrud.setItem(item);
 	     locationHierarchyCrud.create();
	 }
	 
	 private void createLocation() {
		 location = new Location();
		 location.setLocationName("locationName");
		 location.setLocationType("RUR");
		 location.setLocationHead(individual);
		 location.setLocationLevel(item);
		 location.setCollectedBy(fieldWorker);
		 locationCrud.setItem(location);
	     locationCrud.create();
	 }
}
