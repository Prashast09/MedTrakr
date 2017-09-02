package medtrakr.cricbuzz.ethens.medtrakr.common.lambda;

import java.io.Serializable;

@FunctionalInterface
public interface VoidCallback extends Serializable {
    void onResponse();
}
