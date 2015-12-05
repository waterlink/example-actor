import akka.actor.{Actor, ActorRef}

case object WakeUp
case object TimeToDoSomeWork

class Programmer(coffeeMachine: ActorRef) extends Actor {
  var money = 350
  var payBy = 300

  def receive = {
    case WakeUp => coffeeMachine ! EspressoRequest
    case TimeToDoSomeWork => coffeeMachine ! LatteRequest

    case Bill(request, cents) if cents > money =>
      println(s"Oh, I have to pay $cents cents for this. Too expensive!")

    case Bill(request, cents) =>
      money = money - payBy
      sender ! Payment(request, payBy)
      println(s"Only $cents cents for this. Fantastic! (left money: $money)")

    case Latte =>
      println("Mmm. Good latte!")

    case Espresso =>
      println("Yuck! Cheap espresso!")

    case Change(cents) =>
      money = money + cents
      println(s"Oh, got $cents cents of change. Wonderful! (left money: $money)")
  }
}
