package com.citihub.configr.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import com.citihub.configr.namespace.MongoNamespaceQueries;
import com.citihub.configr.namespace.Namespace;
import com.mongodb.client.MongoClient;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class TestConfigurationController {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MongoClient mongoClient;

  @MockBean
  private MongoNamespaceQueries nsQueries;
  
  @MockBean
  private ConfigurationRepository configurationRepository;

  @BeforeAll
  public void setupMock() {
    when(configurationRepository.findNamespaceById("/configuration"))
        .thenReturn(Optional.of(new Namespace("foo", "bar", null)));
  }

  @Test
  public void testNotFound() throws Exception {
    mockMvc.perform(get("/foo/bar/baz")).andDo(print()).andExpect(status().isNotFound());
  }

  @Test
  public void testGetBadRequest() throws Exception {
    mockMvc.perform(get("/configuration")).andDo(print()).andExpect(status().isBadRequest());
  }

  @Test
  public void testGetValid() throws Exception {
    mockMvc.perform(get("/configuration/x")).andDo(print()).andExpect(status().isOk());
  }
  
}
