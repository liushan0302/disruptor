import com.lmax.disruptor.EventHandler;

/**
 * Created by liushan03 on 16/3/21.
 */
public class TwoHandler implements EventHandler<PersonEvent> {
    public TwoHandler(){

    }

    @Override
    public void onEvent(PersonEvent event, long sequence, boolean endOfBatch)throws Exception{
        Person person = event.getPerson();
        System.out.println("hello world!"+event.getPerson().getName());
    }
}
