import helper.SlidingWindow;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.*;

public class RollingBolt extends BaseRichBolt
{
    private final SlidingWindow<Object> window ;
    private int windowLength ;
    private int frequencyEmit ;
    private OutputCollector collector ;

    public RollingBolt(int window , int frequency)
    {
        windowLength = window ;
        frequencyEmit = frequency ;
        this.window = new SlidingWindow<Object>(window / frequency) ;
    }
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector)
    {
        this.collector = collector ;
    }

    public void execute(Tuple input)
    {
        if(isTickTuple(input))
        {
            HashMap<Object , Long> res = window.getCountsThenAdvanceWindow() ;

            for(Object object : res.keySet())
                collector.emit(new Values(object , res.get(object) )) ;

        }
        else
        {
            window.addObject(input.getValue(0));
            collector.ack(input);
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("object", "count"));

    }
    private boolean isTickTuple(Tuple tuple)
    {
        return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID) && tuple.getSourceStreamId().equals(
                Constants.SYSTEM_TICK_STREAM_ID);
    }

    // Configuration for Tick Tuple
    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String,Object> conf = new HashMap<String , Object>();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, frequencyEmit);
        return conf;

    }
}
