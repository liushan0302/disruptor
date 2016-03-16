/**
 * Created by liushan03 on 16/3/16.
 */
public class test {
    public static void main(String[] args){
        PersonHelper.start();
        for(int i=0 ; i<20; i++){
            Person p = new Person("shaodong"+i, i , "男", "1234566"+i);

            //生产者生产数据
            PersonHelper.produce(p);
        }
    }
}
