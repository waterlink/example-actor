import akka.actor.Actor

sealed trait CoffeeRequest
case object EspressoRequest extends CoffeeRequest
case object LatteRequest extends CoffeeRequest

class RequestAcceptor extends Actor {
  def receive = {
    case EspressoRequest => println("Making espresso")
    case LatteRequest => println("Making latte")
  }
}
