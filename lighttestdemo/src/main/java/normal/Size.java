package normal;

/**
 * Created by lszhen on 2018/1/26.
 */
public enum Size {
    //TODO s m l 有啥作用？
    SMALL("S"),MEDIUM("M"),LARGE("L");

    private String name;

    Size(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
