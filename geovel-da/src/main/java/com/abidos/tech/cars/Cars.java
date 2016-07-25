package com.abidos.tech.cars;


import com.abidos.tech.model.*;
import com.mysema.query.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static com.abidos.tech.model.QCarPosition.carPosition;
import static com.abidos.tech.model.QZone.zone;

@Path("/cars")
@Consumes({"application/json"})
@Produces({"application/json"})
@Stateless
public class Cars {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Inject
    Positions positions;

    @Inject
    private JMSContext jmsContext;

    @PersistenceContext(unitName = CarsPersistenceUnit.NAME)
    private EntityManager entityManager;

    @Resource(mappedName = "java:global/jms/ClassicQueue")
    Queue demoQueue;

    @GET
    @PermitAll
    public Response available(@QueryParam("where") @NotNull String where) {

        LOGGER.info("Retrieving available cars for : {} : ", where);

        List<CarPosition> availableCars = new JPAQuery(entityManager)
                .from(carPosition)
                .where(carPosition.available.eq(true), carPosition.location.isNotNull())
                .list(carPosition);

        Zone searchZone = new JPAQuery(entityManager)
                .from(zone)
                .where(zone.name.equalsIgnoreCase(where))
                .singleResult(zone);

        List<CarPosition> cars = positions.getAvailableCarsInZone(searchZone, availableCars);

        return Response.ok(cars.toString()).build();
    }

    @PUT
    @PermitAll
    public Response save(CarPosition car) {

        jmsContext.createProducer().send(demoQueue, car);

        LOGGER.info("Pushing car position in queue : {} : ", car);

        return Response.ok(car.toString()).build();

    }

}
