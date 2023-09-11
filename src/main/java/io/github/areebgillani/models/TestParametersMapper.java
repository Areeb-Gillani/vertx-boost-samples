package io.github.areebgillani.models;

/**
 * Mapper for {@link Test}.
 * NOTE: This class has been automatically generated from the {@link Test} original class using Vert.x codegen.
 */
@io.vertx.codegen.annotations.VertxGen
public interface TestParametersMapper extends io.vertx.sqlclient.templates.TupleMapper<Test> {

  TestParametersMapper INSTANCE = new TestParametersMapper() {};

  default io.vertx.sqlclient.Tuple map(java.util.function.Function<Integer, String> mapping, int size, Test params) {
    java.util.Map<String, Object> args = map(params);
    Object[] array = new Object[size];
    for (int i = 0;i < array.length;i++) {
      String column = mapping.apply(i);
      array[i] = args.get(column);
    }
    return io.vertx.sqlclient.Tuple.wrap(array);
  }

  default java.util.Map<String, Object> map(Test obj) {
    java.util.Map<String, Object> params = new java.util.HashMap<>();
    params.put("entryBy", obj.getEntryBy());
    params.put("entryTime", obj.getEntryTime());
    params.put("id", obj.getId());
    params.put("message", obj.getMessage());
    return params;
  }
}
