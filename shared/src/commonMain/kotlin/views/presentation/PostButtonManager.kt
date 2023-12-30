package views.presentation

import account.AppUser
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.TablerIcons
import compose.icons.tablericons.Message
import event.TrendWaveEvent
import event.TrendWaveState
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import post.RESTfulPostManager
import utilities.CommonLogger
import utilities.color.Colors
import utilities.color.fromEnum

class PostButtonManager {

    private val client = HttpClient()
    private val url = "https://felix.henneri.ch/php/RESTfulAPI/"

    /**
     * @example #1,uuid#0,uuid#1,uuid.....
     * # -> Abtrennung der buttons
     * 1 -> Message
     * 0 -> Account
     * uuid -> unique id of message or account
     *
     *
     * @example #0+00235b159f29af46abe307d028ce9579#0+00c1436c80e3806d1d032b528a6b0eec
     * @param uuid - Playeruuid
     * @param onEvent - Eventhandling
     * @param notClickable - is button clickable?
     */

    suspend fun getButtonsDatabase(uuid: String, onEvent: (TrendWaveEvent) -> Unit, notClickable: Boolean): List<PostButtons> {
        val buttonsString = AppUser().getButtons(uuid)
        val lst = stringToPostButtonLst(buttonsString, onEvent, notClickable)
        onEvent(TrendWaveEvent.LoadDataToCachePostButtons(lst))
        return lst
    }


    /**
     * String of postbuttons to postbutton list
     *
     * @param uuid - Playeruuid
     * @param onEvent - Eventhandling
     * @param notClickable - is button clickable?
     */
    suspend fun stringToPostButtonLst(lst: String, onEvent: (TrendWaveEvent) -> Unit, notClickable: Boolean): List<PostButtons>{
        val appUser = AppUser()
        val postbuttonlst = emptyList<PostButtons>().toMutableList()
        val parts = lst.split("#")


        for(part in parts) {
            val subParts = part.split("+")
            if (subParts.isNotEmpty()) {
                val firstElement = subParts[0]
                if (firstElement == "0") {
                    //Account
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
                        addbutton = false,
                        id = subParts[1]
                    )
                } else if (firstElement == "1") {
                    //Message
                    val post = RESTfulPostManager().findPostById(subParts[1])
                    postbuttonlst += PostButtons(
                        modifier = Modifier.padding(end = 10.dp),
                        backgroundcolor = Color.fromEnum(Colors.QUATERNARY),
                        textcolor = Color.fromEnum(Colors.SENARY),
                        imageVector = null,
                        text = post.theme,
                        onEvent = onEvent,
                        event = TrendWaveEvent.ClickPostMessageDisplay(
                            posttext = post.text,
                            postdate = post.date,
                            authorname = post.username,
                            postid = post.id,
                        ),
                        notclickable = notClickable,
                        smallicon = TablerIcons.Message,
                        primarybackground = Color.fromEnum(Colors.PRIMARY),
                        addbutton = false,
                        id = subParts[1]
                    )
                }
            }
        }
        return postbuttonlst
    }


    /**
     * Delete the local storage of the homescreen buttons
     *
     * @param onEvent - Eventhandling
     */
    fun deleteLocalButtons(onEvent: (TrendWaveEvent) -> Unit){
        onEvent(TrendWaveEvent.DeleteLocalHomeButtons)
    }

    fun isIDinHomeButtonList(state: TrendWaveState,id: String): Boolean{
        return state.buttonshomescreen.toString().contains(id)
    }


    /**
     * Change buttons in database or local storage
     *
     * @param add - add or remove boolean
     * @param type - 0 = Person 1 = Message
     * @param uuid - post or user iD
     * @param user - user uuid
     * @param onEvent - Eventhandling
     */
    suspend fun buttonChange(add: Boolean, type: Int, uuid: String, user: String, onEvent: (TrendWaveEvent) -> Unit){
        val homeButtons = AppUser().getButtons(user)
        val finurl = url + "userGetter.php"
        if(add){
            //add button to string
            val commonLogger = CommonLogger()
            commonLogger.log(homeButtons)
            val newbuttons = addNewButton(type, uuid, homeButtons)
            commonLogger.log(newbuttons)

            //Update in database
            client.get(finurl) {
                url {
                    parameters.append("uuid", user)
                    parameters.append("homebuttons", newbuttons)
                }
            }

            //Convert to list
            val newLst = stringToPostButtonLst(newbuttons, onEvent, false)

            //update locally
            onEvent(TrendWaveEvent.LoadDataToCachePostButtons(newLst))
        }else {
            //Remove buttom from string
            val newbuttons = removeButton(type, uuid, homeButtons)

            //Update in database
            client.get(finurl) {
                url {
                    parameters.append("uuid", user)
                    parameters.append("homebuttons", newbuttons)
                }
            }

            //Convert to list
            val newLst = stringToPostButtonLst(newbuttons, onEvent, false)

            //update locally
            onEvent(TrendWaveEvent.LoadDataToCachePostButtons(newLst))
        }
    }

    /**
     * Add new button to your home screen
     *
     * @param button type
     * @param uuid (User or post)
     * @param homeButtons - string of buttons
     * @example #1,uuid#0,uuid#1,uuid.....
     * @example # -> Abtrennung der buttons
     * @example 1 -> Message
     * @example 0 -> Account
     * @example uuid -> unique id of message or account
     * @example #0+00235b159f29af46abe307d028ce9579#0+00c1436c80e3806d1d032b528a6b0eec
     */
    fun addNewButton(type: Int, uuid: String, homeButtons: String): String {
        var prevUserButtonsList = homeButtons

        val toAdd = "$type+$uuid#"

        return prevUserButtonsList + toAdd
    }


    /**
     * remove button from your home screen
     *
     * @param button type
     * @param uuid (User or post)
     * @param homeButtons - string of buttons
     * @example #1,uuid#0,uuid#1,uuid.....
     * @example # -> Abtrennung der buttons
     * @example 1 -> Message
     * @example 0 -> Account
     * @example uuid -> unique id of message or account
     * @example #0+00235b159f29af46abe307d028ce9579#0+00c1436c80e3806d1d032b528a6b0eec
     */
    fun removeButton(type: Int, uuid: String, homeButtons: String): String{
        var prevUserButtonsList = homeButtons.split("#").toMutableList()

        prevUserButtonsList.remove("$type+$uuid")

        var newUserButtonsList = "#"
        for(entry in prevUserButtonsList){
            if(entry != "" && entry != " ")
                newUserButtonsList = "$newUserButtonsList$entry#"
        }


        return newUserButtonsList
    }


}