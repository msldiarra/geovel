package com.abidos.tech.persistence;


import com.abidos.tech.model.CarPosition;
import com.abidos.tech.model.CarsPersistenceUnit;
import com.abidos.tech.model.Location;
import com.mysema.query.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.abidos.tech.model.QCarPosition.carPosition;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:global/jms/ClassicQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
})
public class PositionSaver implements MessageListener {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext(unitName = CarsPersistenceUnit.NAME)
    EntityManager entityManager;

    @Override
    public void onMessage(Message message) {

        try {

            CarPosition car = message.getBody(CarPosition.class);
            LOGGER.info("Read car from queue : {} : ", car);

            Location location = new Location();
            location.setLatitude(car.getLocation().getLatitude());
            location.setLongitude(car.getLocation().getLongitude());

            entityManager.persist(location);

            CarPosition existingCarInfo = new JPAQuery(entityManager)
                    .from(carPosition)
                    .where(carPosition.reference.equalsIgnoreCase(car.getReference()))
                    .singleResult(carPosition);

            existingCarInfo.setLocation(location);
            existingCarInfo.setAvailable(car.getAvailable());
            existingCarInfo.setTime(car.getTime());

            entityManager.merge(existingCarInfo);

            LOGGER.info("Car persisted");


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
