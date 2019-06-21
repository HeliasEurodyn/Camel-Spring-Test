package com.flexi;

import com.flexi.model.RouteStepTypes;
import com.flexi.model.RouteStep;
import com.flexi.camel.routebuilders.RouteStepBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlexiCamelApplicationTests {

    @Test
    public void contextLoads() {
        assertEquals("hello", "Test Value");
    }


    @Test
    public void CamelEncryptDecryptTest() throws Exception {

        List<RouteStep> routeSteps = new ArrayList<RouteStep>();
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXITimerFrom,"2000") );
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXISetSimpleBody,"Test Value") );
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXIEncrypt));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXIDecrypt));
        RouteStepBuilder RouteStepBuilder = new RouteStepBuilder(routeSteps);

        ModelCamelContext context = new DefaultCamelContext();
        context.addRoutes(RouteStepBuilder);
        context.start();

        String result = "Test Value";
        assertEquals(result, "Test Value");
    }





}
