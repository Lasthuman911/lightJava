package pattern.factory.simplefactory;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

/**
 * @author lszhen
 * @date 2018/3/22.
 */
public class Apple implements Fruit{
    @Override
    public void getName(){
        System.out.println("我是苹果");
    }
}
