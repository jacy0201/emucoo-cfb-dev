package com.emucoo.restApi.config;

import com.emucoo.utils.GaodeGeoMap;
import com.emucoo.utils.WaterMarkUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by tombaby on 2018/3/30.
 */
@Configuration
public class ThirdToolsConfig {

    @Bean
    public GaodeGeoMap gaodeGeoMap() {
        return new GaodeGeoMap();
    }

    @Bean
    public WaterMarkUtils waterMarkUtils() {
        return new WaterMarkUtils();
    }
}
