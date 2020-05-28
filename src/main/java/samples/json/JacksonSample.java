package samples.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.IActor;
import base.Utility;

/**
 * JSON変換方法の比較
 */

public class JacksonSample implements IActor {
  static Logger log = LoggerFactory.getLogger(JacksonSample.class);

  // Jackson
  ObjectMapper mapper = new ObjectMapper();

  // テストパターン
  JsonCommonTestPattern testpattern = new JsonCommonTestPattern();

  /**
   * JSONパターン別動作確認
   * 
   * @param args
   */
  public void jacksonTestpattern(List<String> args) {
    log.info("=== jacksonTestpattern ===");
    String json;
    try {
      // JSON文字列->クラス->JSON文字列
      for (String item : args) {
        log.info("--- before ---");
        log.info(item);
        log.info("--- after ---");
        JsonMappingDummy hoge = mapper.readValue(item, JsonMappingDummy.class);
        json = mapper.writeValueAsString(hoge);
        log.info(String.format("%s", json));
      }
    } catch (JsonProcessingException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * ネストされたパラメータの取り方(body部がオブジェクト型)<br>
   * 
   * @param args
   */
  public void jacksonNestedMappingParameter(String arg) {
    log.info(String.format("=== %s ===", Utility.getMethodName()));
    log.info("--- before ---");
    log.info(arg);
    String json;
    try {
      JsonNode node = mapper.readTree(arg);
      json = mapper.writeValueAsString(node.get("body"));
      // JSON文字列->クラス->JSON文字列
      log.info("--- JsonNode ---");
      log.info(json);
      log.info("--- readValue ---");
      JsonMappingDummy hoge = mapper.readValue(json, JsonMappingDummy.class);
      json = mapper.writeValueAsString(hoge);
      log.info(json);

    } catch (JsonProcessingException e) {
      log.error(e.getOriginalMessage());
    }
  }

  @Override
  public void close() throws IOException {
    // Nothing ToDo

  }

  @Override
  public void action() throws Exception {
    jacksonTestpattern(testpattern.allPattern());
    jacksonNestedMappingParameter(testpattern.pattern(2));

  }

  @Override
  public void init() throws Exception {
    // Nothing ToDo

  }

  @Override
  public void terminate() throws Exception {
    // Nothing ToDo

  }

}