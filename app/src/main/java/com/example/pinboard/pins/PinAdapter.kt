package com.example.pinboard.pins

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pinboard.R
import com.example.pinboard.cache.InitialCache
import com.example.pinboard.cache.PictureLoader
import com.example.pinboard.model.PinModel
import com.example.pinboard.utils.Utilities
import kotlinx.android.synthetic.main.card_layout.view.*

class PinAdapter(
    private var pinList: List<PinModel>,
    private var onDataListener: OnDataListener
) : RecyclerView.Adapter<PinAdapter.PinHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinHolder {
        PictureLoader.setCache(InitialCache())
        return PinHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PinHolder, position: Int) =
        holder.bind(pinList[position], onDataListener)

    override fun getItemCount() = pinList.size


    class PinHolder(articleView: View) : RecyclerView.ViewHolder(articleView) {

        fun bind(pinModel: PinModel, onDataListener: OnDataListener) = with(itemView) {

            val color = Color.parseColor(pinModel.color)
            card_view.setBackgroundColor(color)

            PictureLoader.displayImage(pinModel.urls.thumb, imageViewCard, R.drawable.nocover)

            imageViewCard.setOnClickListener({ _ -> onDataListener.profileClicked(pinModel) })

            PictureLoader.displayImage(pinModel.user.profile_image.small, imageViewAvatar, R.drawable.profile35)

            with(pinModel) {
                textViewAvatar.text = user.name

                imageViewAvatar.setOnClickListener({ _ ->
                    Utilities().openWebPage(user.links.html, context)
                })

                val dark: Boolean = Utilities().isDark(color)
                if (dark) textViewAvatar.setTextColor(Utilities().getResColor(context))

                textViewAvatar.setOnClickListener({ _ ->
                    Utilities().openWebPage(user.links.html, context)
                })

                imageViewReload.setOnClickListener({ _ ->
                    Toast.makeText(context, "Image resume request", Toast.LENGTH_SHORT).show()
                    PictureLoader.displayImage(pinModel.urls.thumb, imageViewCard, R.drawable.nocover)
                })
                imageViewCancel.setOnClickListener({ result ->
                    Toast.makeText(context, "Image pause request", Toast.LENGTH_SHORT).show()
                    PictureLoader.pauseRequest()
                })
            }
        }

    }
}
