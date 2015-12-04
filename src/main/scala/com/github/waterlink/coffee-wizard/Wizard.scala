import akka.actor.{ActorSystem, ActorRef, Props}

object CoffeeWizard extends App {
  val system = ActorSystem("CoffeeWizard")

  val requestAcceptor = system.actorOf(Props[RequestAcceptor], "RequestAcceptor")

  requestAcceptor ! EspressoRequest
  requestAcceptor ! LatteRequest
  println("I have ordered espresso and latte, and it doesn't work!")

  system.shutdown()
}
