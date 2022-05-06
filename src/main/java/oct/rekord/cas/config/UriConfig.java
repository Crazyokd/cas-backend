package oct.rekord.cas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Rekord
 * @date 2022/05/06 19:58
 */
@Data
@Component
@ConfigurationProperties(prefix = "my.config")
public class UriConfig {

    private List<String> includeUri;

    private List<String> excludeUri;

}
