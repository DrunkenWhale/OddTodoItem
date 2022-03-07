package demo.controller.usr

import akka.http.scaladsl.server.Directives._

object SignIn {
    post{
        path("usr" / "sign-in"){
            formField("usr","pwd"){ (usr,pwd)=>
                
            }
        }
    }
}
