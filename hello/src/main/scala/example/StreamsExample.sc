
def from(n: Int): Stream[Int] = n #:: from(n + 1)


def primes(nums: Stream[Int]): Stream[Int] = nums.head #:: primes(nums.tail.filter{ x =>
  Console.println(s"check $x for ${nums.head}")
  x % nums.head != 0}
)


val naturals = from(2)

primes(naturals).take(10).mkString(",")