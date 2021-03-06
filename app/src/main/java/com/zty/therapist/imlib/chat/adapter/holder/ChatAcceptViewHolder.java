package com.zty.therapist.imlib.chat.adapter.holder;

import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.zty.therapist.R;
import com.zty.therapist.imlib.chat.adapter.ChatAdapter;
import com.zty.therapist.imlib.chat.enity.MessageInfo;
import com.zty.therapist.imlib.chat.util.Utils;
import com.zty.therapist.imlib.chat.widget.BubbleLinearLayout;
import com.zty.therapist.imlib.chat.widget.GifTextView;
import com.zty.therapist.imlib.utils.ImageUtils;
import com.zty.therapist.model.ResultBean;
import com.zty.therapist.model.UserModel;
import com.zty.therapist.url.RequestCallback;
import com.zty.therapist.utils.MyImageLoader;
import com.zty.therapist.utils.ResultUtil;
import com.zty.therapist.utils.TimeUtils;
import com.zty.therapist.utils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Rance on 2016/11/29 10:47
 * 邮箱：rance935@163.com
 */
public class ChatAcceptViewHolder extends BaseViewHolder<MessageInfo> {

    @BindView(R.id.chat_item_date)
    TextView chatItemDate;
    @BindView(R.id.chat_item_header)
    ImageView chatItemHeader;
    @BindView(R.id.chat_item_content_image)
    ImageView chatItemContentImage;
    @BindView(R.id.chat_item_layout_image)
    BubbleLinearLayout chatItemLayoutImage;
    @BindView(R.id.chat_item_content_text)
    GifTextView chatItemContentText;
    @BindView(R.id.chat_item_voice)
    ImageView chatItemVoice;
    @BindView(R.id.chat_item_layout_content)
    BubbleLinearLayout chatItemLayoutContent;
    @BindView(R.id.chat_item_voice_time)
    TextView chatItemVoiceTime;

    private ChatAdapter.onItemClickListener onItemClickListener;
    private Handler handler;
    private RelativeLayout.LayoutParams layoutParams;

    public ChatAcceptViewHolder(ViewGroup parent, ChatAdapter.onItemClickListener onItemClickListener, Handler handler) {
        super(parent, R.layout.item_chat_accept);
        ButterKnife.bind(this, itemView);
        this.onItemClickListener = onItemClickListener;
        this.handler = handler;
        layoutParams = (RelativeLayout.LayoutParams) chatItemLayoutContent.getLayoutParams();
    }

    @Override
    public void setData(MessageInfo data) {
        chatItemDate.setText(data.getTime() != null ? data.getTime() : "");
        if (!TextUtils.isEmpty(data.getHeader())) {
            MyImageLoader.load(getContext(), data.getHeader(), chatItemHeader);
        } else {
            loadHeader(data.getFrom());
//            MyImageLoader.load(getContext(), R.mipmap.default_avatar, chatItemHeader);
        }
        chatItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onHeaderClick(getDataPosition());
            }
        });

        chatItemDate.setText(TimeUtils.getChatTime((new Long(data.getTime()))));

        if (TimeUtils.getTimeCount(new Long(data.getTime())) > 10) {
            chatItemDate.setVisibility(View.VISIBLE);
        } else {
            chatItemDate.setVisibility(View.GONE);
        }

        if (data.getContent() != null) {
            chatItemContentText.setSpanText(handler, data.getContent(), true);
            chatItemVoice.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemLayoutImage.setVisibility(View.GONE);
            TextPaint paint = chatItemContentText.getPaint();
            // 计算textview在屏幕上占多宽
            int len = (int) paint.measureText(chatItemContentText.getText().toString().trim());
            if (len < Utils.dp2px(getContext(), 200)) {
                layoutParams.width = len + Utils.dp2px(getContext(), 30);
                layoutParams.height = Utils.dp2px(getContext(), 48);
            } else {
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            }
            chatItemLayoutContent.setLayoutParams(layoutParams);
        } else if (data.getImageUrl() != null) {
            chatItemVoice.setVisibility(View.GONE);
            chatItemLayoutContent.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.GONE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemLayoutImage.setVisibility(View.VISIBLE);
            MyImageLoader.load(getContext(), ImageUtils.getThumbnailImagePath(data.getImageUrl()), chatItemContentImage);
            chatItemContentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onImageClick(chatItemContentImage, getDataPosition());
                }
            });
            layoutParams.width = Utils.dp2px(getContext(), 120);
            layoutParams.height = Utils.dp2px(getContext(), 48);
            chatItemLayoutContent.setLayoutParams(layoutParams);
        } else if (data.getFilepath() != null) {
            chatItemVoice.setVisibility(View.VISIBLE);
            chatItemLayoutContent.setVisibility(View.VISIBLE);
            chatItemContentText.setVisibility(View.GONE);
            chatItemVoiceTime.setVisibility(View.VISIBLE);
            chatItemLayoutImage.setVisibility(View.GONE);
            chatItemVoiceTime.setText(Utils.formatTime(data.getVoiceTime()));
            chatItemLayoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onVoiceClick(chatItemVoice, getDataPosition());
                }
            });
            layoutParams.width = Utils.dp2px(getContext(), 120);
            layoutParams.height = Utils.dp2px(getContext(), 48);
            chatItemLayoutContent.setLayoutParams(layoutParams);
        }
    }

    private void loadHeader(String userId) {
        UserUtils.getUserMessage(userId, new RequestCallback() {
            @Override
            public void onFailureCallback(int requestCode, String errorMsg) {

            }

            @Override
            public void onSuccessCallback(int requestCode, String response) {
                ResultBean resultBean = ResultUtil.getResult(response);
                if (resultBean.isSuccess()) {
                    UserModel userModel = new Gson().fromJson(resultBean.getResult(), UserModel.class);
                    if (userModel != null && !TextUtils.isEmpty(userModel.getPhoto()))
                        MyImageLoader.load(getContext(), userModel.getPhoto(), chatItemHeader);
                }
            }
        });
    }
}
