package td.spec.muji.manager.model.constants;

public enum RequestMethodEnum {

  GET("doGet"),
  POST("doPost"),
  DELETE("doDelete"),
  PUT("doPut"),

  ;

  private String name;

  RequestMethodEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
