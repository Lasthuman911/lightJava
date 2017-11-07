package proxy.steady;

/**
 * 静态代理测试
 */
public class Client {
    public static void main(String[] args) {
        BusinessAgent businessAgent = new BusinessAgent(new Vender());
        businessAgent.sell();
        businessAgent.ad();
    }
}
