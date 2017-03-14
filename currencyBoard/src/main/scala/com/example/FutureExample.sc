import scala.concurrent.Future

trait VoteApi
{

  def vote(name:String,message:String):Future[Boolean] = ???


}

class MyVoteUsage
{


  def max(names:Seq[String], message:String,voteApi: VoteApi):Future[Boolean] = {
   ???
  }

  def consensus(names:List[String],voteApi:VoteApi): Future[Boolean] = ???

  def futureSequence[A](seq:Seq[Future[A]]):Future[Seq[A]]=
  {
???
  }

}