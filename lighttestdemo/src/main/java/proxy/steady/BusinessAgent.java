package proxy.steady;

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
}
