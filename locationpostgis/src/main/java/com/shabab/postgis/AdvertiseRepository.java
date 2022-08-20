package com.shabab.postgis;
import org.geolatte.geom.Geometry;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.persistence.Tuple;
import java.util.List;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;


@Repository
public interface AdvertiseRepository extends PagingAndSortingRepository<Advertise, Long> {



    @Query("SELECT vl, distance(vl.location, ?1) as distance from Advertise vl order by distance")
    List<Tuple> findAllWithDistance(Point<G2D> refPnt);


    @Query("SELECT vl from Advertise vl where  distance(vl.location, ?1) < ?2")
    List<Advertise> findAllWithDistance3(Point<G2D> refPnt, double dist);


    @Query(" SELECT evt  ,distance(evt.location, ?1) as distance from Advertise evt where distance<200")
    List<Advertise> findAllWithDistance4(Point<G2D> refPnt);

    //@Query("SELECT TOP 1 *  from VehicleLock evt where  distance(evt.lastLocation, ?1)" )
    //@Query("SELECT evt, distance(evt.lastLocation, ?1) as distance from VehicleLock evt where evt.vehicle.inService=false order by distance" )
    //@Query("SELECT vl, distance(vl.lastLocation, ?1) as distance from VehicleLock vl where vl.vehicle.inService=true order by distance" )
    @Query("SELECT vl, distance(vl.location, ?1) as distance from Advertise vl  order by distance" )
    List<Tuple> findNearest(Point<G2D> refPnt, Pageable page);


    @Query("SELECT v1 FROM Advertise AS v1 WHERE  within(v1.location, :filter)=TRUE ")
    List<Advertise> findWithin(@Param("filter") Geometry filter);

}





