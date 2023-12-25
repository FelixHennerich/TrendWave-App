package views.presentation

import account.AppUser
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.Plus
import compose.icons.tablericons.Message
import event.TrendWaveEvent
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import utilities.CommonLogger
import utilities.color.Colors
import utilities.color.fromEnum

class PostButtonManager {

    /**
     *
     * @example #1,uuid#0,uuid#1,uuid.....
     * # -> Abtrennung der buttons
     * 1 -> Message
     * 0 -> Account
     * uuid -> unique id of message or account
     *
     *
     * @example #0+00235b159f29af46abe307d028ce9579#0+00c1436c80e3806d1d032b528a6b0eec
     */

    suspend fun getButtonsDatabase(
        uuid: String,
        onEvent: (TrendWaveEvent) -> Unit,
        notClickable: Boolean
    ): List<PostButtons> {
        val appUser = AppUser()
        val postbuttons = appUser.getButtons(uuid)
        val postbuttonlst = emptyList<PostButtons>().toMutableList()

        val input = postbuttons
        val parts = input.split("#")

        for(part in parts) {
            val subParts = part.split("+")

            if (subParts.isNotEmpty()) {
                val firstElement = subParts[0]
                if (firstElement == "0") {
                    //Account
                    val commonLogger = CommonLogger()
                    commonLogger.log(notClickable.toString())
                    postbuttonlst += PostButtons(
                        modifier = Modifier.padding(end = 10.dp),
                        backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                        textcolor = Color.fromEnum(Colors.SENARY),
                        imageVector = null,
                        text = appUser.getUsername(subParts[1]),
                        onEvent = onEvent,
                        event = TrendWaveEvent.ClickUserProfileViewButton(
                            appUser.getUser(
                                subParts[1]
                            )
                        ),
                        notclickable = notClickable,
                        smallicon = Icons.Rounded.Person,
                        primarybackground = Color.fromEnum(Colors.PRIMARY),
                        addbutton = false
                    )
                } else if (firstElement == "1") {
                    //Message
                }
            }
        }

        onEvent(TrendWaveEvent.LoadDataToCachePostButtons(postbuttonlst))


        return postbuttonlst
    }


    fun deleteLocalButtons(onEvent: (TrendWaveEvent) -> Unit){
        onEvent(TrendWaveEvent.DeleteLocalHomeButtons)
    }


}