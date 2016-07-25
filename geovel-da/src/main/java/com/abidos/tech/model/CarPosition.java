package com.abidos.tech.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Entity
@XmlRootElement
@Table(name = "CarPosition")
public class CarPosition implements Serializable {

    public static final String PLATFORM_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reference;

    @Column(nullable = false)
    private String driverName;
    
    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean available;

    @OneToOne
    @JoinColumn(name="locationid")
    private Location location;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public static String getPlatformDateFormat() {
        return PLATFORM_DATE_FORMAT;
    }

    @Override
    public String toString() {
        return "CarPosition{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", driverName='" + driverName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", available=" + available +
                ", location=" + location +
                ", time=" + time +
                '}';
    }
}
