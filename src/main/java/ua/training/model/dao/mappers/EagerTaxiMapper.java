package ua.training.model.dao.mappers;

import ua.training.model.entities.Driver;
import ua.training.model.entities.Taxi;
import ua.training.model.entities.TaxiClass;
import ua.training.model.entities.TaxiStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class EagerTaxiMapper implements ObjectMapper<Taxi>{
    @Override
    public Taxi extractFromResultSet(ResultSet rs) throws SQLException {
        Taxi taxi = new Taxi();
        taxi.setId(rs.getLong("id"));
        taxi.setInfo(rs.getString("info"));
        taxi.setCapacity(rs.getInt("capacity"));

        TaxiClass taxiClass = new TaxiClass(rs.getString("class_name"), rs.getBigDecimal("class_multiplier"));
        taxiClass.setId(rs.getLong("class_id"));

        taxi.setTaxiClass(taxiClass);

        TaxiStatus taxiStatus = new TaxiStatus(rs.getString("status_name"));
        taxiStatus.setId(rs.getLong("status_id"));

        taxi.setTaxiStatus(taxiStatus);

        Driver driver = new Driver(rs.getString("driver_name"), rs.getString("driver_surname"), rs.getString("driver_phone"));
        driver.setId(rs.getLong("driver_id"));

        taxi.setDriver(driver);
        return taxi;
    }

    @Override
    public Taxi makeUnique(Map<Long, Taxi> cache, Taxi object) {
        cache.putIfAbsent(object.getId(), object);
        return cache.get(object.getId());
    }
}
