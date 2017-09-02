package medtrakr.cricbuzz.ethens.medtrakr.common.lambda;

@FunctionalInterface public interface ParameterCallback<T> {
  void onResponse(T t);
}
