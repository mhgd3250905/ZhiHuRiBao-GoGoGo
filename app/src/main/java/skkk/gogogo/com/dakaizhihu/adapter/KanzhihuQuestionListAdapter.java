package skkk.gogogo.com.dakaizhihu.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import skkk.gogogo.com.dakaizhihu.KanzhihuQuestionListGson.Answer;
import skkk.gogogo.com.dakaizhihu.R;

/*
* 
* 描    述：用于homefragment 的recyclerView的适配器
* 作    者：ksheng
* 时    间：
*/
public class KanzhihuQuestionListAdapter extends RecyclerView.Adapter<KanzhihuQuestionListAdapter.MyViewHolder> {
    private List<Answer> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public KanzhihuQuestionListAdapter(Context mContext, List<Answer> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mContext);
    }

    /*
* @desc 设置点击事件
* @时间 2016/6/18 15:53
*/
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /*
    * @desc 创建view
    * @时间 2016/6/21 23:27
    */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_kanzhihu_question, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    /*
    * @desc 数据绑定
    * @时间 2016/6/21 23:27
    */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (!TextUtils.isEmpty(mDatas.get(position).getAvatar())){
            holder.sdvKanzhihuImage.setImageURI(Uri.parse(mDatas.get(position).getAvatar()));
        }
        holder.tvKanzhihuTitle.setText(mDatas.get(position).getTitle());
        holder.tvKanzhihuAnswer.setText(mDatas.get(position).getSummary());//加载标题

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

/*
* @desc 获取Item数
* @时间 2016/6/22 13:03
*/
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /*
    * @desc viewHolder的设置
    * @时间 2016/6/22 13:03
    */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvKanzhihuTitle; //问题标题
        SimpleDraweeView sdvKanzhihuImage;//问题 缩略图
        TextView tvKanzhihuAnswer; //问题内容

        public MyViewHolder(View view) {
            super(view);
            tvKanzhihuTitle= (TextView) view.findViewById(R.id.tv_kanzhihu_title);
            sdvKanzhihuImage= (SimpleDraweeView) view.findViewById(R.id.sdv_kanzhihu_image);
            tvKanzhihuAnswer= (TextView) view.findViewById(R.id.tv_kanzhihu_answer);
        }
    }
}
