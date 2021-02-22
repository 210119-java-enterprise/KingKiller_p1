package JunitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.revature.kingkiller.scapers.ConfigScraper;
import com.revature.kingkiller.util.ConfigData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConfigScraperTest {
    private ConfigScraper configScraper;
    private ConfigData configData;

    @BeforeEach
    public void setUp() {
        configScraper = new ConfigScraper();
        configData = new ConfigData();
        configData.setPassword("Packers1!");
        configData.setUrl("jdbc:postgresql://revaturedb.cqu0vx8vy1y0.us-east-2.rds.amazonaws.com:5432/mydb");
        configData.setUsername("enewman10");
    }

    @Test
    @DisplayName("GetConfigurationDataFromFile")
    public void scrapeConfig() {
        assertEquals(configData.getUrl(), configScraper.ScrapeConfig("src/main/resources/KingKiller.cfg.xml").getUrl(),
                "URL did not match!");
        assertEquals(configData.getUsername(), configScraper.ScrapeConfig("src/main/resources/KingKiller.cfg.xml").getUsername(),
                "Username did not match!");
        assertEquals(configData.getPassword(), configScraper.ScrapeConfig("src/main/resources/KingKiller.cfg.xml").getPassword(),
                "Password did not match!");
    }

}
