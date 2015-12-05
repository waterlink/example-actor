import akka.actor.{ActorSystem, ActorRef, Props}

object CoffeeWizard extends App {
  val system = ActorSystem("CoffeeWizard")

  val requestAcceptor = system.actorOf(Props[RequestAcceptor], "RequestAcceptor")
  val johndoe = system.actorOf(Props(classOf[Programmer], requestAcceptor), "JohnDoeTheProgrammer")

  johndoe ! TimeToDoSomeWork
  johndoe ! WakeUp
  requestAcceptor ! ShutdownRequest
}
