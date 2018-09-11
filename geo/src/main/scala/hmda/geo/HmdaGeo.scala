package hmda.geo

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import hmda.geo.api.http.HmdaGeoQueryApi
import org.slf4j.LoggerFactory

object HmdaGeo extends App {

  val log = LoggerFactory.getLogger("geo")

  log.info(
    """  _    _ __  __ _____             _____
      | | |  | |  \/  |  __ \   /\      / ____|
      | | |__| | \  / | |  | | /  \    | |  __  ___  ___
      | |  __  | |\/| | |  | |/ /\ \   | | |_ |/ _ \/ _ \
      | | |  | | |  | | |__| / ____ \  | |__| |  __/ (_) |
      | |_|  |_|_|  |_|_____/_/    \_\  \_____|\___|\___/
      |
      |
      |   180   150W  120W  90W   60W   30W   000   30E   60E   90E   120E  150E  180
      |    |     |     |     |     |     |     |     |     |     |     |     |     |
      |90N-+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-90N
      |    |           . _..::__:  ,-"-"._        |7       ,     _,.__             |
      |    |   _.___ _ _<_>`!(._`.`-.    /         _._     `_ ,_/  '  '-._.---.-.__|
      |    |>.{     " " `-==,',._\{  \  / {)      / _ ">_,-' `                mt-2_|
      |60N-+  \_.:--.       `._ )`^-. "'       , [_/(                       __,/-' +-60N
      |    | '"'     \         "    _L        oD_,--'                )     /. (|   |
      |    |          |           ,'          _)_.\\._<> 6              _,' /  '   |
      |    |          `.         /           [_/_'` `"(                <'}  )      |
      |30N-+           \\    .-. )           /   `-'"..' `:._          _)  '       +-30N
      |    |    `        \  (  `(           /         `:\  > \  ,-^.  /' '         |
      |    |              `._,   ""         |           \`'   \|   ?_)  {\         |
      |    |                 `=.---.        `._._       ,'     "`  |' ,- '.        |
      |000-+                   |    `-._         |     /          `:`<_|h--._      +-000
      |    |                   (        >        .     | ,          `=.__.`-'\     |
      |    |                    `.     /         |     |{|              ,-.,\     .|
      |    |                     |   ,'           \   / `'            ,"     \     |
      |30S-+                     |  /              |_'                |  __  /     +-30S
      |    |                     | |                                  '-'  `-'   \.|
      |    |                     |/                                         "    / |
      |    |                     \.                                             '  |
      |60S-+                                                                       +-60S
      |    |                      ,/            ______._.--._ _..---.---------._   |
      |    |     ,-----"-..?----_/ )      __,-'"             "                  (  |
      |    |-.._(                  `-----'                                       `-|
      |90S-+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-90S
      |    Map 1998 Matthew Thomas.|Freely usable as long as this|line is included.|
      |    |     |     |     |     |     |     |     |     |     |     |     |     |
      |   180   150W  120W  90W   60W   30W   000   30E   60E   90E   120E  150E  180
      |-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+----+-----+
    """.stripMargin
  )

  val config = ConfigFactory.load()

  val host = config.getString("hmda.geo.http.host")
  val port = config.getString("hmda.geo.http.port")

  implicit val system = ActorSystem("hmda-institutions")
  system.actorOf(HmdaGeoQueryApi.props(), "hmda-institutions-api")

}
