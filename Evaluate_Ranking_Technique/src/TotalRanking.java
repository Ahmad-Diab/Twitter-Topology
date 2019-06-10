public class TotalRanking
{
    private final Ranking ranking ;
    private int topN ;
    TotalRanking(int topN) {
        this.topN = topN ;
        ranking = new Ranking(topN) ;
    }

    public Ranking getRanking(){return ranking;}

    public void updateRanking(Ranking tuple) {
        ranking.updateWith(tuple);
    }

    public Ranking execute() { return ranking.copy() ; }

}
