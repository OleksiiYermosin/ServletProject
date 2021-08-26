package ua.training.model.dao.mappers;

import ua.training.model.entities.TaxiClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TaxiClassMapper implements ObjectMapper<TaxiClass> {

    @Override
    public TaxiClass extractFromResultSet(ResultSet rs) throws SQLException {
        TaxiClass taxiClass = new TaxiClass();
        taxiClass.setId(rs.getLong("id"));
        taxiClass.setName(rs.getString("name"));
        taxiClass.setMultiplier(rs.getBigDecimal("multiplier"));
        return taxiClass;
    }

    @Override
    public TaxiClass makeUnique(Map<Long, TaxiClass> cache, TaxiClass taxiClass) {
        cache.putIfAbsent(taxiClass.getId(), taxiClass);
        return cache.get(taxiClass.getId());
    }
}
