package td.spec.muji.manager.model.constants;

public enum RequestMethodEnum {

  GET("Get"),
  POST("Post"),
  DELETE("Delete"),
  PUT("Put"),

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
