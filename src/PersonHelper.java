import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;

/**
 * Created by liushan03 on 16/3/16.
 */
public class PersonHelper {
    private static PersonHelper instance;
    private static boolean inited = false;

    private static final int BUFFER_SIZE = 256;
    private RingBuffer<PersonEvent> ringBuffer;
    private SequenceBarrier sequenceBarrier;
    private PersonEventHandler handler;
    private BatchEventProcessor<PersonEvent> batchEventProcessor;

    public PersonHelper(){
        ringBuffer = RingBuffer.createSingleProducer(PersonEvent.EVENT_FACTORY,
                                                BUFFER_SIZE,
                                                new YieldingWaitStrategy());

        ////创建SequenceBarrier
        sequenceBarrier = ringBuffer.newBarrier();

        ////创建消息处理器
        handler = new PersonEventHandler();
        batchEventProcessor = new BatchEventProcessor<PersonEvent>(ringBuffer,sequenceBarrier,handler);

    }

    public static void start(){
        instance = new PersonHelper();
        Thread thread = new Thread(instance.batchEventProcessor);
        thread.start();
        inited = true;
    }

    public static void shutdown(){
        if (!inited){
            throw  new RuntimeException("personhelper not inited!");
        }
        else{
            instance.doHalt();
        }
    }

    public static void produce(Person person){
        if (!inited){
            throw new RuntimeException("personhelper not inited!");
        }else{
            instance.doProduce(person);
        }
    }


    private void doHalt(){
        batchEventProcessor.halt();
    }

    private void doProduce(Person person){
        long sequence = ringBuffer.next();
        ringBuffer.get(sequence).setPerson(person);
        ringBuffer.publish(sequence);
    }

}
