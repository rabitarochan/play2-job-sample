package job

import akka.actor.{ Actor, ActorRef, Props }
import akka.camel.{ Consumer, CamelMessage }
import play.api._
import play.api.libs.concurrent._
import play.api.Play.current

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
  var actorRef: Option[ActorRef] = None

  def start(format: String): ActorRef = {
    val ref = Akka.system.actorOf(Props(SampleJob(format)))
    actorRef = Option(ref)
    ref
  }

  private def apply(format: String) = {
    val job = new SampleJob(format)
    instance = Option(job)
    job
  }

  def stop() {
    actorRef match {
      case Some(ref) => {
        Akka.system.stop(ref)
        instance = None
        actorRef = None
      }
      case _         => ()
    }
  }

}