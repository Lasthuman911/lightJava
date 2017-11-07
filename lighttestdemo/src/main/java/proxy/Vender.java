package proxy;

public class Vender implements Sell {
    @Override
    public void sell() {
        System.out.println("sell method");
    }
    @Override
    public void ad() {
        System.out.println("ad method");
    }
}
