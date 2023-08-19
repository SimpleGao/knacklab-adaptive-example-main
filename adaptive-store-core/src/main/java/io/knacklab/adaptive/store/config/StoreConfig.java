package io.knacklab.adaptive.store.config;

import io.knacklab.adaptive.store.core.StoreSpace;
import io.knacklab.adaptive.store.expection.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties("knack")
@ComponentScan("io.knacklab.adaptive.store")
public class StoreConfig {
    private static final Log logger = LogFactory.getLog(StoreConfig.class);
    private static final String FORMAT_NO_STORE_FOUND = "No store found with given name - [%s]";
    private final StaticListableBeanFactory storeFactory = new StaticListableBeanFactory();
    private List<StoreSpace> stores = new ArrayList<>();

    public String getFullName(String shortName) {
        val result = this.stores.stream().filter(spec -> spec.isMatched(shortName))
                .findFirst();
        if (result.isPresent()) {
            return result.get().getFullName();
        }
        throw new NotFoundException(String.format(FORMAT_NO_STORE_FOUND, shortName));
    }

    public StoreSpace getSpec(String fullName) {
        if (storeFactory.getBeanDefinitionCount() == 0) {
            this.stores.forEach(spec -> storeFactory.addBean(spec.getName(), spec));
        }
        try {
            return storeFactory.getBean(StoreSpace.getShortName(fullName), StoreSpace.class);
        } catch (Exception e) {
            if (StoreConfig.logger.isTraceEnabled()) {
                StoreConfig.logger.trace(e.getMessage());
            }
            throw new NotFoundException(e.getMessage());
        }
    }
}
