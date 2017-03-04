import java.net.{Inet6Address, InetAddress}

trait Entity
{
  type Address <: AnyRef

  val address: Address
}

case class PersonAddress(street:String,city:String)

case class Person(name: String, address:PersonAddress) extends Entity
{
  type Address = PersonAddress

}

class Robot(name:String,
            val address: InetAddress) extends Entity
{
  type Address = InetAddress
}

class Ip6Robot(name: String,
               address: Inet6Address) extends Robot(name,address)
{
  override type Address = Inet6Address
}

def addresses(seq: Seq[Entity]) =
  seq map (_.address.toString)