package com.tongdao.cases;

import com.google.gson.Gson;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.td.spec.marketdata.common.entity.KLine;
import com.tongdao.conf.MConfig;

import lombok.extern.slf4j.Slf4j;

import org.bson.Document;
import org.bson.json.Converter;
import org.bson.json.JsonWriterSettings;
import org.bson.json.StrictJsonWriter;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by zhengda on 2018/8/1.
 */
@Slf4j
public class MongoTest {

  private MongoCollection<Document> kLineCollection;

  private final String code = "spec001";

  @BeforeClass
  public void setuP() {

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
    MongoClient mongoClient = new MongoClient(new ServerAddress("192.168.201.229", 27017));
    MongoDatabase database = mongoClient.getDatabase(MConfig.dataBase);
    kLineCollection = database.getCollection(MConfig.collectionName);
  }

  @Test(description = "增")
  public void c() throws Exception {

    long beforeTest = kLineCollection.countDocuments();

    // jackson方式toJson
    ObjectMapper mapper = new ObjectMapper();
    // 新建存储的kline对象
    KLine k1 = new KLine();
    k1.setCode(code);
    k1.setOpen(1L);
    k1.setClose(2L);
    kLineCollection.insertOne(Document.parse(mapper.writeValueAsString(k1)));

    // gson形式插入
    KLine k2 = new KLine();
    k2.setCode(code);
    k2.setOpen(1L);
    k2.setClose(2L);

    Gson gson = new Gson();
    String kJson = gson.toJson(k2);

    List<Document> t = new ArrayList<>();
    t.add(Document.parse(kJson));
    kLineCollection.insertMany(t);

    long afterTest = kLineCollection.countDocuments();
    Assert.assertEquals(afterTest - beforeTest, 2);

  }

  @Test(description = "查")
  public void r() {

    // 表数据总数
    log.info("总数量:" + kLineCollection.countDocuments());
    // 查询条件
    BasicDBObject queryObject = new BasicDBObject("code", "A90001").append("amount", 376420L);
    FindIterable<Document> documents = kLineCollection.find(queryObject);
    MongoCursor<Document> iterator = documents.iterator();
    while (iterator.hasNext()) {
      // https://stackoverflow.com/questions/35209839/converting-document-objects-in-mongodb-3-to-pojos
      JsonWriterSettings settings = JsonWriterSettings.builder().int64Converter(new Converter<Long>() {
        @Override
        public void convert(Long value, StrictJsonWriter writer) {
          writer.writeNumber(value.toString());
        }
      }).build();

      String s = iterator.next().toJson(settings);
      Gson g = new Gson();
      KLine o = g.fromJson(s, KLine.class);
      log.info(o.getCode());
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
    log.info("beforeTest is" + beforeTest);
    log.info("afterTest is" + afterTest);

  }
}
