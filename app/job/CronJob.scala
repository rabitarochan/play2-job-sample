package job

import akka.actor.Actor
import akka.camel.{ Consumer, CamelMessage }
import play.api._

abstract class CronJob() extends Consumer {

    def cronFormat: String

    def actorName: String

    def endpointUri = "quartz://%s?cron=%s" format (actorName, cronFormat.split(" ").mkString("+"))

}