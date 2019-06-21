package com.flexi.camel.routebuilders;

import com.flexi.model.RouteStep;
import com.flexi.camel.processors.DecryptionProcessor;
import com.flexi.camel.processors.EmptyProcessor;
import com.flexi.camel.processors.EncryptionProcessor;
import com.flexi.model.RouteStepTypes;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.List;

public class RouteStepBuilder extends RouteBuilder {

    private static int CountRouteStepBuilders = 0;
    private int RouteStepBuilderId = 0;
    List<RouteStep> routeSteps;


    public RouteStepBuilder( List<RouteStep> routeSteps) {
        this.routeSteps = routeSteps;
        CountRouteStepBuilders++;
        RouteStepBuilderId = CountRouteStepBuilders;
    }


    public String getFirstRootStepId() {
        return "PATH-"+this.RouteStepBuilderId+"-0";
    }


    @Override
    public void configure() throws Exception {

        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setPrettyPrint(false);

        int i =0;
        for(RouteStep routeStep : this.routeSteps) {
            String directId = "PATH-" + this.RouteStepBuilderId + "-" + i;
            String nextDirectId = "PATH-" + this.RouteStepBuilderId + "-" + (i + 1);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXITimerFrom)
                from("timer:everysec?period=" + routeStep.getRouteAction() + "")
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIMqttFrom)
                from(routeStep.getRouteAction())
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXISplit)
                from("direct:" + directId)
                        .split(body())
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXISetBody)
                from("direct:" + directId)
                        .setBody(simple(routeStep.getRouteAction()))
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXITo
                    || routeStep.getRouteStepType() == RouteStepTypes.FLEXISql
                    || routeStep.getRouteStepType() == RouteStepTypes.FLEXIFile)
                from("direct:" + directId)
                        .to(routeStep.getRouteAction())
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXILog)
                from("direct:" + directId)
                        .log(routeStep.getRouteAction())
                        .to("direct:" + nextDirectId);

            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIProcessor)
                from("direct:" + directId)
                        .process(routeStep.getProcessor())
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIDelay)
                from("direct:" + directId)
                        .delayer(Long.parseLong(routeStep.getRouteAction()))
                        .to("log:delayer of " + routeStep.getRouteAction() + " Msecs executed")
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIMqttTo)
                from("direct:" + directId)
                        .to(routeStep.getRouteAction())
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIHashmapToJson)
                from("direct:" + directId)
                        .marshal(jacksonDataFormat)
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIJsonToHashmap)
                from("direct:" + directId)
                        .unmarshal(jacksonDataFormat)
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIEncrypt)
                from("direct:" + directId)
                        .marshal(jacksonDataFormat)
                        .process(new EncryptionProcessor())
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIDecrypt)
                from("direct:" + directId)
                        .process(new DecryptionProcessor())
                        .unmarshal(jacksonDataFormat)
                        .to("direct:" + nextDirectId);

            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXISetSimpleBody)
                from("direct:" + directId)
                        .setBody(simple(routeStep.getRouteAction()))
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIRestTo)
                from("direct:" + directId)
                        .setHeader(Exchange.HTTP_METHOD, constant((String) routeStep.getOption("Method")))
                        .to(routeStep.getRouteAction())
                        .to("direct:" + nextDirectId);


            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIRestFrom)
            {

                restConfiguration()
                        .contextPath("CamelServlet")
                        .port((Integer) routeStep.getOption("port"))
                        .enableCORS((Boolean) routeStep.getOption("enableCORS"))
                        .component("servlet")
                        .bindingMode(RestBindingMode.json);


                rest("/api/")
                    .id("api-route")
                    .consumes("application/json")
                    .post((String) routeStep.getOption("subdomain"))
                    .to("direct:" + nextDirectId);

            }




            if (routeStep.getRouteStepType() == RouteStepTypes.FLEXIRestFromResponce)
                from("direct:" + directId)
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                        .to("direct:" + nextDirectId);


            i++;
        }

        String directId = "PATH-"+this.RouteStepBuilderId+"-"+i;
        from("direct:"+directId).process(new EmptyProcessor());



    }







}
