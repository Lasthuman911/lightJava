package proxy.cglib;

import proxy.Sell;
import proxy.Vender;

import javax.print.attribute.standard.SheetCollate;

public class CgLibClient {
    public static void main(String[] args) {
//        CGLibProxy proxy = new CGLibProxy();
//        Sell sell = proxy.getProxy(Vender.class);
        //单例模式重构
        Sell sell = CGLibProxy.getInstance().getProxy(Vender.class);
        sell.ad();
    }
}
