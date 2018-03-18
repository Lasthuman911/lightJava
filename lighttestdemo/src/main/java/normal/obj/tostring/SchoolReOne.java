package normal.obj.tostring;

/**
 * Created by lszhen on 2018/1/25.
 */
public class SchoolReOne {

    public String schoolName;
    public String address;
    public int stuNum;

    public SchoolReOne(String schoolName, String address, int stuNum) {
        this.schoolName = schoolName;
        this.address = address;
        this.stuNum = stuNum;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStuNum() {
        return stuNum;
    }

    public void setStuNum(int stuNum) {
        this.stuNum = stuNum;
    }

    @Override
    public String toString() {
        return "SchoolReOne{" +
                "schoolName='" + schoolName + '\'' +
                ", address='" + address + '\'' +
                ", stuNum=" + stuNum +
                '}';
    }
}
