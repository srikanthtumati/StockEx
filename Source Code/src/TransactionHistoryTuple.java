/**
 * A tuple used to store transaction history
 * @author Srikanth Tumati
 * @version 1.0
 * @since 1.0
 * @param <K> a generic
 * @param <L> a generic
 * @param <M> a generic
 * @param <N> a generic
 * @param <O> a generic
 * @param <P> a generic
 */
public class TransactionHistoryTuple<K,L,M,N,O,P> {

    K val1;
    L val2;
    M val3;
    N val4;
    O val5;
    P val6;

    /**
     * initializes the TransactionHistory tuple
     * @param val1 the first value
     * @param val2 the second value
     * @param val3 the third value
     * @param val4 the fourth value
     * @param val5 the fifth value
     * @param val6 the sixth value
     */
    public TransactionHistoryTuple(K val1, L val2, M val3, N val4, O val5, P val6){
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
        this.val4 = val4;
        this.val5 = val5;
        this.val6 = val6;
    }

}
