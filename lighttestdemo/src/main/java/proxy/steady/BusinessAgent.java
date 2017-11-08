package proxy.steady;

import proxy.Sell;
import proxy.Vender;

/**
 *  静态代理
 *  @author light
 */
public class BusinessAgent implements Sell {
    private Vender mVender;

    public BusinessAgent(Vender mVender) {
        this.mVender = mVender;
    }

    @Override
    public void sell() {
        System.out.println("before");
        mVender.sell();
        System.out.println("after");
    }

    @Override
    public void ad() {
        System.out.println("before ad");
        mVender.ad();
        System.out.println("after ad");
    }

    @Override
    public int fk(int akNum) {
        System.out.println("before fk");
        int result = mVender.fk(788);
        System.out.println("after fk");
        return  result;
    }
}
