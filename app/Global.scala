import akka.actor.{ Props }
import akka.actor.Actor._
import play.api._
import play.api.libs.concurrent._
import play.api.Play.current

import job._

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    val actor = Akka.system.actorOf(Props[SampleJob])
    //Akka.system.stop(actor)
  }

}