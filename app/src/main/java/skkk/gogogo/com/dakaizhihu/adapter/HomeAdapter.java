package skkk.gogogo.com.dakaizhihu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import skkk.gogogo.com.dakaizhihu.HomeGson.Story;
import skkk.gogogo.com.dakaizhihu.R;

/*
* 
* 描    述：用于homefragment 的recyclerView的适配器
* 作    者：ksheng
* 时    间：
*/
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Story> mDatas;
    private Context mContext;
    private ImageLoader imageLoader;
    private LayoutInflater inflater;

    public HomeAdapter(Context mContext, List<Story> mDatas,ImageLoader imageLoader) {
        this.imageLoader=imageLoader;
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
        View view = inflater.inflate(R.layout.item_home_content, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    /*
    * @desc 数据绑定
    * @时间 2016/6/21 23:27
    */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try {
            ImageLoader.ImageListener listener=ImageLoader.getImageListener(holder.nivHomeImage,
                    R.drawable.ic_launcher,R.drawable.fall);

            imageLoader.get(mDatas.get(position).getImages().get(0),
                    listener);
        }catch (Exception e){

        }


//        holder.nivHomeImage.setDefaultImageResId(R.drawable.ic_launcher);//默认图片
//        holder.nivHomeImage.setErrorImageResId(R.drawable.fall);//错误图片
//        holder.nivHomeImage.setImageUrl(mDatas.get(position).getImages().get(0),
//                    imageLoader);//加载成功之图片

        holder.tvHomeTitle.setText(mDatas.get(position).getTitle());//加载标题

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

        ImageView nivHomeImage;//item 缩略图
        TextView tvHomeTitle; //item标题

        public MyViewHolder(View view) {
            super(view);
            nivHomeImage= (ImageView) view.findViewById(R.id.niv_home_image);
            tvHomeTitle= (TextView) view.findViewById(R.id.tv_home_title);
        }
    }



}
