package com.weiwei.controller.common;

public class Constants extends com.weiwei.parent.common.base.Constants{
	private static final String API_PATH = "/weiwei";
	private static final String SECURE_API_PATH = "/sweiwei";

    public static final String AUTHENTICATE_URL = SECURE_API_PATH + "/authenticate";
    public static final String STUFF_URL = API_PATH + "/stuff";
    public static final String REGISTER_URL = SECURE_API_PATH + "/register";
    public static final String REGISTER_CODE_URL = SECURE_API_PATH + "/registerCode";

    // Spring Boot Actuator services
    public static final String AUTOCONFIG_ENDPOINT = "/autoconfig";
    public static final String BEANS_ENDPOINT = "/beans";
    public static final String CONFIGPROPS_ENDPOINT = "/configprops";
    public static final String ENV_ENDPOINT = "/env";
    public static final String MAPPINGS_ENDPOINT = "/mappings";
    public static final String METRICS_ENDPOINT = "/metrics";
    public static final String SHUTDOWN_ENDPOINT = "/shutdown";
}
