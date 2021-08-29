package ua.training.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.training.model.entities.User;
import ua.training.model.services.QueryService;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryServiceTest {

    private final static QueryService queryService = new QueryService();

    @Mock
    User user;

    @Test
    public void testUserQueryFilterPreparatorReturnsId(){
        when(user.getId()).thenReturn(1L);
        Assert.assertEquals("=1", queryService.prepareUserFilterQuery(user, false, null, null));
    }

    @Test
    public void testUserQueryFilterPreparatorReturnsStatement(){
        Assert.assertEquals("IS NOT NULL", queryService.prepareUserFilterQuery(null, false, null, null));
    }

    @Test
    public void testUserQueryFilterPreparatorReturnsQuery(){
        Assert.assertEquals("=(SELECT id FROM users WHERE name='name' AND surname='surname')",
                queryService.prepareUserFilterQuery(null, true, "name", "surname"));
    }

}
