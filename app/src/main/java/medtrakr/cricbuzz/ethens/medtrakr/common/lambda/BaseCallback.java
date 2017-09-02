package medtrakr.cricbuzz.ethens.medtrakr.common.lambda;

@FunctionalInterface public interface BaseCallback<T, V> {
  T onResponse(V from);
}
