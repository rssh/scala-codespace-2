import com.example._

import java.util.concurrent.Executors
import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}

import scala.concurrent.{Future, Promise}
import scala.util.{Random, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.FiniteDuration


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


object MyVoteUsage
{

  def firstVoteWithinTimeout()= ???

  def firstVote(names:Seq[String], message:String, voteApi:VoteApi):Future[Option[Boolean]] =
  {
    val votes: Seq[Future[Boolean]] = names map (name => voteApi.vote(name, message))

    val promise = Promise[Option[Boolean]]()

    votes.foreach{ f =>
      f.onComplete {
        r => if (!promise.isCompleted)
          promise.tryComplete(r.map(x => Option(x))) }
    }

    promise.future
  }


  def nVotes(names:Seq[String], message:String, voteApi: VoteApi, timeout: FiniteDuration):Future[Int] = {

    val nSuccesses = new AtomicInteger(0)
    val nCompleted = new AtomicInteger(0)
    val scheduler = Executors.newSingleThreadScheduledExecutor()
    val p = Promise[Int]()

    val votes: Seq[Future[Boolean]] = names map (name => voteApi.vote(name, message) withTimeout timeout)
    votes.foreach {
      f => f.onComplete { t: Try[Boolean] =>
        t match {
          case Success(r) => nSuccesses.incrementAndGet()
          case _ =>
        }
        val n = nCompleted.incrementAndGet()
        if (n == names.size) {
          p.trySuccess(nSuccesses.get())
        }
      }
    }

    p.future

  }

  def maxVote(names:Seq[String], message:String,voteApi: VoteApi):Future[Option[Boolean]] = {
   val fseq:Future[Seq[Boolean]] = futureSequence(names map( name => voteApi.vote(name,message)))

   for(seq <- fseq) yield {
     val grouped = seq.groupBy(x => x).mapValues(_.size)
     val trueVotes = grouped.getOrElse(true,0)
     val falseVotes = grouped.getOrElse(false,0)
     if (trueVotes == falseVotes) {
       None
     }  else
       Some(trueVotes > falseVotes)
   }

  }


  def consensus(names:List[String],voteApi:VoteApi): Future[Boolean] = {
    ???
  }

  def futureSequence[A](seq:Seq[Future[A]]):Future[Seq[A]]=
  {
    val s0: Future[Seq[A]] = Future successful Seq()
    seq.foldLeft(s0){ (s,e) =>
      //e.flatMap( ev => s.map(sv=> ev +: sv ) )
      for{
        sv <- s
        ev <- e
      } yield ev +: sv
    }
  }


}

/*
val s1 = Seq(Future successful 1, Future successful 2)

val s1r = MyVoteUsage.futureSequence(s1)

s1r.onComplete(System.out.println )

val s2 = Seq(Future successful 1, Future failed new RuntimeException("AAA"))

val s2r = MyVoteUsage.futureSequence(s2)

s2r.onComplete(System.out.println)
*/

val voteApi = new VoteApi()

val r = MyVoteUsage.maxVote(Seq("a","b"),"ABA",voteApi)


r.onComplete(System.out.println)