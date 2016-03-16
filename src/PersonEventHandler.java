import com.lmax.disruptor.EventHandler;

/**
 * Created by liushan03 on 16/3/16.
 */
public class PersonEventHandler implements EventHandler<PersonEvent> {
    public PersonEventHandler() {

    }

    @Override
    public void onEvent(PersonEvent event, long sequence, boolean endOfBatch)throws Exception{
        Person person = event.getPerson();
        System.out.println("name = "+person.getName()+"  "+
                           "age = "+person.getAge()+"  "+
                           "gender = "+person.getGender()+"  "+
                           "mobile = "+person.getMobile()
        );
    }
}
