package com.flexi;

import com.flexi.model.RouteStep;
import com.flexi.model.RouteStepTypes;
import com.flexi.camel.processors.BodySerializerProcessor;
import com.flexi.camel.processors.OpenWeatherMapProcessor;
import com.flexi.camel.processors.RandIntProcessor;
import com.flexi.camel.routebuilders.RouteStepBuilder;
import lombok.extern.java.Log;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.paho.PahoComponent;
import org.apache.camel.component.paho.PahoConstants;
import org.apache.camel.component.sql.SqlComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Log
@ShellComponent
public class ShellCommands {

    public ShellCommands() throws Exception {
   //    this.dynamicRoutes3();
    }


    @ShellMethod("datasourceTest")
    public void datasourceTest() throws Exception {

         ModelCamelContext context = new DefaultCamelContext();

         /*
          *
          * Creation of datasources for camel
          *
          */
         BasicDataSource ds = new BasicDataSource();
         ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
         ds.setUsername("root");
         ds.setPassword("");
         ds.setUrl("jdbc:mysql://localhost:3306/pgp");


         context.getComponent("sql", SqlComponent.class).setDataSource(ds);

         /*
          * Creation of execution logic (Route) for camel
          * Using RouteBuilder class
          */
         context.addRoutes(
                new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer:everysec?period=1s")
                        .to("sql:select * from user")
                        .split().body()
                        .log("${body}")
                        .process(new BodySerializerProcessor())
                        .to("file:C:/Users/helias/vids");
             }
         });

         context.start();
    }


    @ShellMethod("dynamicRouterTest")
    public void dynamicRouterTest() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();

        /*
         *
         * Creation of datasources for camel
         *
         */
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("");
        ds.setUrl("jdbc:mysql://localhost:3306/pgp");
        context.getComponent("sql", SqlComponent.class).setDataSource(ds);

        /*
         *
         * Generate list for the dynamic paths (RouteStep list)
         *
         */
        List<RouteStep> routeSteps = new ArrayList<RouteStep>();

        routeSteps.add( new RouteStep(RouteStepTypes.FLEXITo,"sql:select * from user"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXIDelay,"2000"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"${body}"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXIProcessor, new BodySerializerProcessor() ));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXITo,"file:C:/Users/helias/vids"));
        //    routeSteps.add( new RouteStep(RouteStep.FLEXIROUTER));
        // routeSteps.add( routebuilder.getRouteAction() );

        /*
         * Creation of execution logic (Route) for camel
         * Using RouteStepBuilder class
         */
        RouteStepBuilder routeStepBuilder = new RouteStepBuilder(routeSteps);

        /*
         * Assign RouteStepBuilder to the camel context
         */
        context.addRoutes(routeStepBuilder);

        context.start();
    }



    @ShellMethod("dynamicRouterTest2")
    public void dynamicRouterTest2() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();

        /*
         *
         * Creation of mysql datasource
         *
         */
        BasicDataSource mysqlDataSource = new BasicDataSource();
        mysqlDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        mysqlDataSource.setUsername("root");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/pgp");
       // context.getComponent("sql2", SqlComponent.class).setDataSource(ds);
        context.getRegistry().bind("mysqlDb",mysqlDataSource);


        /*
         *
         * Creation of sql server datasource
         *
         */
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        BasicDataSource sqlserverDataSource = new BasicDataSource();
        sqlserverDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        sqlserverDataSource.setUsername("sa");
        sqlserverDataSource.setPassword("1491205");
        sqlserverDataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=pgp");
        // context.getComponent("sql2", SqlComponent.class).setDataSource(ds);
        context.getRegistry().bind("sqlServerDb",sqlserverDataSource);

        /*
         *
         * Generate list for the dynamic paths (RouteStep list)
         *
         */
        List<RouteStep> routeSteps = new ArrayList<RouteStep>();

        routeSteps.add( new RouteStep(RouteStepTypes.FLEXITo,"sql:select * from user1;?dataSource=#sqlServerDb"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"1. ${body}"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXISplit));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"3. ${body}"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXIDelay,"2000"));


        /*
         * Creation of execution logic (Route) for camel
         * Using RouteStepBuilder class
         */
        RouteStepBuilder routeStepBuilder = new RouteStepBuilder(routeSteps);

        /*
         * Assign RouteStepBuilder to the camel context
         */
        context.addRoutes(routeStepBuilder);

        context.start();
    }

    @ShellMethod("dynamicRouterTest3")
    public void dynamicRouterTest3() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();

        /*
         *
         * Creation of mysql datasource
         *
         */
        BasicDataSource mysqlDataSource = new BasicDataSource();
        mysqlDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        mysqlDataSource.setUsername("root");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/pgp");
        // context.getComponent("sql2", SqlComponent.class).setDataSource(ds);
        context.getRegistry().bind("mysqlDb",mysqlDataSource);


        /*
         *
         * Creation of sql server datasource
         *
         */
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        BasicDataSource sqlserverDataSource = new BasicDataSource();
        sqlserverDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        sqlserverDataSource.setUsername("sa");
        sqlserverDataSource.setPassword("1491205");
        sqlserverDataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=pgp");
        // context.getComponent("sql2", SqlComponent.class).setDataSource(ds);
        context.getRegistry().bind("sqlServerDb",sqlserverDataSource);

        /*
         *
         * Generate list for the dynamic paths (RouteStep list)
         *
         */
        List<RouteStep> routeSteps = new ArrayList<RouteStep>();
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXITo,"sql:select * from user;?dataSource=#mysqlDb"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXISplit));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"${body}"));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXIProcessor, new BodySerializerProcessor() ));
        routeSteps.add( new RouteStep(RouteStepTypes.FLEXITo,"file:C:/Users/helias/vids"));

        /*
         * Creation of execution logic (Route) for camel
         * Using RouteStepBuilder class
         */
        RouteStepBuilder routeStepBuilder = new RouteStepBuilder(routeSteps);
        context.addRoutes(routeStepBuilder);

        context.start();
    }

    @ShellMethod("dynamicRoutes")
    public void dynamicRoutes() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();
        context.getRegistry().bind("paho",new PahoComponent());

        /*
         * Datasource
         * Creation of mysql datasource
         */
        BasicDataSource mysqlDataSource = new BasicDataSource();
        mysqlDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        mysqlDataSource.setUsername("root");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/pgp");
        context.getRegistry().bind("mysqlDb",mysqlDataSource);

        /*
         * Datasource
         * Creation of sql server datasource
         */
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        BasicDataSource sqlserverDataSource = new BasicDataSource();
        sqlserverDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        sqlserverDataSource.setUsername("sa");
        sqlserverDataSource.setPassword("1491205");
        sqlserverDataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=pgp");
        context.getRegistry().bind("sqlServerDb",sqlserverDataSource);

        /*
         * Execution Instructions
         * Generate list for the dynamic paths (RouteStep list)
         * This is the execution logic for camel
         */
        List<RouteStep> dbToDbRouteSteps = new ArrayList<RouteStep>();
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXITimerFrom,"10000") );
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:select u.* from user u where u.id not in (select id from flexihistoryusers);?dataSource=#mysqlDb") );
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISplit) );
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:insert into flexihistoryusers values (:#id);?dataSource=#mysqlDb") );
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:insert into user1 values (:#id,:#password,3,4,:#username);?dataSource=#sqlServerDb") );


        List<RouteStep> weatherRouteSteps = new ArrayList<RouteStep>();
        weatherRouteSteps.add( new RouteStep(RouteStepTypes.FLEXITimerFrom,"60000") );
        RouteStep restRouteStep = new RouteStep(RouteStepTypes.FLEXIRestTo,"http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&APPID=89796c82d43df1f5fe20bc52316abf19");
        restRouteStep.setOption("Method","POST");
        weatherRouteSteps.add(restRouteStep);
        weatherRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIProcessor, new OpenWeatherMapProcessor() ));
        weatherRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:insert into weather values (:#deg,:#speed,:#temp_min,:#humidity,:#clouds,:#id,:#weathermain,:#weatherdescription,GETDATE());?dataSource=#sqlServerDb") );


        List<RouteStep> dbToFileAndMqttRouteSteps = new ArrayList<RouteStep>();
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXITimerFrom,"2000") );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:select u.*,w2.* from user1 u, (select top 1 w.* from weather w order by w.datecreated desc) as w2 where u.id not in (select id from flexihistoryusers);?dataSource=#sqlServerDb") );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISplit) );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"1.HashMap ${body}") );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIEncrypt));
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIMqttTo,"paho:rooms/room1/sensors/temp?brokerUrl=tcp://localhost:1883") );


        List<RouteStep> mqttListenerRouteSteps = new ArrayList<RouteStep>();
        mqttListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIMqttFrom,"paho:rooms/room1/sensors/temp?brokerUrl=tcp://localhost:1883") );
        mqttListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIDecrypt));
        mqttListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"New Message Received From Mqtt: ${body}") );


        /*
         * Generate RouteStepBuilder class and assign the RouteStep list etc.
         * Start execution
         */
        RouteStepBuilder dbToDbRouteStepBuilder = new RouteStepBuilder(dbToDbRouteSteps);
        RouteStepBuilder weatherRouteStepBuilder = new RouteStepBuilder(weatherRouteSteps);
        RouteStepBuilder dbToFileAndMqttRouteStepBuilder = new RouteStepBuilder(dbToFileAndMqttRouteSteps);
        RouteStepBuilder  mqttListenerRouteStepBuilder = new RouteStepBuilder(mqttListenerRouteSteps);

        context.addRoutes(dbToDbRouteStepBuilder);
     //   context.addRoutes(weatherRouteStepBuilder);
        context.addRoutes(dbToFileAndMqttRouteStepBuilder);
        context.addRoutes(mqttListenerRouteStepBuilder);

        context.start();
    }


    @ShellMethod("dynamicRoutes2")
    public void dynamicRoutes2() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();
        context.getRegistry().bind("paho",new PahoComponent());

        /*
         * Datasource
         * Creation of mysql datasource
         */
        BasicDataSource mysqlDataSource = new BasicDataSource();
        mysqlDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        mysqlDataSource.setUsername("root");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/pgp");
        context.getRegistry().bind("mysqlDb",mysqlDataSource);

        /*
         * Datasource
         * Creation of sql server datasource
         */
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        BasicDataSource sqlserverDataSource = new BasicDataSource();
        sqlserverDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        sqlserverDataSource.setUsername("sa");
        sqlserverDataSource.setPassword("1491205");
        sqlserverDataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=pgp");
        context.getRegistry().bind("sqlServerDb",sqlserverDataSource);

        /*
         * Execution Instructions
         * Generate list for the dynamic paths (RouteStep list)
         * This is the execution logic for camel
         */
        List<RouteStep> dbToDbRouteSteps = new ArrayList<RouteStep>();
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXITimerFrom,"10000") );
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIProcessor, new RandIntProcessor(3000)));
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:SELECT f.* FROM pgp.flexientries f ORDER BY RAND() LIMIT :#randInt;?dataSource=#mysqlDb") );
        dbToDbRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIEncrypt));



        List<RouteStep> weatherRouteSteps = new ArrayList<RouteStep>();
        weatherRouteSteps.add( new RouteStep(RouteStepTypes.FLEXITimerFrom,"60000") );
        RouteStep restRouteStep = new RouteStep(RouteStepTypes.FLEXIRestTo,"http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&APPID=89796c82d43df1f5fe20bc52316abf19");
        restRouteStep.setOption("Method","POST");
        weatherRouteSteps.add(restRouteStep);
        weatherRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIProcessor, new OpenWeatherMapProcessor() ));
        weatherRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:insert into weather values (:#deg,:#speed,:#temp_min,:#humidity,:#clouds,:#id,:#weathermain,:#weatherdescription,GETDATE());?dataSource=#sqlServerDb") );


        List<RouteStep> dbToFileAndMqttRouteSteps = new ArrayList<RouteStep>();
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXITimerFrom,"2000") );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISql,"sql:select u.*,w2.* from user1 u, (select top 1 w.* from weather w order by w.datecreated desc) as w2 where u.id not in (select id from flexihistoryusers);?dataSource=#sqlServerDb") );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISplit) );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"1.HashMap ${body}") );
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIEncrypt));
        dbToFileAndMqttRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIMqttTo,"paho:rooms/room1/sensors/temp?brokerUrl=tcp://localhost:1883") );


        List<RouteStep> mqttListenerRouteSteps = new ArrayList<RouteStep>();
        mqttListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIMqttFrom,"paho:rooms/room1/sensors/temp?brokerUrl=tcp://localhost:1883") );
        mqttListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIDecrypt));
        mqttListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"New Message Received From Mqtt: ${body}") );


        /*
         * Generate RouteStepBuilder class and assign the RouteStep list etc.
         * Start execution
         */
        RouteStepBuilder dbToDbRouteStepBuilder = new RouteStepBuilder(dbToDbRouteSteps);
        RouteStepBuilder weatherRouteStepBuilder = new RouteStepBuilder(weatherRouteSteps);
        RouteStepBuilder dbToFileAndMqttRouteStepBuilder = new RouteStepBuilder(dbToFileAndMqttRouteSteps);
        RouteStepBuilder  mqttListenerRouteStepBuilder = new RouteStepBuilder(mqttListenerRouteSteps);

        context.addRoutes(dbToDbRouteStepBuilder);
        //   context.addRoutes(weatherRouteStepBuilder);
        context.addRoutes(dbToFileAndMqttRouteStepBuilder);
        context.addRoutes(mqttListenerRouteStepBuilder);

        context.start();
    }




    @ShellMethod("pahoListener")
    public void pahoListenerTest() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("paho:rooms/room1/sensors/temp?brokerUrl=tcp://localhost:1883").log("${body}");//.process(print);

            }

            private Processor print = new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    Object header = exchange.getIn().getHeader(PahoConstants.MQTT_TOPIC);
                    String topic = (String) header;
                    Message camelMessage = exchange.getIn();

                    byte[] body = (byte[]) camelMessage.getBody();
                    String payload = new String(body, "utf-8");

                    log.info("topic=" + topic + ", payload=" + payload);


                }
            };

        });


        context.start();
    }



    @ShellMethod("executeParallelTest")
    public void executeParallelTest() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer:everysec?period=1000").setBody(simple("Route1")).log("${body}");
                from("timer:everysec?period=1000").setBody(simple("Route2")).log("${body}");
            }
        });

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer:everysec?period=1000").setBody(simple("Route1111")).log("${body}");
                from("timer:everysec?period=1000").setBody(simple("Route2222")).log("${body}");
            }
        });


        context.start();
    }


    @ShellMethod("executeParallelTest")
    public void restlistener() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();


       // ServletRegistrationBean servlet = new ServletRegistrationBean(
        //        new CamelHttpTransportServlet(), "/camel/*");
       // servlet.setName("servlet");
        //servlet.setLoadOnStartup(1);


        //context.getRegistry().bind("CamelServlet",servlet);
       // context.getRegistry().bind("CamelServlet",servlet);


        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                CamelContext context = new DefaultCamelContext();


                restConfiguration()
                        .contextPath("CamelServlet")
                        .port(8080)
                        .enableCORS(false)
                        //.apiContextPath("/api-doc")
                     //   .apiProperty("api.title", "Test REST API")
                     //   .apiProperty("api.version", "v1")
                     //   .apiContextRouteId("doc-api")
                        .component("servlet")
                        .bindingMode(RestBindingMode.json);


               //  .contextPath(contextPath)
               //   .port(serverPort)
               //   .enableCORS(true)
              //    .apiContextPath("/api-doc")
              //    .apiProperty("api.title", "Test REST API")
              //    .apiProperty("api.version", "v1")
             //     .apiContextRouteId("doc-api")
             //     .component("servlet")
              //    .bindingMode(RestBindingMode.json);

                rest("/api/")
                        .id("api-route")
                        .consumes("application/json")
                        .post("/bean")
                      //  .bindingMode(RestBindingMode.json)
                        //.type(String.class)
                        .to("direct:remoteService");

                from("direct:remoteService")
              //  .routeId("direct-route")
                .tracing()
                .log(">>> Hello Camel World")
                .log(">>> ${body}")
                .transform().simple("Hello Camel World")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200)) .log(">>>!! ${body}");


     /*           rest("/api/")
                        .get("/hello")
                      //  .id("api-route")
                       // .consumes("application/json")
                        //.post("/bean")
                        //.bindingMode(RestBindingMode.json_xml)
                       // .type(MyBean.class)
                        .log("log:test") .transform().simple("Hello ${in.body.name}")
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
*/
           //     rest("/user")
            //            .get("/hello").
           //            to("log:${body}");
            }
        });

        context.start();
    }


    @ShellMethod("dynamicRoutes3")
    public void dynamicRoutes3() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();


        /*
         * Datasource
         * Creation of mysql datasource
         */
        BasicDataSource mysqlDataSource = new BasicDataSource();
        mysqlDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        mysqlDataSource.setUsername("root");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/pgp");
        context.getRegistry().bind("mysqlDb",mysqlDataSource);

        /*
         * Datasource
         * Creation of sql server datasource
         */
        DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        BasicDataSource sqlserverDataSource = new BasicDataSource();
        sqlserverDataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        sqlserverDataSource.setUsername("sa");
        sqlserverDataSource.setPassword("1491205");
        sqlserverDataSource.setUrl("jdbc:sqlserver://localhost:1433;databaseName=pgp");
        context.getRegistry().bind("sqlServerDb",sqlserverDataSource);


        /*
         * Execution Instructions
         * Generate list for the dynamic paths (RouteStep list)
         * This is the execution logic for camel
         */

        List<RouteStep> restListenerRouteSteps = new ArrayList<RouteStep>();

        RouteStep restRouteStep = new RouteStep(RouteStepTypes.FLEXIRestFrom);
        restRouteStep.setOption("port",8080);
        restRouteStep.setOption("enableCORS",false);
        restRouteStep.setOption("subdomain","/hw");
        restListenerRouteSteps.add( restRouteStep );

        restListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXILog,"${body[id]}"));
        restListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXISetSimpleBody,"Id: 1232"));
        restListenerRouteSteps.add( new RouteStep(RouteStepTypes.FLEXIRestFromResponce));

        RouteStepBuilder routeStepBuilder = new RouteStepBuilder(restListenerRouteSteps);
        context.addRoutes(routeStepBuilder);

        context.start();
    }



    @ShellMethod("dataConversionsTest")
    public void dataConversionsTest() throws Exception {

        ModelCamelContext context = new DefaultCamelContext();

        /*
         * Datasource
         * Creation of mysql datasource
         */
        BasicDataSource mysqlDataSource = new BasicDataSource();
        mysqlDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        mysqlDataSource.setUsername("root");
        mysqlDataSource.setPassword("");
        mysqlDataSource.setUrl("jdbc:mysql://localhost:3306/pgp");
        context.getRegistry().bind("mysqlDb",mysqlDataSource);


        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
                jacksonDataFormat.setPrettyPrint(false);

                from("timer:everysec?period=30000")
                        .to("sql:select u.* from user u where u.id in (select id from flexihistoryusers);?dataSource=#mysqlDb")
                        .log("Initial List<LinkedHashMap> data: ${body}")
                        .marshal(jacksonDataFormat)
                        .log("Converted to Json: ${body}")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                               String results = exchange.getIn().getBody(String.class);
                                log.info("Serialize to single String in processor: " + results);
                                exchange.getIn().setBody(results);
                            }
                        })
                        .log("Json serialized: ${body}")
                        .unmarshal(jacksonDataFormat)
                        .log("Json back to List<LinkedHashMap> original: ${body}")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                List<LinkedHashMap> objs = null;
                                objs = exchange.getIn().getBody(ArrayList.class);
                                for(LinkedHashMap cObj : objs)
                                {
                                   log.info("(Iterate All rows in one Processor) id: " + cObj.get("id").toString());
                                }
                            }
                        })
                        .split().body()
                        .log("After split List<LinkedHashMap> to LinkedHashMap ${body}")
                        .process(new Processor() {
                            @Override
                            public void process(Exchange exchange) throws Exception {
                                LinkedHashMap objs = null;
                                objs = exchange.getIn().getBody(LinkedHashMap.class);
                                log.info("id: " + objs.get("id").toString());
                                objs.put("New Value","Inserted new value");
                            }
                        })
                        .marshal(jacksonDataFormat)
                        .log("Converted LinkedHashMap back to Json : ${body}");
            }
        });

        context.start();
    }




}


