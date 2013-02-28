package job


import akka.actor.Actor
import akka.camel.{ Consumer, CamelMessage }
import play.api._


class SampleJob extends CronJob("0 * * * * ?") {

  def receive = {
    //case msg => println(msg)
    case CamelMessage(_, m) => println("currentTime: %s, next: %s" format(m("scheduledFireTime"), m("nextFireTime")))
  }

}
