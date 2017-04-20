package com.example.evolution


import scala.util.Random

trait EvolutionOps
{

   type Carrier

   type HromoSet

   type Population = Seq[HromoSet]

   type RankedPopulation = Seq[(HromoSet,Double)]

   def isCorrect(carrier: Carrier, hromoSet: HromoSet): Boolean

   def rank(carrier:Carrier, hromoSet: HromoSet): Double

   def mutate(carrier:Carrier,origin:HromoSet): Seq[HromoSet]

   def crossover(carrier: Carrier, x:HromoSet, y: HromoSet): Seq[HromoSet]

   def step(carrier:Carrier, population: Population): Population  =
   {
      for{ i <- 0 until population.size
           xi = random.nextInt(population.size)
           yi = random.nextInt(population.size)
           r <- crossover(carrier,population(xi),population(yi))
      } yield r
   }

   def run(carrier: Carrier, init: Population, targetSize: Int):Either[String,Population] =
   {
      val epsilon = 0.01
      val finalPlatoLen = 3
      val maxSteps = 100

      var stop = false
      var success = false
      var nSteps = 0
      var platoSteps = 0
      var rankf = rank(carrier)
      var prevAvgRank = avgRank(init.map(rankf))

      if (! init.forall{x =>
           val r = isCorrect(carrier,x)
           if (!r) Console.print(s"incorect init ${x}")
           r
        } ) {
        return Left("init not correct")
      }

      var current = init
      while(!stop) {
        // TODO: thins about
       var next = (current ++ step(carrier,current)).map(rankf).sortBy(_._2).take(targetSize)
       var nextAvgRank = avgRank(next)

       if (Math.abs(prevAvgRank - nextAvgRank) < epsilon) {
          platoSteps += 1
          if (platoSteps >= finalPlatoLen) {
             stop = true
             success = true
          }
       } else {
          platoSteps = 0
       }
       nSteps += 1
       if (nSteps >= maxSteps) {
          stop = true
       }
       Console.println(s"nSteps = ${nSteps}, avgRank = ${nextAvgRank}")
     }
     if (success) {
        Right(current)
     } else {
        Left(s"Plato not reached during $nSteps steps")
     }
   }

   def rank(carrier: Carrier): HromoSet => (HromoSet,Double) =
   { x=>(x,rank(carrier,x)) }

   def avgRank(rankedPopulation: Seq[(HromoSet,Double)]):Double =
   {
      rankedPopulation.map(_._2).sum / rankedPopulation.size
   }



   def random: Random

}

class SetCoverageEvolutionOps(val random:Random) extends EvolutionOps
{


   case class Sets(d:Set[Int],
                   s:IndexedSeq[(Set[Int],Double)]
                  )

   override type Carrier = Sets

   override type HromoSet = IndexedSeq[Int]   // Indexed set in s

  override def isCorrect(carrier: Carrier, hromoSet: IndexedSeq[Int]): Boolean =
  {
    isCover(carrier,hromoSet.toSet)
  }

   def rank(carrier:Carrier, hromoSet: HromoSet): Double =
   {
    hromoSet.map(i => carrier.s(i)._2).sum
   }

   override def mutate(carrier: Carrier, origin: HromoSet): Seq[HromoSet] =
   {
      ???
   }

   override def crossover(carrier: Carrier, x: HromoSet, y: HromoSet): Seq[HromoSet] =
   {
      var newGen = Set[Int]()
      //  for(ax <- )
      while(!isCover(carrier,newGen)) {
         var ng = if (random.nextDouble() < 0.5) {
            x(random.nextInt(x.size))
         }else{
            y(random.nextInt(y.size))
         }
         newGen = newGen + ng
      }
      Seq(newGen.toIndexedSeq)
   }

   def isCover(carrier: Carrier, newGen: Set[Int]): Boolean =
   {
     if (newGen.isEmpty)
       false
     else {
       val sets: Set[Set[Int]] = newGen.map(carrier.s(_)._1)
       val united = sets.reduce((x, y) => x union y)
       carrier.d.subsetOf(united)
     }
   }

}

object Main
{

  def main(args:Array[String]): Unit =
  {
   val random = new Random(0)
   val ops = new SetCoverageEvolutionOps(random)

   val carriers = new ops.Sets(
      d=Set(1,2,3,4,5),
      s=IndexedSeq(
         (Set(1,2),1.0), // 0
         (Set(3),0.5),   // 1
         (Set(4),0.6),   // 2
         (Set(6,5),1.0), // 3
         (Set(1,3),0.5), // 4
         (Set(2,4),0.1), // 5
         (Set(4,3,5),0.7),// 6
         (Set(5),0.4),    // 7
         (Set(4,3),0.1) // 8
      )
   )

   val init = Seq(
      IndexedSeq(0,2,4,3),
      IndexedSeq(0,5,6),
      IndexedSeq(4,5,7),
      IndexedSeq(4,5,3,8)
   )

   val r = ops.run(carriers,init,4)

   System.err.println(s"r=$r")

  }




}
