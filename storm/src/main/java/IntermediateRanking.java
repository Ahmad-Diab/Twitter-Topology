
import helper.*;

import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;


public  class IntermediateRanking extends BaseRichBolt
{
    private int frequencyEmit ;
    private int topN ;
    private OutputCollector collector ;
    private final  Ranking ranking;

    public IntermediateRanking(int topN, int frequency) {
        this.topN = topN ;
        frequencyEmit = frequency ;
        ranking = new Ranking(topN) ;
    }
    public Ranking getRanking() {return ranking ;}

    public void updateRankings(Tuple tuple) {
        Rankable r = RankableObjects.createRankable(tuple) ;
        ranking.updateWith(r);
    }

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector ;
    }

    public void execute(Tuple input)
    {
        if(isTickTuple(input))
            collector.emit(new Values(ranking.copy())) ;
        else
            updateRankings(input);
    }

    private boolean isTickTuple(Tuple tuple)
    {
        return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID) && tuple.getSourceStreamId().equals(
                Constants.SYSTEM_TICK_STREAM_ID);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("ranking"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String,Object> conf = new HashMap<String , Object>();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, frequencyEmit);
        return conf;
    }

}
