package job


import akka.actor.{ Actor, ActorRef }
import akka.camel.{ Consumer, CamelMessage }
import play.api._


class SampleJob private(format: String) extends CronJob() {

  def cronFormat = format

  def actorName = "default"

  def receive = {
    //case msg => println(msg)
    case CamelMessage(_, m) =>
      println("[%s] currentTime: %s, next: %s" format(m("triggerName"), m("scheduledFireTime"), m("nextFireTime")))
  }

}

object SampleJob {

  var instance: Option[SampleJob] = None

  def apply(format: String): SampleJob = {
    val job = new SampleJob(format)
    instance = Option(job)
    job
  }

}