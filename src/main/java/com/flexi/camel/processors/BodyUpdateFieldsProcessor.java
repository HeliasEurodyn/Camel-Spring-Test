package com.flexi.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.ArrayList;
import java.util.List;

public class BodyUpdateFieldsProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {


           List<LinkedCaseInsensitiveMap<String>> objs = null;

           objs = exchange.getIn().getBody(ArrayList.class);

            for(LinkedCaseInsensitiveMap<String> cObj : objs)
                {
                    System.out.println(cObj);
                    cObj.put("username","ilias");
                    cObj.get("username");
                  //  System.out.println(cObj.get("username"));
                  //  if(EntitiesSerialized == "") EntitiesSerialized = dummyCamelEntity.toString();
                  //  else EntitiesSerialized += "," + dummyCamelEntity.toString();
                }

         //   List<DummyCamelEntity> dummyCamelEntities = message.getBody(ArrayList.class);

        //    String EntitiesSerialized = "";

                       /* for(DummyCamelEntity dummyCamelEntity : dummyCamelEntities)
                        {
                            if(EntitiesSerialized == "") EntitiesSerialized = dummyCamelEntity.toString();
                            else EntitiesSerialized += "," + dummyCamelEntity.toString();
                        }


                      for(int i =0; i< dummyCamelEntities.size();i++)
                      {
                          if(EntitiesSerialized == "") EntitiesSerialized = dummyCamelEntities.get(i).toString();
                          else EntitiesSerialized += "," + dummyCamelEntities.get(i).toString();
                      }

                        message.setBody(EntitiesSerialized);
                   //    message.setBody("SQL");
                    }*/
       // })


    }


}
