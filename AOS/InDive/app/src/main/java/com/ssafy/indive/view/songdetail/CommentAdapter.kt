package com.ssafy.indive.view.songdetail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemCommentBinding
import com.ssafy.indive.model.dto.Comment

class CommentAdapter(private val commentCLickListener: CommentCLickListener) :
    ListAdapter<Comment, CommentAdapter.CommentViewHolder>(diffUtil) {

    inner class CommentViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {
            binding.comment = comment
        }

        fun click(comment: Comment) {
            binding.ivCommentEdit.setOnClickListener {
                commentCLickListener.clickEdit(comment)
            }
            binding.ivCommentRemove.setOnClickListener {
                commentCLickListener.clickRemove(comment.commentSeq)

            }
            binding.ivCommentReport.setOnClickListener {
                commentCLickListener.clickReport(comment.commentSeq)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        holder.bind(getItem(position))
        holder.click(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Comment>() {
            override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: Comment, newItem: Comment) =
                oldItem.commentSeq == newItem.commentSeq
        }
    }

    interface CommentCLickListener {
        fun clickEdit(comment: Comment)
        fun clickRemove(commentSeq: Long)
        fun clickReport(commentSeq: Long)
    }
}

