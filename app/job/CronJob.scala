package job

import akka.actor.Actor
import akka.camel.{ Consumer, CamelMessage }
import play.api._

abstract class CronJob(cron: String, name: String = "default") extends Consumer {

    def endpointUri = "quartz://%s?cron=%s" format (name, cron.split(" ").mkString("+"))

}