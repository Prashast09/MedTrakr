package medtrakr.cricbuzz.ethens.medtrakr.common.lambda;

public class TwoParamReturn<K, V> {
    private K firstParam;
    private V secondParam;

    public TwoParamReturn(K firstParam, V secondParam) {
        this.firstParam = firstParam;
        this.secondParam = secondParam;
    }


    public K getFirstParam() {
        return firstParam;
    }

    public void setFirstParam(K firstParam) {
        this.firstParam = firstParam;
    }

    public V getSecondParam() {
        return secondParam;
    }

    public void setSecondParam(V secondParam) {
        this.secondParam = secondParam;
    }
}
