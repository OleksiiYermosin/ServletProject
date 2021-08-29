package ua.training.model.dao.mappers;

import ua.training.model.entities.TaxiStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaxiStatusMapper implements ObjectMapper<TaxiStatus> {

    @Override
    public TaxiStatus extractFromResultSet(ResultSet rs) throws SQLException {
        TaxiStatus taxiStatus = new TaxiStatus();
        taxiStatus.setId(rs.getLong("id"));
        taxiStatus.setName(rs.getString("name"));
        return taxiStatus;
    }

}
