

package org.study.gift.core.common.property;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class YamlEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            String[] activeProfiles = environment.getActiveProfiles();
            for (String profile : activeProfiles) {
                List<Resource> resources =
                        Arrays.asList(resourcePatternResolver.getResources(resolveResourcePattern(profile)));
                resources.stream()
                         .map(this::loadYaml)
                         .filter(Objects::nonNull)
                         .flatMap(it -> it.stream())
                         .forEach(it -> environment.getPropertySources().addLast(it));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String resolveResourcePattern(String profile) {
        return MessageFormat.format("classpath*:*-{0}.yml", profile);
    }

    private List<PropertySource<?>> loadYaml(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("Resource " + resource + " does not exist");
        }
        try {
            return loader.load(resource.getURL().toString(), resource);
        } catch (IOException ex) {
            throw new IllegalStateException(
                    "Failed to load yaml configuration from " + resource, ex);
        }
    }
}
