package com.ssafy.indive.view.songdetail


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemReplyBinding
import com.ssafy.indive.model.response.ReplyResponse


class ReplyAdapter(
    val replyCLickListener: ReplyCLickListener,
    val memberSeq: Long
) :
    ListAdapter<ReplyResponse, ReplyAdapter.CommentViewHolder>(diffUtil) {

    inner class CommentViewHolder(val binding: ItemReplyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reply: ReplyResponse) {
            binding.apply {
                this.reply = reply

            }
            if (memberSeq == reply.member.memberSeq) {
                binding.apply {
                    ivCommentEdit.visibility = View.VISIBLE
                    ivCommentRemove.visibility = View.VISIBLE
                    ivCommentReport.visibility = View.GONE
                }
            } else {
                binding.apply {
                    ivCommentEdit.visibility = View.GONE
                    ivCommentRemove.visibility = View.GONE
                    ivCommentReport.visibility = View.VISIBLE
                }

            }
        }

        fun click(reply: ReplyResponse) {
            binding.ivCommentEdit.setOnClickListener {
                replyCLickListener.clickEdit(reply)
            }
            binding.ivCommentRemove.setOnClickListener {
                replyCLickListener.clickRemove(reply.replySeq)

            }
            binding.ivCommentReport.setOnClickListener {
                replyCLickListener.clickReport(reply.replySeq)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        holder.bind(getItem(position))
        holder.click(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ReplyResponse>() {

            override fun areContentsTheSame(oldItem: ReplyResponse, newItem: ReplyResponse) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: ReplyResponse, newItem: ReplyResponse) =
                oldItem.replySeq == newItem.replySeq
        }
    }

    interface ReplyCLickListener {
        fun clickEdit(reply: ReplyResponse)
        fun clickRemove(replySeq: Long)
        fun clickReport(replySeq: Long)
    }
}

