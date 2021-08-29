package ua.training.services;

import org.junit.Assert;
import org.junit.Test;
import ua.training.model.services.CalculationService;
import ua.training.model.utils.MyConstants;

import java.math.BigDecimal;

public class CalculationServiceTest {

    private final static CalculationService calculationService = new CalculationService();

    @Test
    public void testTimeCalculator(){
        String string = "Test";
        double multiplier = 7.0;
        int scale = 2;
        double value = (Math.cos(string.hashCode()) + 1.5) * multiplier;
        BigDecimal result = BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_UP);
        Assert.assertEquals(0, result.compareTo(calculationService.calculateTimeOrDistance(string, multiplier, scale)));
    }

    @Test
    public void testTotalCalculator(){
        String string = "Test";
        int scale = 2;
        double discount = 0.0;
        double multiplier = 7.0, distance = calculationService.calculateTimeOrDistance(string, multiplier, scale).doubleValue();
        double value = multiplier * ((distance * MyConstants.ADDITIONAL_PRICE) + MyConstants.INITIAL_PRICE);
        BigDecimal result = BigDecimal.valueOf(value - (value * discount / 100)).setScale(2, BigDecimal.ROUND_UP);
        Assert.assertEquals(0, result.compareTo(calculationService.calculateTotal(multiplier, discount, distance)));
    }

}
