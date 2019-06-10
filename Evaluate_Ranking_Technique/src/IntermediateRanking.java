public class IntermediateRanking
{
    private Ranking ranking ;
    private int topN ;
    IntermediateRanking(int topN) {
        this.topN = topN ;
        ranking = new Ranking(topN) ;
    }

    public Ranking getRanking(){return ranking;}

    public void updateRanking(Tuple tuple)
    {
        Rankable rankable = RankableObjects.createRankable(tuple);
        ranking.updateWith(rankable);
    }

    public Ranking execute() { return getRanking(); }

}
