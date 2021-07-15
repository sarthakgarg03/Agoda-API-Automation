package com.apiautomation.environmentconfiguration;

import com.apiautomation.utils.ParseConfigFile;
import com.apiautomation.utils.apiutils.TestAPIBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;

public class EnvVariablesConfig {

        private final static Logger logger = LoggerFactory.getLogger(EnvVariablesConfig.class);

    public static String environment;
    public static String testingType;
    public static String baseEnvFilePath;
    public static String baseEnvFileName;


    private static Map<String, String> environmentProperties = new HashMap<>();

    /**
     *
     * @param TestingType - to define testing type - Regression/Smoke
     * @param Environment - to define env to executable - dev/test/prod
     */
    @BeforeSuite
    @Parameters({"TestingType", "Environment"})
    public void configureEnvironmentProperties(String TestingType, String Environment) {

        this.environment = System.getProperty("Environment");
        this.testingType = System.getProperty("TestingType");

        if(this.environment==null){
            this.environment=Environment;
        }
        if(this.testingType==null){
            this.testingType = TestingType;
        }

        logger.info("environment: "+ this.environment +" TestingType: "+ this.testingType);

        baseEnvFilePath = "Config//"+testingType.toLowerCase()+"//"+environment.toLowerCase()+"//EnvironmentConfig";
        baseEnvFileName = "environment.cfg";

       environmentProperties = ParseConfigFile.getAllDefaultProperties(baseEnvFilePath, baseEnvFileName);
       logger.info("Reading Environment.Config file from location: "+ baseEnvFilePath+"//"+ baseEnvFileName);

        TestAPIBase.setUp();

    }

    /**
     *
     * @param key key define in environment.cfg file in default section
     * @return  value of key
     */
    public static String getEnvironmentProperty(String key) {
        return environmentProperties.get(key.toLowerCase());
    }







}
