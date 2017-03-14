import scala.concurrent.Future
import scala.util.Random


class VoteApi
{


  val random = new Random(1)

  def vote(name:String,message:String):Future[Boolean] =
  {
    // submit task to execution context
    Future{ random.nextBoolean() }

    // return successful in this thread
    //Future successful random.nextBoolean()

    //Future failed new IllegalStateException("AAA")
  }


}

class MyVoteUsage
{


  def maxVote(names:Seq[String], message:String,voteApi: VoteApi):Future[Boolean] = {
   ???
  }


  def consensus(names:List[String],voteApi:VoteApi): Future[Boolean] = ???

  def futureSequence[A](seq:Seq[Future[A]]):Future[Seq[A]]=
  {
???
  }

}