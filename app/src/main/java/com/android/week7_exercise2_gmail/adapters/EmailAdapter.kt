import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.week7_exercise2_gmail.R

class EmailAdapter(private val emailList: List<EmailModel>) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>() {

    private val colors = listOf(
        Color.parseColor("#F44336"), // Red
        Color.parseColor("#E91E63"), // Pink
        Color.parseColor("#9C27B0"), // Purple
        Color.parseColor("#3F51B5"), // Blue
        Color.parseColor("#4CAF50"), // Green
        Color.parseColor("#FF9800"), // Orange
        Color.parseColor("#009688")  // Teal
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.email_layout, parent, false)
        return EmailViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emailList[position]
        holder.senderName.text = email.senderName
        holder.subject.text = email.subject
        holder.preview.text = email.preview
        holder.time.text = email.time

        holder.check.isSelected = email.isStarred
        holder.check.setOnClickListener {
            email.isStarred = !email.isStarred
            holder.check.isSelected = email.isStarred
        }

        val backgroundColor = colors[position % colors.size]

        val firstLetter = email.senderName.first().uppercaseChar()

        val avatarDrawable = createAvatarDrawable(firstLetter, backgroundColor)
        holder.avatar.setImageDrawable(avatarDrawable)
    }

    override fun getItemCount(): Int {
        return emailList.size
    }

    class EmailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
        val senderName: TextView = itemView.findViewById(R.id.senderName)
        val subject: TextView = itemView.findViewById(R.id.subject)
        val preview: TextView = itemView.findViewById(R.id.preview)
        val time: TextView = itemView.findViewById(R.id.time)
        val check: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    private fun createAvatarDrawable(firstLetter: Char, backgroundColor: Int): BitmapDrawable {
        val bitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint()
        paint.color = backgroundColor
        paint.isAntiAlias = true
        canvas.drawCircle(24f, 24f, 24f, paint)

        paint.color = Color.WHITE
        paint.textSize = 24f
        paint.textAlign = Paint.Align.CENTER
        val textBounds = Rect()
        paint.getTextBounds(firstLetter.toString(), 0, 1, textBounds)
        canvas.drawText(firstLetter.toString(), 24f, 24f - textBounds.exactCenterY(), paint)

        return BitmapDrawable(Resources.getSystem(), bitmap)
    }

}
