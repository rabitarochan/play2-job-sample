package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import job._
import akka.actor.{ Props }
import play.api.libs.concurrent._
import play.api.Play.current

object Application extends Controller {
  
  val scheduleForm = Form(
    "cronFormat" -> nonEmptyText
  )

  def index = Action {
    val form = SampleJob.instance.map(
      a => scheduleForm.fill(a.cronFormat)
    ).getOrElse(scheduleForm)

    Ok(views.html.index(form))
  }

  def restartActor = Action { implicit req =>
    scheduleForm.bindFromRequest.fold(
      error => BadRequest(views.html.index(error)),
      cronFormat => {
        println("restartActor: cronFormat = %s" format cronFormat)
        SampleJob.instance match {
          case Some(actor) => SampleJob.stop()
          case None        => ()
        }
        SampleJob.start(cronFormat)
        Redirect(routes.Application.index)
      }
    )
  }
  
}