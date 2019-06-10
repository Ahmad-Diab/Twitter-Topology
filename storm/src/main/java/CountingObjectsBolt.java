import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class CountingObjectsBolt extends BaseRichBolt
{
    private OutputCollector collector ;
    private HashMap<String , Long> map ;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector)
    {
        this.collector = collector ;
        map = new HashMap<String, Long>() ;
    }

    public void execute(Tuple input)
    {
        String word = input.getStringByField("tweet-word");

        if(map.get(word) == null)
            map.put(word , 1l) ;
        else
            map.put(word , map.get(word) + 1) ;

        collector.emit(new Values(word , map.get(word))) ;
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word","count"));
    }
}