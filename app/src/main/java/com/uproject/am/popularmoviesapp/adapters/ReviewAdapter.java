package com.uproject.am.popularmoviesapp.adapters;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.uproject.am.popularmoviesapp.R;
import com.uproject.am.popularmoviesapp.entities.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

    private List<Review> mReviewsList;
    private Context mContext;

    public ReviewAdapter(Context context,List<Review> reviewsList) {
        this.mContext = context;
        this.mReviewsList = reviewsList;
    }

    @Override
    public ReviewAdapter.ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.review_item, parent, false);
        return new ReviewAdapter.ReviewAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewAdapterViewHolder reviewAdapterViewHolder, int position) {
        Review review = mReviewsList.get(position);
        reviewAdapterViewHolder.mAuthor.setText(review.getAuthor());
        reviewAdapterViewHolder.mReview.setText(review.getContent());

    }

    @Override
    public int getItemCount() {
        if (null == mReviewsList) return 0;
        return mReviewsList.size();
    }


    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author)
        TextView mAuthor;

        @BindView(R.id.tv_review)
        TextView mReview;

        public ReviewAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

}