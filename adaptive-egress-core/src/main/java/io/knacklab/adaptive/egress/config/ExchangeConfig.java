package io.knacklab.adaptive.egress.config;

import io.knacklab.adaptive.egress.core.ExchangeSpec;
import io.knacklab.adaptive.egress.exception.NotFoundException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Configuration
@EnableWebFlux
@NoArgsConstructor
@ConfigurationProperties(prefix = "egress")
@ComponentScan("io.knacklab.adaptive.egress")
public class ExchangeConfig {
    private static final Log logger = LogFactory.getLog(ExchangeConfig.class);
    private static final String FORMAT_NO_EXCHANGE_FOUND = "No exchange found with given name - [%s]";
    private final StaticListableBeanFactory exchangeFactory = new StaticListableBeanFactory();
    private List<ExchangeSpec> exchanges = new ArrayList<>();
    private List<ExchangeSpec> ex = new ArrayList<>();

    public String getFullName(String shortName) {
        final Optional<ExchangeSpec> result = this.exchanges.stream()
                .filter(spec -> spec.isMatched(shortName))
                .findFirst();
        if (result.isPresent()) {
            return result.get().getFullName();
        }
        throw new NotFoundException(String.format(FORMAT_NO_EXCHANGE_FOUND, shortName));
    }

    public ExchangeSpec getSpec(String fullName) {
        if (exchangeFactory.getBeanDefinitionCount() == 0) {
            this.exchanges.forEach(spec ->
                    exchangeFactory.addBean(spec.getName(), spec));
        }
        try {
            return exchangeFactory.getBean(
                    ExchangeSpec.getShortName(fullName), ExchangeSpec.class);
        } catch (Exception e) {
            if (ExchangeConfig.logger.isTraceEnabled()) {
                ExchangeConfig.logger.trace(e.getMessage());
            }
            throw new NotFoundException(e.getMessage());
        }
    }
}
