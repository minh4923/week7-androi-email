package com.android.week7_exercise2_gmail

import EmailAdapter
import EmailModel
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emailAdapter: EmailAdapter
    private lateinit var emailList: MutableList<EmailModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Tạo danh sách email giả lập
        val emailList = mutableListOf(
            EmailModel("Edurila.com", "Web Design Course", "Learn web design", "12:34 PM", false),
            EmailModel("Chris Abad", "Campaign Monitor", "Let us know your thoughts!", "11:22 AM", true),
            EmailModel("Tuto.com", "8h de formation gratuite", "Blender, CSS", "11:04 AM", false),
            EmailModel("Support", "Société Ovh", "Suivi de vos services", "10:26 AM", false),
            EmailModel("Matt from Ionic", "New Ionic Creator", "Announcing the all-new Creator", "9:10 AM", false),
            EmailModel("Google", "Security Alert", "New sign-in from a device", "8:15 AM", true),
            EmailModel("Spotify", "Your Weekly Playlist", "Discover new music this week", "Yesterday", false),
            EmailModel("GitHub", "Repository Update", "Your repo has been updated", "Yesterday", false),
            EmailModel("Facebook", "Notification", "You have new friend requests", "Yesterday", true),
            EmailModel("StackOverflow", "New Answer", "Someone replied to your question", "2 days ago", false),
            EmailModel("LinkedIn", "Job Alert", "New job matches your profile", "2 days ago", true),
            EmailModel("Twitter", "Mention", "You were mentioned in a tweet", "3 days ago", false),
            EmailModel("AWS", "Invoice", "Your monthly bill is ready", "3 days ago", true),
            EmailModel("Slack", "Workspace Invite", "Join the new workspace", "4 days ago", false),
            EmailModel("Netflix", "New Releases", "Check out what's new this week", "4 days ago", false)
        )


        emailAdapter = EmailAdapter(emailList)
        recyclerView.adapter = emailAdapter
    }
}
