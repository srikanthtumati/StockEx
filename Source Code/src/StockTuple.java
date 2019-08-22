/**
 * Stock Tuple is a tuple containing all stock information
 * @author Srikanth Tumati
 * @version 1.0
 * @since 1.0
 * @param <K> a generic
 * @param <L> a generic
 * @param <M> a generic
 * @param <N> a generic
 */
public class StockTuple<K, L, M, N>{

    private K val1;
    private L val2;
    private M val3;
    private N val4;

    /**
     * creates the stocktuple object
     * @param val1 the first value
     * @param val2 the second value
     * @param val3 the third value
     * @param val4 the fourth value
     */
    public StockTuple(K val1, L val2, M val3, N val4){
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
    }

    /**
     * gets the first value
     * @return val1
     */
    public K getVal1(){
        return this.val1;
    }

    /**
     * gets the second value
     * @return val2
     */
    public L getVal2(){
        return this.val2;
    }

    /**
     * gets the third value
     * @return val3
     */
    public M getVal3(){
        return this.val3;
    }

    /**
     * gets the fourth value
     * @return val4
     */
    public N getVal4(){
        return this.val4;
    }

}
