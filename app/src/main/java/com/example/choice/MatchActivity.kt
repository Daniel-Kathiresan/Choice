package com.example.choice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.random.Random

//Daniel matching
class MatchActivity : AppCompatActivity(), CardStackListener {
    //Init variables
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var userDB: DatabaseReference
    private lateinit var currUserDB: DatabaseReference
    private val adapter = CardStackAdapter()
    private val cardItems = mutableListOf<CardItem>()
    //Date
    private var currentDate: Date = Date()
    private var newDate: Date = Date()
    //Random variables
    private var randVarDate = 0
    private var randVarTime = 0
    //Date variabled randomised
    private var Date = ""
    private var Time = ""
    //Notification
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationID = 101

    var userGender: String = ""
    var userGenderPref: String = ""
    //Call card manager API
    private val manager by lazy {
        CardStackLayoutManager(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        //Set references
        userDB = Firebase.database.reference.child("Users")
        currUserDB = FirebaseDatabase.getInstance().getReference("Users")



        val currentUserDB = userDB.child(getCurrentUserID())
        //Listen for current user first name, if there is one force input, error prevention
        currentUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("first_name").value == null) {
                    showNameInputPopup()
                    return
                }
                //Call all setup methods, moved from main body
                getUserGender()
                randomDateTime()
                initCardStackView()
                initButtons()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    private fun randomDateTime(){
        //Get a random number
        randVarDate = Random.nextInt(1,8)
        randVarTime = Random.nextInt(-10,10)
        //Get current date and time
        val currDate = Calendar.getInstance()
        currDate.time = currentDate
        println("Current Date:  $currDate")
        currDate.add(Calendar.DAY_OF_WEEK, randVarDate)
        println("Adding random day: $currDate")
        currDate.add(Calendar.HOUR, randVarTime)
        println("Adding random time: $currDate")
        //Change new date to changed date
        newDate = currDate.time
        println("New Date: $newDate")
        //Create date format
        val df = SimpleDateFormat("dd-MM-yyyy")
        val tf = SimpleDateFormat("HH")
        //Change date + time variable
        Date = (df.format(newDate)).toString()
        println("New Date: $Date")
        Time = (tf.format(newDate)).toString()
        println("New Time: $Time")
    }
    fun getUserGender(){
        val user = auth.currentUser
        userDB.child(user!!.uid).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("gender").value != null){
                    userGender = snapshot.child("gender").value.toString()
                    getUserGenderPref()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    fun getUserGenderPref(){
        val user = auth.currentUser
        userDB.child(user!!.uid).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child("gender_pref").value != null){
                    userGenderPref = snapshot.child("gender_pref").value.toString()
                    getUnSelectedUsers()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun initCardStackView() {
        val stackView = findViewById<CardStackView>(R.id.cardStackView)
        //Find card stack (api) and set adapter
        stackView.layoutManager = manager
        stackView.adapter = adapter
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)

    }
    //Get users that havent been liked or disliked
    //Also do UID
    private fun getUnSelectedUsers() {
        println("User Gender = $userGender")
        println("User Pref = $userGenderPref")
        userDB.addChildEventListener(object : ChildEventListener {
            //Ladder IF Statements for Gender Matching
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //Basic match check (M->F, F->M)
                if (snapshot.child("uid").value != getCurrentUserID()&&
                    snapshot.child("likedBy").child("like").hasChild(getCurrentUserID()).not() &&
                    snapshot.child("likedBy").child("disLike").hasChild(getCurrentUserID()).not()
                    &&
                            snapshot.child("gender").value == userGenderPref &&
                            snapshot.child("gender_pref").value == userGender
                ) {
                    val userId = snapshot.child("uid").value.toString()
                    var name = "undecided"
                    if (snapshot.child("first_name").value != null) {
                        name = snapshot.child("first_name").value.toString()
                    }
                    val bio = snapshot.child("bio").value.toString()
                    val profilePic = snapshot.child("profile_picture").value.toString()
                    //Add to card items
                    cardItems.add(CardItem(userId, name, bio, profilePic))
                    adapter.submitList(cardItems)
                    //Notify data set changed to adapter
                    adapter.notifyDataSetChanged()
                }
                //Check "other" gender, snapshot
                if (snapshot.child("uid").value != getCurrentUserID()&&
                    snapshot.child("likedBy").child("like").hasChild(getCurrentUserID()).not() &&
                    snapshot.child("likedBy").child("disLike").hasChild(getCurrentUserID()).not()
                    &&
                    snapshot.child("gender").value.toString() == "Other" &&
                            userGender == snapshot.child("gender_pref").value.toString()
                ) {
                    val userId = snapshot.child("uid").value.toString()
                    var name = "undecided"
                    if (snapshot.child("first_name").value != null) {
                        name = snapshot.child("first_name").value.toString()
                    }
                    val bio = snapshot.child("bio").value.toString()
                    val profilePic = snapshot.child("profile_picture").value.toString()
                    //Add to card items
                    cardItems.add(CardItem(userId, name, bio, profilePic))
                    adapter.submitList(cardItems)
                    //Notify data set changed to adapter
                    adapter.notifyDataSetChanged()

                }
                //Check "other" gender, user
                if (snapshot.child("uid").value != getCurrentUserID()&&
                    snapshot.child("likedBy").child("like").hasChild(getCurrentUserID()).not() &&
                    snapshot.child("likedBy").child("disLike").hasChild(getCurrentUserID()).not()
                    &&
                    userGender == "Other" &&
                            snapshot.child("gender").value.toString() == userGenderPref
                ) {
                    val userId = snapshot.child("uid").value.toString()
                    var name = "undecided"
                    if (snapshot.child("first_name").value != null) {
                        name = snapshot.child("first_name").value.toString()
                    }
                    val bio = snapshot.child("bio").value.toString()
                    val profilePic = snapshot.child("profile_picture").value.toString()
                    //Add to card items
                    cardItems.add(CardItem(userId, name, bio, profilePic))
                    adapter.submitList(cardItems)
                    //Notify data set changed to adapter
                    adapter.notifyDataSetChanged()

                }
                //Everyone check 1 (Snapshot gender everyone)
                if (snapshot.child("uid").value != getCurrentUserID()&&
                    snapshot.child("likedBy").child("like").hasChild(getCurrentUserID()).not() &&
                    snapshot.child("likedBy").child("disLike").hasChild(getCurrentUserID()).not()
                    &&
                    snapshot.child("gender_pref").value == "Everyone" &&
                    snapshot.child("gender").value == userGenderPref){
                    val userId = snapshot.child("uid").value.toString()
                    var name = "undecided"
                    if (snapshot.child("first_name").value != null) {
                        name = snapshot.child("first_name").value.toString()
                    }
                    val bio = snapshot.child("bio").value.toString()
                    val profilePic = snapshot.child("profile_picture").value.toString()
                    //Add to card items
                    cardItems.add(CardItem(userId, name, bio, profilePic))
                    adapter.submitList(cardItems)
                    //Notify data set changed to adapter
                    adapter.notifyDataSetChanged()
                }
                //Everyone Check 2 (User Gender Everyone)
                if (snapshot.child("uid").value != getCurrentUserID()&&
                    snapshot.child("likedBy").child("like").hasChild(getCurrentUserID()).not() &&
                    snapshot.child("likedBy").child("disLike").hasChild(getCurrentUserID()).not()
                    &&
                    snapshot.child("gender_pref").value == userGender &&
                    "Everyone" == userGenderPref){
                    val userId = snapshot.child("uid").value.toString()
                    var name = "undecided"
                    if (snapshot.child("first_name").value != null) {
                        name = snapshot.child("first_name").value.toString()
                    }
                    val bio = snapshot.child("bio").value.toString()
                    val profilePic = snapshot.child("profile_picture").value.toString()
                    //Add to card items
                    cardItems.add(CardItem(userId, name, bio, profilePic))
                    adapter.submitList(cardItems)
                    //Notify data set changed to adapter
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                cardItems.find { it.userId == snapshot.key }?.let {
                    it.name = snapshot.child("first_name").value.toString()
                }
                //Submit to adapter
                adapter.submitList(cardItems)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })
    }
    //Shows name input if user has no name, removes errors that may occur with registration
    private fun showNameInputPopup() {
        val editText = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("No name detected: Please enter your name")
            .setView(editText)
            .setPositiveButton("Confirm") { _, _ ->
                if (editText.text.isEmpty()) {
                    showNameInputPopup()
                } else {
                    saveUserName(editText.text.toString())
                }
            }
            .setCancelable(false)
            .show()
    }
    //Saves username into firebase
    private fun saveUserName(name: String) {

        val userId: String = getCurrentUserID()
        val currentUserDb = userDB.child(userId)
        val user = mutableMapOf<String, Any>()
        user["uid"] = userId
        user["first_name"] = name
        currentUserDb.updateChildren(user)
        getUnSelectedUsers()
    }
    //Gets the current user ID, returns if not logged in
    private fun getCurrentUserID(): String {
        if (auth.currentUser == null) {
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }



    //Function for liking user
    private fun like() {
        val card = cardItems[manager.topPosition - 1]
        cardItems.removeFirst()
        //Add liked tree to firebase information
        userDB.child(card.userId)
            .child("likedBy")
            .child("like")
            .child(getCurrentUserID())
            .setValue(true)

        saveMatchIfOtherUserLikeMe(card.userId)
        //Display on like
        Toast.makeText(this, "${card.name} has been liked.", Toast.LENGTH_SHORT).show()
    }

    private fun disLike() {
        val card = cardItems[manager.topPosition - 1]
        cardItems.removeFirst()

        userDB.child(card.userId)
            .child("likedBy")
            .child("disLike")
            .child(getCurrentUserID())
            .setValue(true)
        //Display on dislike
        Toast.makeText(this, "${card.name} has been disliked.", Toast.LENGTH_SHORT).show()
    }

    //Save match in firebase like tree, viewable on firebase data
    private fun saveMatchIfOtherUserLikeMe(otherUserId: String) {
        //Call random date time
        randomDateTime()

        val otherUserDB =
            userDB.child(getCurrentUserID()).child("likedBy").child("like").child(otherUserId)
        otherUserDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value == true) {

                    //Automatic matching added
                    userDB.child(getCurrentUserID())
                        .child("likedBy")
                        .child("match")
                        .child(otherUserId)
                        .setValue(true)

                    userDB.child(otherUserId)
                        .child("likedBy")
                        .child("match")
                        .child(getCurrentUserID())
                        .setValue(true)
                    //Create date and time for other user
                    userDB.child(otherUserId)
                        .child("date")
                        .child("with")
                        .child(getCurrentUserID())
                        .child("day")
                        .setValue(Date)

                    userDB.child(otherUserId)
                        .child("date")
                        .child("with")
                        .child(getCurrentUserID())
                        .child("time")
                        .setValue(Time)
                    //Create date and time for current user
                    userDB.child(getCurrentUserID())
                        .child("date")
                        .child("with")
                        .child(otherUserId)
                        .child("day")
                        .setValue(Date)

                    userDB.child(getCurrentUserID())
                        .child("date")
                        .child("with")
                        .child(otherUserId)
                        .child("time")
                        .setValue(Time)

                    //Send the notification
                    createNotificationChannel()
                    sendNotification()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {}

    //Override for card adapter, set right and left actions
    override fun onCardSwiped(direction: Direction?) {
        println("Function activated")
        when (direction) {
            Direction.Right -> like()
            Direction.Left -> disLike()
            else -> {}
        }
    }
    //Rewind not implemented
    override fun onCardRewound() {}

    override fun onCardCanceled() {}
    //Set view on card appear

    override fun onCardAppeared(view: View?, position: Int) {}
    //Set view on card disappear

    override fun onCardDisappeared(view: View?, position: Int) {}

    private fun initButtons(){
        val returnMainButton = findViewById<Button>(R.id.returnToMain)
        returnMainButton.setOnClickListener {
            startActivity(Intent(this, BottomNav::class.java))
        }
        //TODO: Not working, remove at end if still broken
//        val likeButton = findViewById<View>(R.id.like_button)
//        likeButton.setOnClickListener{
//            like()
//        }
//        val skipButton = findViewById<View>(R.id.skip_button)
//        skipButton.setOnClickListener{
//            onCardSwiped(Direction.Left)
//        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(){

        val intent = Intent(this, BottomNav::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent,0)
        val bitmap = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.randomuser)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources,R.drawable.randomuser)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.choicelogonew)
            .setContentTitle("You got Match!!")
            .setContentText("")
            .setLargeIcon(bitmapLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle().bigText("Check your match page now!"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationID,builder.build())
        }
    }


}