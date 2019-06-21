package com.flexi.model;

public enum RouteStepTypes {
    FLEXITo,
    FLEXISql,
    FLEXIFile,
    FLEXIDelay,
    FLEXILog,
    FLEXIRouter,
    FLEXISetBody,
    FLEXISplit,
    FLEXIProcessor,

    FLEXIRestFrom,
  //  FLEXIRestFromConfig,
    FLEXIRestFromResponce,
    FLEXIRestTo,

    FLEXIMqttFrom,
    FLEXIMqttTo,

    FLEXITimerFrom,

    FLEXIHashmapToJson,
    FLEXIJsonToHashmap,
    FLEXIEncrypt,
    FLEXIDecrypt,
    FLEXISetSimpleBody

}
