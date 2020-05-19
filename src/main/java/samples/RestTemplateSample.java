package samples;

import java.io.IOException;

import org.springframework.web.client.RestTemplate;

import base.IActor;

/**
 * RestTemplateお試し
 */
public class RestTemplateSample implements IActor {
  private RestTemplate restTemplate = null;

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void action() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void init() throws Exception {
    // TODO Auto-generated method stub
    restTemplate = null;

  }

  @Override
  public void terminate() throws Exception {
    // TODO Auto-generated method stub

  }

}