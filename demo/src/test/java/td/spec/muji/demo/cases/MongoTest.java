package td.spec.muji.demo.cases;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import lombok.extern.slf4j.Slf4j;

import org.bson.Document;
import org.bson.json.Converter;
import org.bson.json.JsonWriterSettings;
import org.bson.json.StrictJsonWriter;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import td.spec.muji.demo.conf.MConfig;
import td.spec.muji.demo.conf.test.RetryAnalyzer;
import td.spec.muji.demo.entity.KLine;


/**
 * Created by zhish1228 on 2018/8/1.
 */
@Slf4j
public class MongoTest {

  private MongoCollection<Document> kLineCollection;

  private final String code = "spec001";

  @BeforeClass
  public void setUp(ITestContext context) {

    // 全局添加重试监听器
    for (ITestNGMethod method : context.getAllTestMethods()) {
      method.setRetryAnalyzer(new RetryAnalyzer());
    }

    log.info("===============MongoDB初始化========================");
    MongoClientOptions options = new MongoClientOptions.Builder()
        .cursorFinalizerEnabled(true)
        .connectTimeout(3000)
        .socketTimeout(0)
        .threadsAllowedToBlockForConnectionMultiplier(5000)
        .build();
    MongoCredential credential = MongoCredential.createCredential(MConfig.userName, MConfig.dataBase, MConfig.passWord);
    // 测试环境db 需要支持登录校验
    // MongoClient mongoClient = new MongoClient(new ServerAddress(MConfig.ip, MConfig.port), credential, options);
    // 本地测试环境只需要ip port
    MongoClient mongoClient = new MongoClient(new ServerAddress("192.168.201.229", 27017));
    MongoDatabase database = mongoClient.getDatabase(MConfig.dataBase);
    kLineCollection = database.getCollection(MConfig.collectionName);
  }

  @Test(description = "增")
  public void c() throws Exception {

    long beforeTest = kLineCollection.countDocuments();

    // 新建存储的kline对象
    KLine k1 = new KLine();
    k1.setCode(code);
    k1.setOpen(1L);
    k1.setClose(2L);
    kLineCollection.insertOne(Document.parse(JSONObject.toJSONString(k1)));

    // gson形式插入
    KLine k2 = new KLine();
    k2.setCode(code);
    k2.setOpen(1L);
    k2.setClose(2L);

    List<Document> documentList = new ArrayList<>();
    documentList.add(Document.parse(JSONObject.toJSONString(k2)));
    kLineCollection.insertMany(documentList);

    long afterTest = kLineCollection.countDocuments();
    // 测试过后插入了两条数据
    Assert.assertEquals(afterTest - beforeTest, 2);

  }

  @Test(description = "查")
  public void r() {

    // 表数据总数
    log.info("总数量:" + kLineCollection.countDocuments());
    // 查询条件*2
    BasicDBObject queryObject = new BasicDBObject("code", "A90001").append("amount", 376420L);
    FindIterable<Document> documents = kLineCollection.find(queryObject);
    MongoCursor<Document> iterator = documents.iterator();
    while (iterator.hasNext()) {
      // https://stackoverflow.com/questions/35209839/converting-document-objects-in-mongodb-3-to-pojos
      // 修正转换格式
      JsonWriterSettings settings = JsonWriterSettings.builder().int64Converter(new Converter<Long>() {
        @Override
        public void convert(Long value, StrictJsonWriter writer) {
          writer.writeNumber(value.toString());
        }
      }).build();
      String s = iterator.next().toJson(settings);
      KLine kLine = JSONObject.parseObject(s, KLine.class);

      log.info("kline code is:" + kLine.getCode());
    }
  }

  @Test(description = "改")
  public void u() {
    r();
    Random random = new Random();
    int ran = random.nextInt(100);
    log.info("ran is :" + ran);
    kLineCollection.updateMany(Filters.eq("code", "A90001"), new Document("$set", new Document("period", ran)));
    r();
  }

  @Test(description = "删")
  public void d() {

    long beforeTest = kLineCollection.countDocuments();
    kLineCollection.deleteMany(Filters.eq("code", code));
    long afterTest = kLineCollection.countDocuments();
    log.info("beforeTest is:" + beforeTest);
    log.info("afterTest is:" + afterTest);
  }

  /**
   * 1.拿到resp json 2、转换成bean （新建一个bean的类） 3、断言
   */

  @Test
  public void jsonPathTest() throws Exception {
    log.info("starttttttttttttttt");
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url("https://www.sojson.com/open/api/weather/json.shtml?city=%E5%8C%97%E4%BA%AC")
        .get()
        .build();

    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      String respBody = response.body().string();
//      log.info(respBody);
      JSONObject res = JSONObject.parseObject(respBody);
      Object eval = JSONPath.eval(res, "$.data");

      log.info(JSONObject.toJSONString(eval));

      int size = JSONPath.size(res, "$.data");
      log.info(String.valueOf(size));

    } else {
      throw new IOException("Unexpected code " + response);
    }
  }
}
