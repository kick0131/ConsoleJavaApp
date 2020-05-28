package samples.json;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.IActor;
import base.Utility;

/**
 * JSON変換方法の比較
 */

public class GsonSample implements IActor {
  static Logger log = LoggerFactory.getLogger(GsonSample.class);

  // GSON
  Gson gson = new Gson();

  // テストパターン
  JsonCommonTestPattern testpattern = new JsonCommonTestPattern();

  /**
   * ネストされたパラメータの取り方(body部がオブジェクト型)<br>
   * 
   * @param args
   */
  public void gsonNestedMappingParameter(String arg) {
    log.info(String.format("=== %s ===", Utility.getMethodName()));
    log.info("--- before ---");
    log.info(arg);
    JsonObject jsonobj = gson.fromJson(arg, JsonObject.class);

    // 型が明確になっている場合の記載
    // JSONがオブジェクト型の場合、直接fromJson()でマッピングを行う
    log.info("--- fromJson ---");
    JsonMappingDummy mapping = gson.fromJson(jsonobj.get("body"), JsonMappingDummy.class);
    String json = gson.toJson(mapping);
    log.info(json);
  }

  /**
   * ネストされたパラメータ＋文字列の場合の取り方<br>
   * このケースはJacksonNG、GSONを使用する
   * 
   * @param args
   */
  public void gsonApiGatewayRequest(String arg) {
    log.info(String.format("=== %s ===", Utility.getMethodName()));
    log.info(arg);

    // JsonObjectで目的のJSON経由でマッピングする場合
    log.info("--- use JsonObject ---");
    JsonObject jsonobj = gson.fromJson(arg, JsonObject.class);
    // 型が明確になっている場合の対応
    // get()で目的のJsonElementを取得し、getAsXXXで型変換
    // fromJson()でエスケープ文字を取り除いてマッピング
    JsonMappingDummy mapping = gson.fromJson(jsonobj.get("body").getAsString(), JsonMappingDummy.class);
    log.info(mapping.toString());
  }

  /**
   * インスタンス->JSON文字列
   */
  public void gsonObjectToString() {
    log.info(String.format("=== %s ===", Utility.getMethodName()));

    JsonMappingDummy dummy = new JsonMappingDummy();
    dummy.setName("Tom");
    dummy.setAge(10);

    log.info("--- fromJsonString ---");
    log.info(gson.toJson(dummy));
  }

  @Override
  public void close() throws IOException {
    // Nothing ToDo
  }

  /**
   * メイン処理
   */
  @Override
  public void action() throws Exception {
    gsonNestedMappingParameter(testpattern.pattern(2));
    gsonApiGatewayRequest(testpattern.pattern(3));
    gsonObjectToString();
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