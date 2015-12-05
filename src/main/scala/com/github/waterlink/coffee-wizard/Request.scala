import akka.actor.Actor

case object ShutdownRequest

sealed trait CoffeeRequest
case object EspressoRequest extends CoffeeRequest
case object LatteRequest extends CoffeeRequest

sealed trait Coffee
case object Espresso extends Coffee
case object Latte extends Coffee

case class Bill(request: CoffeeRequest, cents: Int)
case class Payment(request: CoffeeRequest, cents: Int)
case class Change(cents: Int)

class RequestAcceptor extends Actor {
  val espressoCost = 150
  val latteCost = 270

  def receive = {
    case EspressoRequest =>
      sender ! Bill(EspressoRequest, espressoCost)
      println(s"Charging $espressoCost cents for espresso")
    case Payment(EspressoRequest, cents) if cents >= espressoCost =>
      sender ! Espresso
      if (cents > espressoCost) sender ! Change(cents - espressoCost)
      println(s"Received $cents cents for espresso")
      println("Making espresso")
    case Payment(EspressoRequest, cents) =>
      sender ! Change(cents)
      println(s"Received insufficient $cents cents for espresso")

    case LatteRequest =>
      sender ! Bill(LatteRequest, latteCost)
      println(s"Charging $latteCost cents for latte")
    case Payment(LatteRequest, cents) if cents >= latteCost =>
      sender ! Latte
      if (cents > latteCost) sender ! Change(cents - latteCost)
      println(s"Received $cents cents for latte")
      println("Making latte")
    case Payment(LatteRequest, cents) =>
      sender ! Change(cents)
      println(s"Received insufficient $cents cents for latte")

    case ShutdownRequest =>
      context.system.shutdown
  }
}
